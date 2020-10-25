import data.ResultEntry;
import estimations.DataSubjectIdsForUserEstimation;
import html.HTMLTableBuilder;
import kafka.DataConsumer;
import kafka.DataProducer;
import kafka.TopicCreator;
import kafka.constants.KafkaConstants;
import mongo.MongoCreator;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.TopicPartition;

import java.awt.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Timestamp;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

public class DataHandler {

    public void handle (){
        createTopic();

        generateDataForProducer();

        final List<ConsumerRecord<Integer, Integer>> resultsList = getDataFromConsumer();

        List<ResultEntry> dataSubjectIdsForUser = new DataSubjectIdsForUserEstimation().estimate(resultsList);

        new MongoCreator().storeResultsInMongoDb(dataSubjectIdsForUser);

        final File file = exportResultsToHTML(dataSubjectIdsForUser);

        displayHTML(file);
    }

    private static void createTopic() {
        new TopicCreator().create();
    }

    private static void generateDataForProducer() {
        final Random rand = new Random();

        final KafkaProducer<Integer, Integer> dataProducer = new DataProducer().createProducer();

        IntStream.range(0, KafkaConstants.ENTRIES_NUMBER).forEach(i -> {
            final Timestamp timestamp = new Timestamp(new Date().getTime());
            final Integer dataSubjectId = rand.nextInt(1000000) + 1000000;
            final Integer viewerId = rand.nextInt(100) + 1;
            dataProducer.send(new ProducerRecord(KafkaConstants.TOPIC_NAME, 0, timestamp.getTime(), viewerId, dataSubjectId));
        });
        dataProducer.close();
    }

    private static List<ConsumerRecord<Integer, Integer>> getDataFromConsumer() {
        int recordsCounter = 0;
        List<ConsumerRecord<Integer, Integer>> allRecords = new ArrayList<>();
        final Consumer<Integer, Integer> kafkaConsumer = new DataConsumer().subscribe();

        while(recordsCounter <= KafkaConstants.ENTRIES_NUMBER) {
            final ConsumerRecords<Integer, Integer> consumerRecords = kafkaConsumer.poll(Duration.ofSeconds(600));
            allRecords.addAll(consumerRecords.records(new TopicPartition(KafkaConstants.TOPIC_NAME, 0)));
            recordsCounter += consumerRecords.count();
        }
        return allRecords;
    }

    private static File exportResultsToHTML(List<ResultEntry> dataSubjectIdsForUser) {
        final File file = new File("result.htm");
        try(final BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
            final HTMLTableBuilder htmlBuilder = new HTMLTableBuilder();
            htmlBuilder.addTableHeader("Viewer Id", "DataSubjectIds Number");

            dataSubjectIdsForUser.forEach(element -> htmlBuilder.addRowValues(String.valueOf(element.getViewerID()), String.valueOf(element.getDataSubjectIds())));

            final String table = htmlBuilder.build();
            bw.write(table);
        } catch(IOException exception) {
            System.out.println("failed to export file");
        }
        return file;
    }

    private void displayHTML(File file) {
        try {
            Desktop.getDesktop().browse(file.toURI());
        } catch (IOException e) {
            System.out.println("failed to open browser with error " + e.getMessage());
        }
    }

}
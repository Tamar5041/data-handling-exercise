package kafka;

import avro.shaded.com.google.common.collect.Lists;
import kafka.constants.KafkaConstants;
import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.CreateTopicsResult;
import org.apache.kafka.clients.admin.NewTopic;

import java.util.Properties;

public class TopicCreator {

    public void create() {
        final Properties properties = buildProperties();
        try (AdminClient adminClient = AdminClient.create(properties)) {
            final NewTopic topic = new NewTopic(KafkaConstants.TOPIC_NAME, 1, (short) 1);
            adminClient.createTopics(Lists.newArrayList(topic));
        } catch (Exception e) {
            System.out.println("Failed to create topic " + e.getMessage());
        }
    }

    private Properties buildProperties() {
        final Properties properties = new Properties();
        properties.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, KafkaConstants.KAFKA_HOST);
        properties.put("key.deserializer", org.apache.kafka.common.serialization.IntegerDeserializer.class.getName());
        properties.put("value.deserializer", org.apache.kafka.common.serialization.IntegerDeserializer.class.getName());
        return properties;
    }

}
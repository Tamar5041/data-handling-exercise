package kafka;

import kafka.constants.KafkaConstants;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.IntegerSerializer;

import java.util.Properties;

public class DataProducer {

    public KafkaProducer<Integer, Integer> createProducer() {
        final Properties properties = buildProperties();
        return new KafkaProducer<>(properties);
    }

    private Properties buildProperties() {
        final Properties properties = new Properties();
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, KafkaConstants.KAFKA_HOST);
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, IntegerSerializer.class.getName());
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, IntegerSerializer.class.getName());
        return properties;
    }

}
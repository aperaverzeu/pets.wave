package wave.pets.data.projector.config;

import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import reactor.kafka.receiver.KafkaReceiver;
import reactor.kafka.receiver.ReceiverOptions;
import wave.pets.utilities.event.CollarEvent;
import wave.pets.utilities.event.MessageEvent;
import wave.pets.utilities.event.PetEvent;
import wave.pets.utilities.event.UserEvent;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static org.apache.kafka.clients.consumer.ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG;
import static org.apache.kafka.clients.consumer.ConsumerConfig.GROUP_ID_CONFIG;
import static org.apache.kafka.clients.consumer.ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG;
import static org.apache.kafka.clients.consumer.ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG;
import static org.springframework.kafka.support.serializer.JsonDeserializer.TRUSTED_PACKAGES;

@Configuration
public class KafkaConsumerConfig {
    @Value("${spring.kafka.bootstrap-servers}")
    private String server;

    @Value("${spring.kafka.consumer.group-id}")
    private String groupId;

    @Value("${spring.kafka.properties.spring.json.trusted.packages}")
    private String trustedPackage;

    @Value("${petswave.kafka.consumer.topic.message}")
    private String messageTopic;

    @Value("${petswave.kafka.consumer.topic.user}")
    private String userTopic;

    @Value("${petswave.kafka.consumer.topic.pet}")
    private String petTopic;

    @Value("${petswave.kafka.consumer.topic.collar}")
    private String collarTopic;

    public Map<String, Object> consumerProperties() {
        Map<String, Object> consumerProps = new HashMap<>();
        consumerProps.put(BOOTSTRAP_SERVERS_CONFIG, server);
        consumerProps.put(GROUP_ID_CONFIG, groupId);
        consumerProps.put(KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        consumerProps.put(VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        consumerProps.put(TRUSTED_PACKAGES, trustedPackage);
        return consumerProps;
    }

    public ReceiverOptions<String, MessageEvent> kafkaReceiverOptions(String topic) {
        ReceiverOptions<String, MessageEvent> basicReceiverOptions = ReceiverOptions.create(consumerProperties());
        return basicReceiverOptions.subscription(Collections.singletonList(topic));
    }

    public ReceiverOptions<String, UserEvent> userKafkaReceiverOptions(String topic) {
        ReceiverOptions<String, UserEvent> basicReceiverOptions = ReceiverOptions.create(consumerProperties());
        return basicReceiverOptions.subscription(Collections.singletonList(topic));
    }

    public ReceiverOptions<String, PetEvent> petKafkaReceiverOptions(String topic) {
        ReceiverOptions<String, PetEvent> basicReceiverOptions = ReceiverOptions.create(consumerProperties());
        return basicReceiverOptions.subscription(Collections.singletonList(topic));
    }

    public ReceiverOptions<String, CollarEvent> collarKafkaReceiverOptions(String topic) {
        ReceiverOptions<String, CollarEvent> basicReceiverOptions = ReceiverOptions.create(consumerProperties());
        return basicReceiverOptions.subscription(Collections.singletonList(topic));
    }

    @Bean
    public KafkaReceiver<String, MessageEvent> messageEventKafkaReceiver() {
        return KafkaReceiver.create(kafkaReceiverOptions(messageTopic));
    }

    @Bean
    public KafkaReceiver<String, UserEvent> userEventKafkaReceiver() {
        return KafkaReceiver.create(userKafkaReceiverOptions(userTopic));
    }

    @Bean
    public KafkaReceiver<String, PetEvent> petEventKafkaReceiver() {
        return KafkaReceiver.create(petKafkaReceiverOptions(petTopic));
    }

    @Bean
    public KafkaReceiver<String, CollarEvent> collarEventKafkaReceiver() {
        return KafkaReceiver.create(collarKafkaReceiverOptions(collarTopic));
    }
}

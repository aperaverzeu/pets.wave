package wave.pets.data.projector.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.reactive.ReactiveKafkaConsumerTemplate;
import reactor.kafka.receiver.ReceiverOptions;
import wave.pets.data.projector.model.MessageEventDTO;

import java.util.Collections;

@Configuration
public class KafkaConsumerConfig {
    @Bean
    public ReceiverOptions<String, MessageEventDTO> kafkaReceiverOptions(
            @Value(value = "${petswave.kafka.consumer.topic}") String topic,
            KafkaProperties kafkaProperties) {
        ReceiverOptions<String, MessageEventDTO> basicReceiverOptions =
                ReceiverOptions.create(kafkaProperties.buildConsumerProperties());
        return basicReceiverOptions.subscription(Collections.singletonList(topic));
    }

    @Bean
    public ReactiveKafkaConsumerTemplate<String, MessageEventDTO> reactiveKafkaConsumerTemplate(
            ReceiverOptions<String, MessageEventDTO> kafkaReceiverOptions) {
        return new ReactiveKafkaConsumerTemplate<>(kafkaReceiverOptions);
    }
}

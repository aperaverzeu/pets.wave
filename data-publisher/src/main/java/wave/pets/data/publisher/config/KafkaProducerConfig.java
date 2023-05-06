package wave.pets.data.publisher.config;

import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.reactive.ReactiveKafkaProducerTemplate;
import reactor.kafka.sender.SenderOptions;
import wave.pets.utilities.event.CollarEvent;
import wave.pets.utilities.event.MessageEvent;
import wave.pets.utilities.event.PetEvent;
import wave.pets.utilities.event.UserEvent;

import java.util.Map;

@Configuration
public class KafkaProducerConfig {
    @Bean
    public ReactiveKafkaProducerTemplate<String, MessageEvent> reactiveKafkaMessageEventProducerTemplate(
            KafkaProperties properties) {
        Map<String, Object> props = properties.buildProducerProperties();
        return new ReactiveKafkaProducerTemplate<>(SenderOptions.create(props));
    }

    @Bean
    public ReactiveKafkaProducerTemplate<String, UserEvent> reactiveKafkaUserEventProducerTemplate(
            KafkaProperties properties) {
        Map<String, Object> props = properties.buildProducerProperties();
        return new ReactiveKafkaProducerTemplate<>(SenderOptions.create(props));
    }

    @Bean
    public ReactiveKafkaProducerTemplate<String, PetEvent> reactiveKafkaPetEventProducerTemplate(
            KafkaProperties properties) {
        Map<String, Object> props = properties.buildProducerProperties();
        return new ReactiveKafkaProducerTemplate<>(SenderOptions.create(props));
    }

    @Bean
    public ReactiveKafkaProducerTemplate<String, CollarEvent> reactiveKafkaCollarEventProducerTemplate(
            KafkaProperties properties) {
        Map<String, Object> props = properties.buildProducerProperties();
        return new ReactiveKafkaProducerTemplate<>(SenderOptions.create(props));
    }
}

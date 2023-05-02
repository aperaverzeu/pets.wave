package wave.pets.data.to.kafka.config;

import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.reactive.ReactiveKafkaProducerTemplate;
import reactor.kafka.sender.SenderOptions;
import wave.pets.data.to.kafka.api.event.MessageEvent;

import java.util.Map;

@Configuration
public class KafkaProducerConfig {
    @Bean
    public ReactiveKafkaProducerTemplate<String, MessageEvent> reactiveKafkaProducerTemplate(
            KafkaProperties properties) {
        Map<String, Object> props = properties.buildProducerProperties();
        return new ReactiveKafkaProducerTemplate<>(SenderOptions.create(props));
    }
}

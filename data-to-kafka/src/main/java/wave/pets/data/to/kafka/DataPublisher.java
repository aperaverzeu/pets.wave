package wave.pets.data.to.kafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequiredArgsConstructor
@Slf4j
public class DataPublisher {
    private final KafkaTemplate<String, String> kafkaTemplate;

    @PostMapping(path = "/data", consumes = APPLICATION_JSON_VALUE)
    public void sendMessage(@RequestBody Message message) {
        var callback = kafkaTemplate.send("data", message.message());
        callback.whenCompleteAsync((result, ex) -> {
            if (ex == null) {
                log.info(message + " " + result.getRecordMetadata().offset());
            } else {
                log.info(message + " " + ex.getMessage());
            }
        });
    }

    public record Message(String message) {}
}


package wave.pets.data.to.cassandra;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
@Slf4j
public class DataConsumer {
    private final MessageRepository messageRepository;

    @KafkaListener(topics = {"data"}, groupId = "groupId")
    public void consumeMessage(String message) {
        log.info("Received Message: " + message);

        messageRepository.save(Message.builder().id(UUID.randomUUID()).message(message).build());
    }
}

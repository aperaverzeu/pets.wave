package wave.pets.data.to.cassandra;

import com.datastax.oss.driver.api.core.uuid.Uuids;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import wave.pets.model.Message;
import wave.pets.repository.MessageRepository;

@Component
@RequiredArgsConstructor
@Slf4j
public class DataConsumer {
    private final MessageRepository messageRepository;

    @KafkaListener(topics = {"data"}, groupId = "groupId")
    public void consumeMessage(String message) {
        log.info("Received Message: " + message);

        messageRepository.save(Message.builder().id(Uuids.timeBased()).message(message).build());
    }
}

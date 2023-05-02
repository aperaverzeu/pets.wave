package wave.pets.data.projector;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.core.reactive.ReactiveKafkaConsumerTemplate;
import org.springframework.stereotype.Component;
import wave.pets.data.projector.model.Message;
import wave.pets.data.projector.model.MessageEventDTO;
import wave.pets.data.projector.model.MessageRequest;
import wave.pets.data.projector.repository.MessageRepository;

import java.util.Objects;

@Component
@Slf4j
public class DataConsumer {
    private final ObjectMapper objectMapper;
    private final MessageRepository messageRepository;

    public DataConsumer(ReactiveKafkaConsumerTemplate<String, MessageEventDTO> reactiveKafkaConsumerTemplate,
                        ObjectMapper objectMapper,
                        MessageRepository messageRepository) {
        this.objectMapper = objectMapper;
        this.messageRepository = messageRepository;

        reactiveKafkaConsumerTemplate.receiveAutoAck()
                .doOnNext(this::logConsumerRecord)
                .map(ConsumerRecord::value)
                .doOnNext(this::publishToDataStore)
                .doOnError(throwable -> log.error(throwable.getMessage()))
                .subscribe();
    }

    private void publishToDataStore(MessageEventDTO messageEventDTO) {
        var messageEntity = mapEventToEntity(messageEventDTO);
        switch (messageEventDTO.getEventType()) {
            case "CREATED" -> create(messageEntity);
            case "UPDATED" -> update(messageEntity);
            case "DELETED" -> delete(messageEntity);
            default -> log.error("throws!");
        }
    }

    private void create(Message message) {
        messageRepository.save(message).subscribe();
    }

    private void update(Message message) {
        messageRepository.findById(Objects.requireNonNull(message.getId()))
                .flatMap((mes) -> {
                    mes.setId(message.getId());
                    mes.setMessage(message.getMessage());
                    return messageRepository.save(mes);
                }).subscribe();
    }

    private void delete(Message message) {
        messageRepository.delete(message).subscribe();
    }

    private Message mapEventToEntity(MessageEventDTO messageEventDTO) {
        MessageRequest messageRequest;
        try {
            messageRequest = objectMapper.readValue(messageEventDTO.getData(), MessageRequest.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        return Message.builder()
                .message(messageRequest.getMessage())
                .build();
    }

    private void logConsumerRecord(ConsumerRecord<String, MessageEventDTO> consumerRecord) {
        log.info("received key={}, value={} from topic={}, offset={}",
                consumerRecord.key(),
                consumerRecord.value(),
                consumerRecord.topic(),
                consumerRecord.offset());
    }
}

package wave.pets.data.projector.consumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import reactor.core.Disposable;
import reactor.kafka.receiver.KafkaReceiver;
import reactor.kafka.receiver.ReceiverRecord;
import wave.pets.data.projector.model.entity.MessageEntity;
import wave.pets.data.projector.repository.MessageRepository;
import wave.pets.utilities.event.MessageEvent;
import wave.pets.utilities.request.MessageRequest;

import java.util.Objects;
import java.util.UUID;

@Component
@RequiredArgsConstructor
@Slf4j
public class DataConsumer {
    private final KafkaReceiver<String, MessageEvent> messageEventKafkaReceiver;
    private final ObjectMapper objectMapper;
    private final MessageRepository messageRepository;

    @EventListener(ApplicationReadyEvent.class)
    public Disposable startKafkaConsumer() {
        return messageEventKafkaReceiver.receive()
                .doOnNext(this::logReceiverRecord)
                .map(ConsumerRecord::value)
                .doOnNext(this::publishToDataStore)
                .subscribe();
    }

    private void publishToDataStore(MessageEvent messageEventDTO) {
        var messageEntity = mapEventToEntity(messageEventDTO);
        switch (messageEventDTO.getEventType()) {
            case "CREATED" -> create(messageEntity);
            case "UPDATED" -> update(messageEntity);
            case "DELETED" -> delete(messageEntity);
            default -> log.error("throws!");
        }
    }

    private void create(MessageEntity messageEntity) {
        messageRepository.save(messageEntity.setAsNew()).subscribe();
    }

    private void update(MessageEntity messageEntity) {
        messageRepository.findById(Objects.requireNonNull(messageEntity.getId()))
                .flatMap(mes -> {
                    mes.setMessage(messageEntity.getMessage());
                    return messageRepository.save(mes);
                })
                .switchIfEmpty(messageRepository.save(messageEntity.setAsNew()))
                .subscribe();
    }

    private void delete(MessageEntity messageEntity) {
        messageRepository.delete(messageEntity).subscribe();
    }

    private MessageEntity mapEventToEntity(MessageEvent messageEventDTO) {
        MessageRequest messageRequest;
        try {
            messageRequest = objectMapper.readValue(messageEventDTO.getData(), MessageRequest.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        return MessageEntity.builder()
                .id(UUID.randomUUID())
                .message(messageRequest.getMessage())
                .build();
    }

    private void logReceiverRecord(ReceiverRecord<String, MessageEvent> record) {
        log.info("received key={}, value={} from topic={}, offset={}",
                record.key(),
                record.value(),
                record.topic(),
                record.offset());
    }
}

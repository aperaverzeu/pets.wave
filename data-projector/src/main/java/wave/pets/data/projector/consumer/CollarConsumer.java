package wave.pets.data.projector.consumer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import reactor.core.Disposable;
import reactor.kafka.receiver.KafkaReceiver;
import wave.pets.data.projector.model.entity.CollarEntity;
import wave.pets.data.projector.model.mapper.CollarMapper;
import wave.pets.data.projector.repository.CollarRepository;
import wave.pets.utilities.event.CollarEvent;

import java.util.Objects;

@Component
@RequiredArgsConstructor
@Slf4j
public class CollarConsumer {
    private final KafkaReceiver<String, CollarEvent> collarEventKafkaReceiver;
    private final CollarMapper collarMapper;
    private final CollarRepository collarRepository;

    @EventListener(ApplicationReadyEvent.class)
    public Disposable startKafkaConsumer() {
        return collarEventKafkaReceiver.receive()
                .doOnNext(this::logConsumerRecord)
                .map(ConsumerRecord::value)
                .doOnNext(this::publishToDataStore)
                .doOnError(throwable -> log.error(throwable.getMessage()))
                .subscribe();
    }

    private void publishToDataStore(CollarEvent collarEvent) {
        var messageEntity = collarMapper.apply(collarEvent);
        switch (collarEvent.getEventType()) {
            case "CREATED" -> create(messageEntity);
            case "UPDATED" -> update(messageEntity);
            case "DELETED" -> delete(messageEntity);
            default -> log.error("throws!");
        }
    }

    private void create(CollarEntity collarEntity) {
        collarRepository.save(collarEntity).subscribe();
    }

    private void update(CollarEntity collarEntity) {
        collarRepository.findById(Objects.requireNonNull(collarEntity.getId()))
                .flatMap((collar) -> {
                    collar.setId(collarEntity.getId());
                    collar.setModel(collarEntity.getModel());
                    collar.setPetId(collarEntity.getPetId());
                    return collarRepository.save(collar);
                }).subscribe();
    }

    private void delete(CollarEntity collarEntity) {
        collarRepository.delete(collarEntity).subscribe();
    }


    private void logConsumerRecord(ConsumerRecord<String, CollarEvent> consumerRecord) {
        log.info("received key={}, value={} from topic={}, offset={}",
                consumerRecord.key(),
                consumerRecord.value(),
                consumerRecord.topic(),
                consumerRecord.offset());
    }
}

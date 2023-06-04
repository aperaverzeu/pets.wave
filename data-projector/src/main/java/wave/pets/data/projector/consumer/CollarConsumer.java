package wave.pets.data.projector.consumer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import reactor.core.Disposable;
import reactor.core.publisher.Mono;
import reactor.kafka.receiver.KafkaReceiver;
import wave.pets.data.projector.consumer.spi.Consumer;
import wave.pets.utilities.entity.CollarEntity;
import wave.pets.data.projector.consumer.mapper.CollarMapper;
import wave.pets.data.projector.repository.CollarRepository;
import wave.pets.utilities.event.CollarEvent;

import java.util.UUID;

@Component
@RequiredArgsConstructor
@Slf4j
public class CollarConsumer extends Consumer<CollarEntity, CollarEvent> {
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

    @Override
    protected void publishToDataStore(CollarEvent collarEvent) {
        var messageEntity = collarMapper.mapEventToEntity(collarEvent);
        switch (collarEvent.getEventType()) {
            case "CREATED" -> create(messageEntity).subscribe();
            case "UPDATED" -> update(messageEntity).subscribe();
            case "DELETED" -> delete(messageEntity).subscribe();
            default -> log.error("throws!");
        }
    }

    @Override
    protected Mono<UUID> create(CollarEntity entity) {
        return collarRepository.save(entity);
    }

    @Override
    protected Mono<Long> update(CollarEntity entity) {
        return collarRepository.update(entity);
    }

    @Override
    protected Mono<Long> delete(CollarEntity entity) {
        return collarRepository.delete(entity);
    }
}

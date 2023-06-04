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
import wave.pets.data.projector.consumer.mapper.PetMapper;
import wave.pets.data.projector.consumer.spi.Consumer;
import wave.pets.utilities.entity.PetEntity;
import wave.pets.data.projector.repository.PetRepository;
import wave.pets.utilities.event.PetEvent;

import java.util.UUID;

@Component
@RequiredArgsConstructor
@Slf4j
public class PetConsumer extends Consumer<PetEntity, PetEvent> {
    private final KafkaReceiver<String, PetEvent> petEventKafkaReceiver;
    private final PetMapper petMapper;
    private final PetRepository petRepository;

    @EventListener(ApplicationReadyEvent.class)
    public Disposable startKafkaConsumer() {
        return petEventKafkaReceiver.receive()
                .doOnNext(this::logConsumerRecord)
                .map(ConsumerRecord::value)
                .doOnNext(this::publishToDataStore)
                .doOnError(throwable -> log.error(throwable.getMessage()))
                .subscribe();
    }

    @Override
    protected void publishToDataStore(PetEvent petEventDTO) {
        var messageEntity = petMapper.mapEventToEntity(petEventDTO);
        switch (petEventDTO.getEventType()) {
            case "CREATED" -> create(messageEntity).subscribe();
            case "UPDATED" -> update(messageEntity).subscribe();
            case "DELETED" -> delete(messageEntity).subscribe();
            default -> log.error("throws!");
        }
    }

    @Override
    protected Mono<UUID> create(PetEntity entity) {
        return petRepository.save(entity);
    }

    @Override
    protected Mono<Long> update(PetEntity entity) {
        return petRepository.update(entity);
    }

    @Override
    protected Mono<Long> delete(PetEntity entity) {
        return petRepository.delete(entity);
    }
}

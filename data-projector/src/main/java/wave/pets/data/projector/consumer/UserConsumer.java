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
import wave.pets.data.projector.consumer.mapper.UserMapper;
import wave.pets.data.projector.consumer.spi.Consumer;
import wave.pets.utilities.entity.UserEntity;
import wave.pets.data.projector.repository.UserRepository;
import wave.pets.utilities.event.UserEvent;

import java.util.UUID;

@Component
@RequiredArgsConstructor
@Slf4j
public class UserConsumer extends Consumer<UserEntity, UserEvent> {
    private final KafkaReceiver<String, UserEvent> userEventKafkaReceiver;
    private final UserMapper userMapper;
    private final UserRepository userRepository;

    @EventListener(ApplicationReadyEvent.class)
    public Disposable startKafkaConsumer() {
        return userEventKafkaReceiver.receive()
                .doOnNext(this::logConsumerRecord)
                .map(ConsumerRecord::value)
                .doOnNext(this::publishToDataStore)
                .doOnError(throwable -> log.error(throwable.getMessage()))
                .subscribe();
    }

    @Override
    protected void publishToDataStore(UserEvent userEventDTO) {
        var messageEntity = userMapper.mapEventToEntity(userEventDTO);
        switch (userEventDTO.getEventType()) {
            case "CREATED" -> create(messageEntity).subscribe();
            case "UPDATED" -> update(messageEntity).subscribe();
            case "DELETED" -> delete(messageEntity).subscribe();
            default -> log.error("throws!");
        }
    }

    @Override
    protected Mono<UUID> create(UserEntity userEntity) {
        return userRepository.save(userEntity);
    }

    @Override
    protected Mono<Long> update(UserEntity userEntity) {
        return userRepository.update(userEntity);
    }

    @Override
    protected Mono<Long> delete(UserEntity userEntity) {
        return userRepository.delete(userEntity);
    }
}

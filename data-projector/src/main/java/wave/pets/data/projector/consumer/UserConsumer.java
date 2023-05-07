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
import wave.pets.data.projector.model.entity.UserEntity;
import wave.pets.data.projector.repository.UserRepository;
import wave.pets.utilities.event.UserEvent;
import wave.pets.utilities.request.UserRequest;

import java.util.Objects;
import java.util.Set;

@Component
@RequiredArgsConstructor
@Slf4j
public class UserConsumer {
    private final KafkaReceiver<String, UserEvent> userEventKafkaReceiver;
    private final ObjectMapper objectMapper;
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

    private void publishToDataStore(UserEvent userEventDTO) {
        var messageEntity = mapEventToEntity(userEventDTO);
        switch (userEventDTO.getEventType()) {
            case "CREATED" -> create(messageEntity);
            case "UPDATED" -> update(messageEntity);
            case "DELETED" -> delete(messageEntity);
            default -> log.error("throws!");
        }
    }

    private void create(UserEntity userEntity) {
        userRepository.save(userEntity.setAsNew()).subscribe();
    }

    private void update(UserEntity userEntity) {
        userRepository.findById(Objects.requireNonNull(userEntity.getId()))
                .flatMap(usr -> {
                    usr.setName(userEntity.getName());
                    usr.setUsername(userEntity.getUsername());
                    usr.setPassword(userEntity.getPassword());
                    usr.setPetsId(userEntity.getPetsId());
                    return userRepository.save(usr);
                })
                .switchIfEmpty(userRepository.save(userEntity.setAsNew()))
                .subscribe();
    }

    private void delete(UserEntity userEntity) {
        userRepository.delete(userEntity).subscribe();
    }

    private UserEntity mapEventToEntity(UserEvent userEventDTO) {
        UserRequest userRequest;
        try {
            userRequest = objectMapper.readValue(userEventDTO.getData(), UserRequest.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        return UserEntity.builder()
                .name(userRequest.getName())
                .username(userRequest.getUsername())
                .password(userRequest.getPassword())
                .petsId(Set.copyOf(userRequest.getPetIds()))
                .build();
    }

    private void logConsumerRecord(ConsumerRecord<String, UserEvent> consumerRecord) {
        log.info("received key={}, value={} from topic={}, offset={}",
                consumerRecord.key(),
                consumerRecord.value(),
                consumerRecord.topic(),
                consumerRecord.offset());
    }
}

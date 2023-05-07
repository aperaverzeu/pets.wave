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
import wave.pets.data.projector.model.entity.PetEntity;
import wave.pets.data.projector.repository.PetRepository;
import wave.pets.utilities.event.PetEvent;
import wave.pets.utilities.request.PetRequest;

import java.util.Objects;

@Component
@RequiredArgsConstructor
@Slf4j
public class PetConsumer {
    private final KafkaReceiver<String, PetEvent> petEventKafkaReceiver;
    private final ObjectMapper objectMapper;
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

    private void publishToDataStore(PetEvent petEventDTO) {
        var messageEntity = mapEventToEntity(petEventDTO);
        switch (petEventDTO.getEventType()) {
            case "CREATED" -> create(messageEntity);
            case "UPDATED" -> update(messageEntity);
            case "DELETED" -> delete(messageEntity);
            default -> log.error("throws!");
        }
    }

    private void create(PetEntity petEntity) {
        petRepository.save(petEntity.setAsNew()).subscribe();
    }

    private void update(PetEntity petEntity) {
        petRepository.findById(Objects.requireNonNull(petEntity.getId()))
                .flatMap(pet -> {
                    pet.setName(petEntity.getName());
                    pet.setWeight(petEntity.getWeight());
                    pet.setHeight(petEntity.getHeight());
                    pet.setAge(petEntity.getAge());
                    pet.setPetType(petEntity.getPetType());
                    pet.setUserId(petEntity.getUserId());
                    return petRepository.save(pet);
                })
                .switchIfEmpty(petRepository.save(petEntity.setAsNew()))
                .subscribe();
    }

    private void delete(PetEntity petEntity) {
        petRepository.delete(petEntity).subscribe();
    }

    private PetEntity mapEventToEntity(PetEvent petEventDTO) {
        PetRequest petRequest;
        try {
            petRequest = objectMapper.readValue(petEventDTO.getData(), PetRequest.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        return PetEntity.builder()
                .name(petRequest.getName())
                .weight(petRequest.getWeight())
                .height(petRequest.getHeight())
                .age(petRequest.getAge())
                .petType(petRequest.getPetType())
                .userId(petRequest.getUserId())
                .build();
    }

    private void logConsumerRecord(ConsumerRecord<String, PetEvent> consumerRecord) {
        log.info("received key={}, value={} from topic={}, offset={}",
                consumerRecord.key(),
                consumerRecord.value(),
                consumerRecord.topic(),
                consumerRecord.offset());
    }
}

package wave.pets.data.publisher.handler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.reactive.ReactiveKafkaProducerTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
import wave.pets.data.publisher.model.mapper.PetMapper;
import wave.pets.data.publisher.repository.PetEventRepository;
import wave.pets.utilities.event.PetEvent;
import wave.pets.utilities.event.spi.EventType;
import wave.pets.utilities.request.PetRequest;

import static org.springframework.web.reactive.function.server.ServerResponse.badRequest;
import static wave.pets.utilities.event.spi.EventType.CREATED;
import static wave.pets.utilities.event.spi.EventType.DELETED;
import static wave.pets.utilities.event.spi.EventType.UPDATED;

@Component
@RequiredArgsConstructor
@Slf4j
public class PetHandler {
    @Value("${petswave.kafka.producer.pet.topic}")
    private String topic;
    @Value("${petswave.kafka.producer.pet.key}")
    private String key;
    private final ReactiveKafkaProducerTemplate<String, PetEvent> reactiveKafkaProducerTemplate;
    private final PetEventRepository petEventRepository;
    private final PetMapper petMapper;

    public Mono<ServerResponse> createPet(ServerRequest request) {
        return handle(request, CREATED);
    }

    public Mono<ServerResponse> updatePet(ServerRequest request) {
        return handle(request, UPDATED);
    }

    public Mono<ServerResponse> deletePet(ServerRequest request) {
        return handle(request, DELETED);
    }

    private Mono<ServerResponse> handle(ServerRequest request, EventType eventType) {
        return request.bodyToMono(PetRequest.class)
                .flatMap(petRequest -> petMapper.mapRequestToEvent(petRequest, eventType))
                .doOnNext(this::publishToKafka)
                .doOnNext(this::publishToEventStore)
                .flatMap(petMapper::mapEventToResponse)
                .switchIfEmpty(badRequest().build());
    }

    private void publishToKafka(PetEvent petEvent) {
        reactiveKafkaProducerTemplate.send(petMapper.mapEventToProducerRecord(petEvent, topic, key))
                .subscribe();
    }

    private void publishToEventStore(PetEvent petEvent) {
        petEventRepository.save(petMapper.mapEventToEntity(petEvent))
                .subscribe();
    }
}

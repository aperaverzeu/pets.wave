package wave.pets.data.publisher.handler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.reactive.ReactiveKafkaProducerTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
import wave.pets.data.publisher.model.event.PetEvent;
import wave.pets.data.publisher.model.event.spi.EventType;
import wave.pets.data.publisher.model.request.PetRequest;
import wave.pets.data.publisher.repository.PetEventRepository;

import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.server.ServerResponse.badRequest;
import static org.springframework.web.reactive.function.server.ServerResponse.status;
import static wave.pets.data.publisher.model.event.spi.EventType.CREATED;
import static wave.pets.data.publisher.model.event.spi.EventType.DELETED;
import static wave.pets.data.publisher.model.event.spi.EventType.UPDATED;

@Component
@RequiredArgsConstructor
@Slf4j
public class PetHandler {
    @Value("${petswave.kafka.producer.topic.pet}")
    private String topic;
    private final ReactiveKafkaProducerTemplate<String, PetEvent> reactiveKafkaProducerTemplate;
    private final PetEventRepository petEventRepository;
    private final ObjectMapper objectMapper;

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
                .flatMap(petRequest -> mapRequestToEvent(petRequest, eventType))
                .doOnNext(this::publishToKafka)
                .doOnNext(this::publishToEventStore)
                .flatMap(this::mapEventToResponse)
                .switchIfEmpty(badRequest().build());
    }

    private void publishToKafka(PetEvent petEvent) {
        reactiveKafkaProducerTemplate.send(mapEventToProducerRecord(petEvent)).subscribe();
    }

    private ProducerRecord<String, PetEvent> mapEventToProducerRecord(PetEvent petEvent) {
        return new ProducerRecord<>(topic, petEvent);
    }

    private void publishToEventStore(PetEvent petEvent) {
        petEventRepository.save(petEvent).subscribe();
    }

    private Mono<PetEvent> mapRequestToEvent(PetRequest petRequest, EventType eventType) {
        return Mono.just(PetEvent.builder()
                .eventType(eventType.name())
                .data(mapObjectToJsonString(petRequest))
                .build());
    }

    private String mapObjectToJsonString(PetRequest petRequest) {
        try {
            return objectMapper.writeValueAsString(petRequest);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private Mono<ServerResponse> mapEventToResponse(PetEvent petEvent) {
        return status(OK)
                .contentType(APPLICATION_JSON)
                .body(Mono.just(petEvent.getData()), String.class);
    }
}

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
import wave.pets.data.publisher.model.event.CollarEvent;
import wave.pets.data.publisher.model.event.spi.EventType;
import wave.pets.data.publisher.model.request.CollarRequest;
import wave.pets.data.publisher.repository.CollarEventRepository;

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
public class CollarHandler {
    @Value("${petswave.kafka.producer.topic.collar}")
    private String topic;
    private final ReactiveKafkaProducerTemplate<String, CollarEvent> reactiveKafkaProducerTemplate;
    private final CollarEventRepository collarEventRepository;
    private final ObjectMapper objectMapper;

    public Mono<ServerResponse> createCollar(ServerRequest request) {
        return handle(request, CREATED);
    }

    public Mono<ServerResponse> updateCollar(ServerRequest request) {
        return handle(request, UPDATED);
    }

    public Mono<ServerResponse> deleteCollar(ServerRequest request) {
        return handle(request, DELETED);
    }

    private Mono<ServerResponse> handle(ServerRequest request, EventType eventType) {
        return request.bodyToMono(CollarRequest.class)
                .flatMap(collarRequest -> mapRequestToEvent(collarRequest, eventType))
                .doOnNext(this::publishToKafka)
                .doOnNext(this::publishToEventStore)
                .flatMap(this::mapEventToResponse)
                .switchIfEmpty(badRequest().build());
    }

    private void publishToKafka(CollarEvent collarEvent) {
        reactiveKafkaProducerTemplate.send(mapEventToProducerRecord(collarEvent)).subscribe();
    }

    private ProducerRecord<String, CollarEvent> mapEventToProducerRecord(CollarEvent collarEvent) {
        return new ProducerRecord<>(topic, collarEvent);
    }

    private void publishToEventStore(CollarEvent collarEvent) {
        collarEventRepository.save(collarEvent).subscribe();
    }

    private Mono<CollarEvent> mapRequestToEvent(CollarRequest collarRequest, EventType eventType) {
        return Mono.just(CollarEvent.builder()
                .eventType(eventType.name())
                .data(mapObjectToJsonString(collarRequest))
                .build());
    }

    private String mapObjectToJsonString(CollarRequest collarRequest) {
        try {
            return objectMapper.writeValueAsString(collarRequest);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private Mono<ServerResponse> mapEventToResponse(CollarEvent collarEvent) {
        return status(OK)
                .contentType(APPLICATION_JSON)
                .body(Mono.just(collarEvent.getData()), String.class);
    }
}

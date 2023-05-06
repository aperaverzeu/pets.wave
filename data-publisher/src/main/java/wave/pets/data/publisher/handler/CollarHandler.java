package wave.pets.data.publisher.handler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.reactive.ReactiveKafkaProducerTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
import wave.pets.data.publisher.model.mapper.CollarMapper;
import wave.pets.data.publisher.repository.CollarEventRepository;
import wave.pets.utilities.event.CollarEvent;
import wave.pets.utilities.event.spi.EventType;
import wave.pets.utilities.request.CollarRequest;

import static org.springframework.web.reactive.function.server.ServerResponse.badRequest;
import static wave.pets.utilities.event.spi.EventType.CREATED;
import static wave.pets.utilities.event.spi.EventType.DELETED;
import static wave.pets.utilities.event.spi.EventType.UPDATED;

@Component
@RequiredArgsConstructor
@Slf4j
public class CollarHandler {
    @Value("${petswave.kafka.producer.topic.collar}")
    private String topic;
    private final ReactiveKafkaProducerTemplate<String, CollarEvent> reactiveKafkaProducerTemplate;
    private final CollarEventRepository collarEventRepository;
    private final CollarMapper collarMapper;

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
                .flatMap(collarRequest -> collarMapper.mapRequestToEvent(collarRequest, eventType))
                .doOnNext(this::publishToKafka)
                .doOnNext(this::publishToEventStore)
                .flatMap(collarMapper::mapEventToResponse)
                .switchIfEmpty(badRequest().build());
    }

    private void publishToKafka(CollarEvent collarEvent) {
        reactiveKafkaProducerTemplate.send(collarMapper.mapEventToProducerRecord(collarEvent, topic))
                .subscribe();
    }

    private void publishToEventStore(CollarEvent collarEvent) {
        collarEventRepository.save(collarMapper.mapEventToEntity(collarEvent))
                .subscribe();
    }
}

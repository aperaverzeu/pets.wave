package wave.pets.data.to.kafka.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.kafka.core.reactive.ReactiveKafkaProducerTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
import wave.pets.data.to.kafka.api.event.EventType;
import wave.pets.data.to.kafka.api.event.MessageEvent;
import wave.pets.data.to.kafka.api.event.MessageEventRepository;
import wave.pets.data.to.kafka.api.request.MessageRequest;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.server.ServerResponse.notFound;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;
import static org.springframework.web.reactive.function.server.ServerResponse.status;

@Component
@RequiredArgsConstructor
@Slf4j
public class DataHandler {
    private final ReactiveKafkaProducerTemplate<String, MessageEvent> reactiveKafkaProducerTemplate;
    private final MessageEventRepository messageEventRepository;
    private final ObjectMapper objectMapper;

    public Mono<ServerResponse> index(ServerRequest request) {
        return ok().contentType(APPLICATION_JSON)
                .body(Mono.just(request.path()), String.class);
    }

    public Mono<ServerResponse> publish(ServerRequest request) {
        return request.bodyToMono(MessageRequest.class)
                .flatMap(this::mapRequestToEvent)
                .doOnNext(this::publishToKafka)
                .doOnNext(this::publishToEventStore)
                .flatMap(this::mapEventToResponse)
                .switchIfEmpty(notFound().build());
    }

    private void publishToKafka(MessageEvent messageEvent) {
//        reactiveKafkaProducerTemplate.send("data", messageEvent)
        reactiveKafkaProducerTemplate.send(mapEventToProducerRecord(messageEvent))
                .doOnNext(r -> logEvent(messageEvent, "sent"))
                .subscribe();
    }

    private void publishToEventStore(MessageEvent messageEvent) {
        messageEventRepository.save(messageEvent)
                .doOnNext(r -> logEvent(r, "event created"))
                .subscribe();
    }

    private Mono<MessageEvent> mapRequestToEvent(MessageRequest messageRequest) {
        return Mono.just(MessageEvent.builder()
                .eventType(EventType.CREATED.name())
                .data(mapObjectToJsonString(messageRequest))
                .build());
    }

    private String mapObjectToJsonString(Object request) {
        try {
            return objectMapper.writeValueAsString(request);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private ProducerRecord<String, MessageEvent> mapEventToProducerRecord(MessageEvent messageEvent) {
        return new ProducerRecord<>("data", "ms", messageEvent);
    }

    private Mono<ServerResponse> mapEventToResponse(MessageEvent messageEvent) {
        return status(CREATED)
                .contentType(APPLICATION_JSON)
                .body(Mono.just(messageEvent.getData()), String.class);
    }

    private void logEvent(MessageEvent messageEvent, String text) {
        log.info(text + " {} {} {}", messageEvent.getId(), messageEvent.getEventType(), messageEvent.getData());
    }
}

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
import wave.pets.data.publisher.model.event.UserEvent;
import wave.pets.data.publisher.model.event.spi.EventType;
import wave.pets.data.publisher.model.request.UserRequest;
import wave.pets.data.publisher.repository.UserEventRepository;

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
public class UserHandler {
    @Value("${petswave.kafka.producer.topic.user}")
    private String topic;
    private final ReactiveKafkaProducerTemplate<String, UserEvent> reactiveKafkaProducerTemplate;
    private final UserEventRepository userEventRepository;
    private final ObjectMapper objectMapper;

    public Mono<ServerResponse> createUser(ServerRequest request) {
        return handle(request, CREATED);
    }

    public Mono<ServerResponse> updateUser(ServerRequest request) {
        return handle(request, UPDATED);
    }

    public Mono<ServerResponse> deleteUser(ServerRequest request) {
        return handle(request, DELETED);
    }

    private Mono<ServerResponse> handle(ServerRequest request, EventType eventType) {
        return request.bodyToMono(UserRequest.class)
                .flatMap(userRequest -> mapRequestToEvent(userRequest, eventType))
                .doOnNext(this::publishToKafka)
                .doOnNext(this::publishToEventStore)
                .flatMap(this::mapEventToResponse)
                .switchIfEmpty(badRequest().build());
    }

    private void publishToKafka(UserEvent userEvent) {
        reactiveKafkaProducerTemplate.send(mapEventToProducerRecord(userEvent)).subscribe();
    }

    private ProducerRecord<String, UserEvent> mapEventToProducerRecord(UserEvent userEvent) {
        return new ProducerRecord<>(topic, userEvent);
    }

    private void publishToEventStore(UserEvent userEvent) {
        userEventRepository.save(userEvent).subscribe();
    }

    private Mono<UserEvent> mapRequestToEvent(UserRequest userRequest, EventType eventType) {
        return Mono.just(UserEvent.builder()
                .eventType(eventType.name())
                .data(mapObjectToJsonString(userRequest))
                .build());
    }

    private String mapObjectToJsonString(UserRequest userRequest) {
        try {
            return objectMapper.writeValueAsString(userRequest);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private Mono<ServerResponse> mapEventToResponse(UserEvent userEvent) {
        return status(OK)
                .contentType(APPLICATION_JSON)
                .body(Mono.just(userEvent.getData()), String.class);
    }
}

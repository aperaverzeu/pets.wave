package wave.pets.data.publisher.handler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.reactive.ReactiveKafkaProducerTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
import wave.pets.data.publisher.model.mapper.UserMapper;
import wave.pets.data.publisher.repository.UserEventRepository;
import wave.pets.utilities.event.UserEvent;
import wave.pets.utilities.event.spi.EventType;
import wave.pets.utilities.request.UserRequest;

import static org.springframework.web.reactive.function.server.ServerResponse.badRequest;
import static wave.pets.utilities.event.spi.EventType.CREATED;
import static wave.pets.utilities.event.spi.EventType.DELETED;
import static wave.pets.utilities.event.spi.EventType.UPDATED;

@Component
@RequiredArgsConstructor
@Slf4j
public class UserHandler {
    @Value("${petswave.kafka.producer.topic.user}")
    private String topic;
    private final ReactiveKafkaProducerTemplate<String, UserEvent> reactiveKafkaProducerTemplate;
    private final UserEventRepository userEventRepository;
    private final UserMapper userMapper;

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
                .flatMap(userRequest -> userMapper.mapRequestToEvent(userRequest, eventType))
                .doOnNext(this::publishToKafka)
                .doOnNext(this::publishToEventStore)
                .flatMap(userMapper::mapEventToResponse)
                .switchIfEmpty(badRequest().build());
    }

    private void publishToKafka(UserEvent userEvent) {
        reactiveKafkaProducerTemplate.send(userMapper.mapEventToProducerRecord(userEvent, topic))
                .subscribe();
    }

    private void publishToEventStore(UserEvent userEvent) {
        userEventRepository.save(userMapper.mapEventToEntity(userEvent))
                .subscribe();
    }
}

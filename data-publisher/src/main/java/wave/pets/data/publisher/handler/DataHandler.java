package wave.pets.data.publisher.handler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.reactive.ReactiveKafkaProducerTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
import wave.pets.data.publisher.model.mapper.MessageMapper;
import wave.pets.data.publisher.repository.MessageEventRepository;
import wave.pets.utilities.event.MessageEvent;
import wave.pets.utilities.request.MessageRequest;

import static org.springframework.web.reactive.function.server.ServerResponse.notFound;

@Component
@RequiredArgsConstructor
@Slf4j
public class DataHandler {
    private final ReactiveKafkaProducerTemplate<String, MessageEvent> reactiveKafkaProducerTemplate;
    private final MessageEventRepository messageEventRepository;
    private final MessageMapper messageMapper;

    public Mono<ServerResponse> publish(ServerRequest request) {
        return request.bodyToMono(MessageRequest.class)
                .flatMap(messageMapper::mapRequestToEvent)
                .doOnNext(this::publishToKafka)
                .doOnNext(this::publishToEventStore)
                .flatMap(messageMapper::mapEventToResponse)
                .switchIfEmpty(notFound().build());
    }

    private void publishToKafka(MessageEvent messageEvent) {
        reactiveKafkaProducerTemplate.send(messageMapper.mapEventToProducerRecord(messageEvent)).subscribe();
    }

    private void publishToEventStore(MessageEvent messageEvent) {
        messageEventRepository.save(messageMapper.mapEventToEntity(messageEvent))
                .subscribe();
    }
}

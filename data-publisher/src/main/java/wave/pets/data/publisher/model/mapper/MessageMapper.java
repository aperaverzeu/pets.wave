package wave.pets.data.publisher.model.mapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
import wave.pets.data.publisher.model.entity.MessageEventEntity;
import wave.pets.utilities.event.MessageEvent;
import wave.pets.utilities.event.spi.EventType;
import wave.pets.utilities.request.MessageRequest;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.server.ServerResponse.status;

@Component
@RequiredArgsConstructor
public class MessageMapper {
    private final ObjectMapper objectMapper;

    public Mono<MessageEvent> mapRequestToEvent(MessageRequest messageRequest) {
        return Mono.just(MessageEvent.builder()
                .eventType(EventType.CREATED.name())
                .data(mapObjectToJsonString(messageRequest))
                .build());
    }

    public ProducerRecord<String, MessageEvent> mapEventToProducerRecord(MessageEvent messageEvent,
                                                                         String topic,
                                                                         String key) {
        return new ProducerRecord<>(topic, key, messageEvent);
    }

    public MessageEventEntity mapEventToEntity(MessageEvent messageEvent) {
        return MessageEventEntity.builder()
                .id(messageEvent.getId())
                .eventType(EventType.CREATED.name())
                .data(messageEvent.getData())
                .build();
    }

    public Mono<ServerResponse> mapEventToResponse(MessageEvent messageEvent) {
        return status(CREATED)
                .contentType(APPLICATION_JSON)
                .body(Mono.just(messageEvent.getData()), String.class);
    }

    private String mapObjectToJsonString(Object request) {
        try {
            return objectMapper.writeValueAsString(request);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}

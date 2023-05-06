package wave.pets.data.publisher.model.mapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
import wave.pets.data.publisher.model.entity.UserEventEntity;
import wave.pets.utilities.event.UserEvent;
import wave.pets.utilities.event.spi.EventType;
import wave.pets.utilities.request.UserRequest;

import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.server.ServerResponse.status;

@Component
@RequiredArgsConstructor
public class UserMapper {
    private final ObjectMapper objectMapper;

    public Mono<UserEvent> mapRequestToEvent(UserRequest userRequest, EventType eventType) {
        return Mono.just(UserEvent.builder()
                .eventType(eventType.name())
                .data(mapObjectToJsonString(userRequest))
                .build());
    }

    public ProducerRecord<String, UserEvent> mapEventToProducerRecord(UserEvent userEvent, String topic) {
        return new ProducerRecord<>(topic, userEvent);
    }

    public UserEventEntity mapEventToEntity(UserEvent userEvent) {
        return UserEventEntity.builder()
                .id(userEvent.getId())
                .eventType(userEvent.getEventType())
                .data(userEvent.getData())
                .build();
    }

    public Mono<ServerResponse> mapEventToResponse(UserEvent userEvent) {
        return status(OK)
                .contentType(APPLICATION_JSON)
                .body(Mono.just(userEvent.getData()), String.class);
    }

    private String mapObjectToJsonString(UserRequest userRequest) {
        try {
            return objectMapper.writeValueAsString(userRequest);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}

package wave.pets.data.publisher.model.mapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
import wave.pets.data.publisher.model.entity.PetEventEntity;
import wave.pets.utilities.event.PetEvent;
import wave.pets.utilities.event.spi.EventType;
import wave.pets.utilities.request.PetRequest;

import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.server.ServerResponse.status;

@Component
@RequiredArgsConstructor
public class PetMapper {
    private final ObjectMapper objectMapper;

    public Mono<PetEvent> mapRequestToEvent(PetRequest petRequest, EventType eventType) {
        return Mono.just(PetEvent.builder()
                .eventType(eventType.name())
                .data(mapObjectToJsonString(petRequest))
                .build());
    }

    public ProducerRecord<String, PetEvent> mapEventToProducerRecord(PetEvent petEvent,
                                                                     String topic,
                                                                     String key) {
        return new ProducerRecord<>(topic, key, petEvent);
    }

    public PetEventEntity mapEventToEntity(PetEvent petEvent) {
        return PetEventEntity.builder()
                .id(petEvent.getId())
                .eventType(petEvent.getEventType())
                .data(petEvent.getData())
                .build();
    }

    public Mono<ServerResponse> mapEventToResponse(PetEvent petEvent) {
        return status(OK)
                .contentType(APPLICATION_JSON)
                .body(Mono.just(petEvent.getData()), String.class);
    }

    private String mapObjectToJsonString(PetRequest petRequest) {
        try {
            return objectMapper.writeValueAsString(petRequest);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}

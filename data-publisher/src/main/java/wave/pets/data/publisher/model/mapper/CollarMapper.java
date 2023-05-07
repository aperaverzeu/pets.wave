package wave.pets.data.publisher.model.mapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
import wave.pets.data.publisher.model.entity.CollarEventEntity;
import wave.pets.utilities.event.CollarEvent;
import wave.pets.utilities.event.spi.EventType;
import wave.pets.utilities.request.CollarRequest;

import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.server.ServerResponse.status;

@Component
@RequiredArgsConstructor
public class CollarMapper {
    private final ObjectMapper objectMapper;

    public Mono<CollarEvent> mapRequestToEvent(CollarRequest collarRequest, EventType eventType) {
        return Mono.just(CollarEvent.builder()
                .eventType(eventType.name())
                .data(mapObjectToJsonString(collarRequest))
                .build());
    }

    public CollarEventEntity mapEventToEntity(CollarEvent collarEvent) {
        return CollarEventEntity.builder()
                .id(collarEvent.getId())
                .eventType(collarEvent.getEventType())
                .data(collarEvent.getData())
                .build();
    }

    public ProducerRecord<String, CollarEvent> mapEventToProducerRecord(CollarEvent collarEvent,
                                                                        String topic,
                                                                        String key) {
        return new ProducerRecord<>(topic, key, collarEvent);
    }

    public Mono<ServerResponse> mapEventToResponse(CollarEvent collarEvent) {
        return status(OK)
                .contentType(APPLICATION_JSON)
                .body(Mono.just(collarEvent.getData()), String.class);
    }

    private String mapObjectToJsonString(CollarRequest collarRequest) {
        try {
            return objectMapper.writeValueAsString(collarRequest);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}

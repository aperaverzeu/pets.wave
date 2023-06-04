package wave.pets.data.projector.consumer.mapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import wave.pets.utilities.entity.CollarEntity;
import wave.pets.utilities.event.CollarEvent;
import wave.pets.utilities.request.CollarRequest;

@Component
@RequiredArgsConstructor
public class CollarMapper {
    private final ObjectMapper objectMapper;

    public CollarEntity mapEventToEntity(CollarEvent collarEvent) {
        CollarRequest collarRequest;
        try {
            collarRequest = objectMapper.readValue(collarEvent.getData(), CollarRequest.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        return CollarEntity.builder()
                .id(collarRequest.getCollarId())
                .model(collarRequest.getModel())
                .petId(collarRequest.getPetId())
                .build();
    }
}

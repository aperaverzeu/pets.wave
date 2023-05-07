package wave.pets.data.projector.model.mapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import wave.pets.data.projector.model.entity.CollarEntity;
import wave.pets.utilities.event.CollarEvent;
import wave.pets.utilities.request.CollarRequest;

import java.util.function.Function;

@Component
@RequiredArgsConstructor
public class CollarMapper implements Function<CollarEvent, CollarEntity> {
    private final ObjectMapper objectMapper;

    @Override
    public CollarEntity apply(CollarEvent collarEvent) {
        CollarRequest collarRequest = getCollarRequest(collarEvent);

        return CollarEntity.builder()
                .model(collarRequest.getModel())
                .petId(collarRequest.getPetId())
                .build();
    }

    private CollarRequest getCollarRequest(CollarEvent collarEvent) {
        try {
            return objectMapper.readValue(collarEvent.getData(), CollarRequest.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}

package wave.pets.data.projector.consumer.mapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import wave.pets.utilities.entity.PetEntity;
import wave.pets.utilities.event.PetEvent;
import wave.pets.utilities.request.PetRequest;

@Component
@RequiredArgsConstructor
public class PetMapper {
    private final ObjectMapper objectMapper;

    public PetEntity mapEventToEntity(PetEvent petEventDTO) {
        PetRequest petRequest;
        try {
            petRequest = objectMapper.readValue(petEventDTO.getData(), PetRequest.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        return PetEntity.builder()
                .id(petRequest.getPetId())
                .name(petRequest.getName())
                .weight(petRequest.getWeight())
                .height(petRequest.getHeight())
                .age(petRequest.getAge())
                .petType(petRequest.getPetType())
                .userId(petRequest.getUserId())
                .build();
    }
}

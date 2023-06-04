package wave.pets.data.projector.consumer.mapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import wave.pets.utilities.entity.UserEntity;
import wave.pets.utilities.event.UserEvent;
import wave.pets.utilities.request.UserRequest;

@Component
@RequiredArgsConstructor
public class UserMapper {
    private final ObjectMapper objectMapper;

    public UserEntity mapEventToEntity(UserEvent userEventDTO) {
        UserRequest userRequest;
        try {
            userRequest = objectMapper.readValue(userEventDTO.getData(), UserRequest.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        return UserEntity.builder()
                .id(userRequest.getUserId())
                .name(userRequest.getName())
                .username(userRequest.getUsername())
                .password(userRequest.getPassword())
                .build();
    }
}

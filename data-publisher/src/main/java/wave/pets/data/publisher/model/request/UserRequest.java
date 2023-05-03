package wave.pets.data.publisher.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

import java.util.List;
import java.util.UUID;

@Builder
@Data
@Jacksonized
public class UserRequest {
    private UUID userId;
    private String name;
    private String username;
    private String password;
    @JsonProperty("petIds")
    private List<UUID> petIds;
}

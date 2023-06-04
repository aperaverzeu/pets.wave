package wave.pets.utilities.request;

import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

import java.util.UUID;

@Builder
@Data
@Jacksonized
public class UserRequest {
    private UUID userId;
    private String name;
    private String username;
    private String password;
}

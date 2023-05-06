package wave.pets.utilities.request;

import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

@Builder
@Data
@Jacksonized
public class MessageRequest {
    private String message;
}

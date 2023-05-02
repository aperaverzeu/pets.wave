package wave.pets.data.projector.model;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
@Builder
@Jacksonized
public class MessageRequest {
    @NotBlank(message = "The message is mandatory")
    String message;
}

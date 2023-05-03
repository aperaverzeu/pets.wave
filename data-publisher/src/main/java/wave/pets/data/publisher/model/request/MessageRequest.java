package wave.pets.data.publisher.model.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.extern.jackson.Jacksonized;

@Builder
@Jacksonized
public class MessageRequest {
    @NotBlank(message = "The message is mandatory")
    private String message;
}

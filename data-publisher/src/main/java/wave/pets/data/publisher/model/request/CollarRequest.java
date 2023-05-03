package wave.pets.data.publisher.model.request;

import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

import java.util.UUID;

@Builder
@Data
@Jacksonized
public class CollarRequest {
    private UUID collarId;
    private String model;
    private UUID petId;
}

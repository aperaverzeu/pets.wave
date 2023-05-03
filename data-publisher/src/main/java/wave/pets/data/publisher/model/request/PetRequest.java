package wave.pets.data.publisher.model.request;

import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

import java.util.UUID;

@Builder
@Data
@Jacksonized
public class PetRequest {
    private UUID petId;
    private String name;
    private String weight;
    private String height;
    private String age;
    private String petType;
    private UUID userId;
}

package wave.pets.utilities.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CollarEntity {
    private UUID id = UUID.randomUUID();
    private String model;
    private UUID petId;
}

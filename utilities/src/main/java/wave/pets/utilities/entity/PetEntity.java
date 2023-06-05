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
public class PetEntity {
    private UUID id;
    private String name;
    private String weight;
    private String height;
    private String age;
    private String petType;
    private UUID userId;
    private UUID collarId;
}

package wave.pets.data.projector.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.domain.Persistable;

import java.util.Objects;
import java.util.UUID;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PetEntity implements Persistable<UUID> {
    @Id
    @Builder.Default
    private UUID id = UUID.randomUUID();
    private String name;
    private String weight;
    private String height;
    private String age;
    private String petType;
    private UUID userId;

    @Override
    public boolean isNew() {
        boolean result = Objects.isNull(id);
        this.id = result ? UUID.randomUUID() : this.id;
        return result;
    }
}

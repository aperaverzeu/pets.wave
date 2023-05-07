package wave.pets.data.projector.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.domain.Persistable;
import org.springframework.data.relational.core.mapping.Table;

import java.util.UUID;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table("pet")
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

    @Transient
    private boolean newPet;

    @Override
    @Transient
    public boolean isNew() {
        return this.newPet || this.id == null;
    }

    public PetEntity setAsNew() {
        this.newPet = true;
        return this;
    }
}

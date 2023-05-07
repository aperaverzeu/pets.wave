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
@Table("collar")
public class CollarEntity implements Persistable<UUID> {
    @Id
    @Builder.Default
    private UUID id = UUID.randomUUID();
    private String model;
    private UUID petId;

    @Transient
    private boolean newCollar;

    @Override
    @Transient
    public boolean isNew() {
        return this.newCollar || this.id == null;
    }

    public CollarEntity setAsNew() {
        this.newCollar = true;
        return this;
    }
}

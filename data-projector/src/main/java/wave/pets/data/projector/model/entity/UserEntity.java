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

import java.util.Set;
import java.util.UUID;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table("user")
public class UserEntity implements Persistable<UUID> {
    @Id
    @Builder.Default
    private UUID id = UUID.randomUUID();
    private String name;
    private String username;
    private String password;
    private Set<UUID> petsId;

    @Transient
    private boolean newUser;

    @Override
    @Transient
    public boolean isNew() {
        return this.newUser || this.id == null;
    }

    public UserEntity setAsNew() {
        this.newUser = true;
        return this;
    }
}

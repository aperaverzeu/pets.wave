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
@Table("message")
public class MessageEntity implements Persistable<UUID> {
    @Id
    private UUID id;
    private String message;

    @Transient
    private boolean newMessageEntity;

    @Override
    @Transient
    public boolean isNew() {
        return this.newMessageEntity || id == null;
    }

    public MessageEntity setAsNew() {
        this.newMessageEntity = true;
        return this;
    }
}

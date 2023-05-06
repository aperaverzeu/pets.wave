package wave.pets.data.publisher.model.entity;

import lombok.experimental.SuperBuilder;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;
import wave.pets.utilities.event.UserEvent;

import java.util.UUID;

@Table("user_events")
@SuperBuilder
public class UserEventEntity extends UserEvent {
    @PrimaryKey
    private UUID id;
}

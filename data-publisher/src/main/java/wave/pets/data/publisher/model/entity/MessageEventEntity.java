package wave.pets.data.publisher.model.entity;

import lombok.experimental.SuperBuilder;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;
import wave.pets.utilities.event.MessageEvent;

import java.util.UUID;

@Table("message_events")
@SuperBuilder
public class MessageEventEntity extends MessageEvent {
    @PrimaryKey
    private UUID id;
}

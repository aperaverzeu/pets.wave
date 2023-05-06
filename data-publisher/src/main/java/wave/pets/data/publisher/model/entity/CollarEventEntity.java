package wave.pets.data.publisher.model.entity;

import lombok.experimental.SuperBuilder;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;
import wave.pets.utilities.event.CollarEvent;

import java.util.UUID;

@Table("collar_events")
@SuperBuilder
public class CollarEventEntity extends CollarEvent {
    @PrimaryKey
    private UUID id;
}

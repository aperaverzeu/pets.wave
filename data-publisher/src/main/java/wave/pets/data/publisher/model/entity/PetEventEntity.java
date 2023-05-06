package wave.pets.data.publisher.model.entity;

import lombok.experimental.SuperBuilder;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;
import wave.pets.utilities.event.PetEvent;

import java.util.UUID;

@Table("pet_events")
@SuperBuilder
public class PetEventEntity extends PetEvent {
    @PrimaryKey
    private UUID id;
}

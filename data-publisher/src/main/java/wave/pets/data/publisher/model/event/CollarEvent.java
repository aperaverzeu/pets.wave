package wave.pets.data.publisher.model.event;

import lombok.experimental.SuperBuilder;
import org.springframework.data.cassandra.core.mapping.Table;
import wave.pets.data.publisher.model.event.spi.Event;

@Table("collar_events")
@SuperBuilder
public class CollarEvent extends Event {}

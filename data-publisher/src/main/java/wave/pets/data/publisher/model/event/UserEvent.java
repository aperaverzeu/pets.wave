package wave.pets.data.publisher.model.event;

import lombok.experimental.SuperBuilder;
import org.springframework.data.cassandra.core.mapping.Table;
import wave.pets.data.publisher.model.event.spi.Event;

@Table("user_events")
@SuperBuilder
public class UserEvent extends Event {}

package wave.pets.data.publisher.model;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.springframework.data.cassandra.core.mapping.Table;

@Table("message_events")
@SuperBuilder
@Getter
@Setter
public class MessageEvent extends Event {
    private String data;
}
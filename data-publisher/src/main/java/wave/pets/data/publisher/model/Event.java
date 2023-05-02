package wave.pets.data.publisher.model;

import com.datastax.oss.driver.api.core.uuid.Uuids;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@ToString
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public abstract class Event {
    // TODO change this to regular UUIDv4
    @PrimaryKey
    @Builder.Default
    private UUID id = Uuids.timeBased();
    @Builder.Default
    private LocalDateTime createdAt = LocalDateTime.now();
    private String eventType;
}


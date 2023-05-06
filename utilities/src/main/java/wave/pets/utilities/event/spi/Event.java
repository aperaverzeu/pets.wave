package wave.pets.utilities.event.spi;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@ToString
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public abstract class Event {
    @Builder.Default
    private UUID id = UUID.randomUUID();
    @Builder.Default
    private LocalDateTime createdAt = LocalDateTime.now();
    private String eventType;
    private String data;
}


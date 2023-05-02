package wave.pets.data.projector.model;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class MessageEventDTO {
    private UUID id;
    private LocalDateTime createdAt;
    private String eventType;
    private String data;
}

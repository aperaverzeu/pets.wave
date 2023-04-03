package wave.pets.query;

import com.datastax.oss.driver.api.core.uuid.Uuids;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Comparator;
import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class MessageController {
    private final MessageRepository messageRepository;

    @GetMapping("/")
    public ResponseEntity<List<Message>> getAllMessages() {
        return ResponseEntity.ok(messageRepository.findAll());
    }

    @GetMapping("/latest")
    public ResponseEntity<Message> getLatestMessage() {
        return messageRepository.findAll()
                .stream()
                .sorted(Comparator.comparingLong(m -> Uuids.unixTimestamp(m.getId())))
                .reduce((first, second) -> second)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/one/{id}")
    public ResponseEntity<Message> getMessageById(@PathVariable UUID id) {
        return messageRepository.findAll()
                .stream()
                .filter(m -> m.getId().equals(id))
                .findFirst()
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}

package wave.pets.query;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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
        final var messages = messageRepository.findAll();
        return ResponseEntity.ok(messages.get(messages.size() - 1));
    }

    @GetMapping("/one/{message}")
    public ResponseEntity<String> getMessage(@PathVariable String message) {
        final var result = messageRepository.findAll()
                .stream()
                .map(Message::getMessage)
                .filter(m -> m.equals(message))
                .findFirst()
                .orElse("");

        return ResponseEntity.ok(result);
    }
}

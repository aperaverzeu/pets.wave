package wave.pets.query;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.UUID;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.server.ServerResponse.notFound;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@Component
@RequiredArgsConstructor
public class MessageHandler {
    private final MessageRepository messageRepository;

    public Mono<ServerResponse> getAllMessages() {
        return ok()
                .contentType(APPLICATION_JSON)
                .body(messageRepository.findAll(), Message.class)
                .switchIfEmpty(notFound().build());
    }

    @GetMapping("/one/{id}")
    public Mono<ServerResponse> getMessageById(@PathVariable String id) {
        return ok()
                .contentType(APPLICATION_JSON)
                .body(messageRepository.findById(UUID.fromString(id)), Message.class)
                .switchIfEmpty(notFound().build());
    }
}

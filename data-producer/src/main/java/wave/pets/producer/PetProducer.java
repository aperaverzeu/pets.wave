package wave.pets.producer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
import wave.pets.model.Message;
import wave.pets.repository.MessageRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static java.util.stream.Stream.concat;
import static org.springframework.http.MediaType.TEXT_EVENT_STREAM;
import static reactor.core.publisher.Flux.fromStream;

@RestController
@RequiredArgsConstructor
@Slf4j
// TODO improve cache management
public class PetProducer {
    private final List<Message> messages = new ArrayList<>();
    private final MessageRepository messageRepository;

    @Scheduled(fixedRate = 3000)
    public void readAndUpdate() {
        messages.addAll(concat(messages.stream(), messageRepository.findAll().stream()).distinct().toList());
        messages.forEach(this::addPetGeo);
    }

    @GetMapping(value = "/geo")
    public Mono<ServerResponse> addPetGeo(Message message) {
        return ServerResponse
                .ok()
                .contentType(TEXT_EVENT_STREAM)
                .body(fromStream(Stream.generate(() -> new Message(message.getId(), message.getMessage()))),
                        Message.class);
    }
}

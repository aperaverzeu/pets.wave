package wave.pets.producer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import wave.pets.model.Message;
import wave.pets.repository.MessageRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static java.time.Duration.ofMillis;
import static java.util.stream.Stream.concat;
import static org.springframework.http.MediaType.TEXT_EVENT_STREAM_VALUE;

@RestController
@RequiredArgsConstructor
@Slf4j
// TODO improve cache management
public class PetProducer {
    private final List<Message> messages = new ArrayList<>();
    private final MessageRepository messageRepository;

    @Scheduled(fixedRate = 250)
    public void readAndUpdate() {
        messages.addAll(concat(messages.stream(), messageRepository.findAll().stream()).distinct().toList());
    }

    @GetMapping(value = "/geo/{id}", produces = TEXT_EVENT_STREAM_VALUE)
    public Flux<Message> addPetGeo(@PathVariable UUID id) {
        var currentMessages = new ArrayList<>(messages);
        var message = currentMessages.stream()
                .filter(m -> m.getId().equals(id))
                .findFirst()
                .orElseThrow();

        return Flux.interval(ofMillis(1000))
                .map(i -> message)
                .doOnNext(m -> log.info("Message {} {}", m.getId(), m.getMessage()));
    }

    /*
       Define some common strategies for Geo and Health data
       Add method, that by id throws some stream of data according to the strategy
       Scheduled operation is only for cache management
     */
}

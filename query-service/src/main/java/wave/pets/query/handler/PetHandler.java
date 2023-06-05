package wave.pets.query.handler;

import lombok.RequiredArgsConstructor;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Sinks;
import wave.pets.query.repository.PetRepository;
import wave.pets.utilities.entity.PetEntity;

import java.time.Duration;
import java.time.LocalTime;
import java.util.UUID;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.http.MediaType.APPLICATION_NDJSON;
import static org.springframework.http.MediaType.TEXT_EVENT_STREAM;
import static org.springframework.web.reactive.function.server.ServerResponse.notFound;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@Component
@RequiredArgsConstructor
public class PetHandler {
    private final PetRepository petRepository;

    public Sinks.Many<String> someSink = Sinks.many().replay().all();

    public Mono<ServerResponse> getAll(@SuppressWarnings("unused parameter") ServerRequest request) {
        return ok()
                .contentType(APPLICATION_JSON)
                .body(petRepository.findAll(), PetEntity.class)
                .switchIfEmpty(notFound().build());
    }

    public Mono<ServerResponse> getPetById(ServerRequest request) {
        return ok()
                .contentType(APPLICATION_JSON)
                .body(petRepository.findById(UUID.fromString(request.pathVariable("id"))), PetEntity.class)
                .switchIfEmpty(notFound().build());
    }

    public Mono<ServerResponse> getAllPetsByUserId(ServerRequest request) {
        return ok()
                .contentType(APPLICATION_JSON)
                .body(petRepository.findAllByUserId(UUID.fromString(request.pathVariable("user_id"))), PetEntity.class)
                .switchIfEmpty(notFound().build());
    }

    public Mono<ServerResponse> getAllStream(@SuppressWarnings("unused parameter") ServerRequest request) {
        return ok()
                .contentType(TEXT_EVENT_STREAM)
                .body(BodyInserters.fromServerSentEvents(Flux.interval(Duration.ofSeconds(3))
                        .map(it -> ServerSentEvent.builder()
                                .id(it.toString())
                                .event("periodic-event")
                                .data("SSE - " + LocalTime.now())
                                .comment("some")
                                .retry(Duration.ofSeconds(10))
                                .build()
                        )))
                .log();
    }
}

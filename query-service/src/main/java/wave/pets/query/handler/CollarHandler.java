package wave.pets.query.handler;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
import wave.pets.query.repository.CollarRepository;
import wave.pets.utilities.entity.CollarEntity;

import java.util.UUID;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.server.ServerResponse.notFound;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@Component
@RequiredArgsConstructor
public class CollarHandler {
    private final CollarRepository collarRepository;

    public Mono<ServerResponse> getAll(@SuppressWarnings("unused parameter") ServerRequest request) {
        return ok()
                .contentType(APPLICATION_JSON)
                .body(collarRepository.findAll(), CollarEntity.class)
                .switchIfEmpty(notFound().build());
    }

    public Mono<ServerResponse> getCollarById(ServerRequest request) {
        return ok()
                .contentType(APPLICATION_JSON)
                .body(collarRepository.findById(UUID.fromString(request.pathVariable("id"))), CollarEntity.class)
                .switchIfEmpty(notFound().build());
    }
}

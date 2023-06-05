package wave.pets.query.handler;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
import wave.pets.query.repository.UserRepository;
import wave.pets.utilities.entity.UserEntity;

import java.util.UUID;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.server.ServerResponse.notFound;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@Component
@RequiredArgsConstructor
public class UserHandler {
    private final UserRepository userRepository;

    @SuppressWarnings("unused parameter")
    public Mono<ServerResponse> getAll(ServerRequest request) {
        return ok()
                .contentType(APPLICATION_JSON)
                .body(userRepository.findAll(), UserEntity.class)
                .switchIfEmpty(notFound().build());
    }

    public Mono<ServerResponse> getUserById(ServerRequest request) {
        return ok()
                .contentType(APPLICATION_JSON)
                .body(userRepository.findById(UUID.fromString(request.pathVariable("id"))), UserEntity.class)
                .switchIfEmpty(notFound().build());
    }
}

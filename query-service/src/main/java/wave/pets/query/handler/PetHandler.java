package wave.pets.query.handler;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
import wave.pets.query.repository.PetRepository;
import wave.pets.utilities.entity.PetEntity;

import java.util.UUID;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.server.ServerResponse.notFound;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@Component
@RequiredArgsConstructor
public class PetHandler {
    private final PetRepository petRepository;

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
}

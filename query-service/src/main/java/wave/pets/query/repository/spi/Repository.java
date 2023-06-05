package wave.pets.query.repository.spi;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface Repository<T> {
    Mono<T> findById(UUID id);

    Flux<T> findAll();
}

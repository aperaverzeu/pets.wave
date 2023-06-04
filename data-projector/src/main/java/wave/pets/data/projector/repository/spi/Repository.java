package wave.pets.data.projector.repository.spi;

import reactor.core.publisher.Mono;

import java.util.UUID;

public interface Repository<T> {
    Mono<UUID> save(T entity);

    Mono<Long> update(T entity);

    Mono<Long> delete(T entity);
}

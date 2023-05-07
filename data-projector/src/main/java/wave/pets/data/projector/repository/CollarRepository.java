package wave.pets.data.projector.repository;

import org.springframework.data.r2dbc.repository.Modifying;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;
import wave.pets.data.projector.model.entity.CollarEntity;

import java.util.UUID;

@Repository
public interface CollarRepository extends R2dbcRepository<CollarEntity, UUID> {
    @Modifying
    @Query("insert into collar (id, model, petId) values (:#{#collarEntity.id}, :#{#collarEntity.model}, :#{#collarEntity.petId})")
    @NonNull
    Mono<CollarEntity> save(@NonNull final CollarEntity collarEntity);
}

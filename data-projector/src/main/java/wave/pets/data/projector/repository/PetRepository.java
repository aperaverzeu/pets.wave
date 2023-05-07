package wave.pets.data.projector.repository;

import org.springframework.data.r2dbc.repository.Modifying;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;
import wave.pets.data.projector.model.entity.PetEntity;

import java.util.UUID;

@Repository
public interface PetRepository extends R2dbcRepository<PetEntity, UUID> {
    @Modifying
    @Query("insert into pet (id, name, weight, height, age, petType, userId) values (:#{#petEntity.id}, :#{#petEntity.name}, :#{#petEntity.weight}, :#{#petEntity.height}, :#{#petEntity.age}, :#{#petEntity.petType}, :#{#petEntity.userId})")
    @NonNull
    Mono<PetEntity> save(@NonNull final PetEntity petEntity);
}

package wave.pets.data.projector.repository;

import org.springframework.data.r2dbc.repository.Modifying;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;
import wave.pets.data.projector.model.entity.UserEntity;

import java.util.UUID;

@Repository
public interface UserRepository extends R2dbcRepository<UserEntity, UUID> {
    @Modifying
    @Query("insert into user (id, name, username, password, petIds) values (:#{#petEntity.id}, :#{#petEntity.name}, :#{#petEntity.username}, :#{#petEntity.password}, :#{#petEntity.petIds})")
    @NonNull
    Mono<UserEntity> save(@NonNull final UserEntity userEntity);
}

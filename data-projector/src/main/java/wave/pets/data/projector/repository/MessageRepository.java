package wave.pets.data.projector.repository;

import org.springframework.data.r2dbc.repository.Modifying;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;
import wave.pets.data.projector.model.entity.MessageEntity;

import java.util.UUID;

@Repository
public interface MessageRepository extends R2dbcRepository<MessageEntity, UUID> {
    @Modifying
    @Query("insert into message (id, message) values (:#{#messageEntity.id}, :#{#messageEntity.message})")
    @NonNull
    Mono<MessageEntity> save(@NonNull final MessageEntity messageEntity);
}

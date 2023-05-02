package wave.pets.query;

import org.springframework.data.r2dbc.repository.Modifying;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Repository
public interface MessageRepository extends R2dbcRepository<Message, UUID> {
    @Modifying
    @Query("insert into message (id, message) values (:#{#messageEntity.id}, :#{#messageEntity.message})")
    @NonNull
    Mono<Message> save(@NonNull final Message messageEntity);
}

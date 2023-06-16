package wave.pets.data.projector.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import wave.pets.utilities.entity.UserEntity;
import wave.pets.data.projector.repository.spi.Repository;

import java.util.Objects;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class UserRepository implements Repository<UserEntity> {
    private final DatabaseClient databaseClient;

    private static final String createUserSQL =
            "INSERT INTO public.\"user\" (id, name, username, password) " +
                    "VALUES (:id, :name, :username, :password)";

    private static final String updateUserSQL =
            "UPDATE public.\"user\" " +
                    "SET name = :name, username = :username, password = :password " +
                    "WHERE id = :id";

    private static final String deleteUserSQL =
            "DELETE FROM public.\"user\" WHERE id = :id";

    @Override
    public Mono<UUID> save(UserEntity userEntity) {
        return databaseClient.sql(createUserSQL)
                .bind("id", Objects.requireNonNull(userEntity.getId()))
                .bind("name", userEntity.getName())
                .bind("username", userEntity.getUsername())
                .bind("password", userEntity.getPassword())
                .fetch()
                .first()
                .map(r -> (UUID) r.get("id"));
    }

    @Override
    public Mono<Long> update(UserEntity userEntity) {
        return databaseClient.sql(updateUserSQL)
                .bind("name", userEntity.getName())
                .bind("username", userEntity.getUsername())
                .bind("password", userEntity.getPassword())
                .bind("id", Objects.requireNonNull(userEntity.getId()))
                .fetch()
                .rowsUpdated();
    }

    @Override
    public Mono<Long> delete(UserEntity userEntity) {
        return databaseClient.sql(deleteUserSQL)
                .bind("id", userEntity.getId())
                .fetch()
                .rowsUpdated();
    }
}

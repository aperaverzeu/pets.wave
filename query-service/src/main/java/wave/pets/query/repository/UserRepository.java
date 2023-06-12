package wave.pets.query.repository;

import io.r2dbc.spi.Row;
import io.r2dbc.spi.RowMetadata;
import lombok.RequiredArgsConstructor;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import wave.pets.query.repository.spi.Repository;
import wave.pets.utilities.entity.UserEntity;

import java.util.UUID;
import java.util.function.BiFunction;

@Component
@RequiredArgsConstructor
public class UserRepository implements Repository<UserEntity> {
    private final DatabaseClient databaseClient;

    public static final BiFunction<Row, RowMetadata, UserEntity> MAPPING_FUNCTION = (row, rowMetaData) ->
            UserEntity.builder()
                    .id(row.get("id", UUID.class))
                    .name(row.get("name", String.class))
                    .username(row.get("username", String.class))
                    .password(row.get("password", String.class))
                    .build();

    private static final String findUserByIdSQL =
            "SELECT * FROM public.\"user\" WHERE id = :id";

    private static final String findUserByUsernameSQL =
            "SELECT * FROM public.\"user\" WHERE username = :username";

    private static final String findAllUsersSQL =
            "SELECT * FROM public.\"user\"";

    @Override
    public Mono<UserEntity> findById(UUID id) {
        return databaseClient.sql(findUserByIdSQL)
                .bind("id", id)
                .map(MAPPING_FUNCTION)
                .one();
    }

    public Mono<UserEntity> findByUsername(String username) {
        return databaseClient.sql(findUserByUsernameSQL)
                .bind("username", username)
                .map(MAPPING_FUNCTION)
                .one();
    }

    @Override
    public Flux<UserEntity> findAll() {
        return databaseClient.sql(findAllUsersSQL)
                .filter((statement, executeFunction) -> statement.fetchSize(10).execute())
                .map(MAPPING_FUNCTION)
                .all();
    }
}

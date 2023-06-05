package wave.pets.query.repository;

import io.r2dbc.spi.Row;
import io.r2dbc.spi.RowMetadata;
import lombok.RequiredArgsConstructor;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import wave.pets.query.repository.spi.Repository;
import wave.pets.utilities.entity.PetEntity;

import java.time.Duration;
import java.util.UUID;
import java.util.function.BiFunction;

@Component
@RequiredArgsConstructor
public class PetRepository implements Repository<PetEntity> {
    private final DatabaseClient databaseClient;

    public static final BiFunction<Row, RowMetadata, PetEntity> MAPPING_FUNCTION = (row, rowMetaData) ->
            PetEntity.builder()
                    .id(row.get("id", UUID.class))
                    .name(row.get("name", String.class))
                    .weight(row.get("weight", String.class))
                    .height(row.get("height", String.class))
                    .age(row.get("age", String.class))
                    .petType(row.get("pet_type", String.class))
                    .userId(row.get("user_id", UUID.class))
                    .collarId(row.get("collar_id", UUID.class))
                    .build();

    private static final String findPetByIdSQL =
            "SELECT * FROM public.\"pet\" WHERE id = :id";

    private static final String findAllPetsSQL =
            "SELECT * FROM public.\"pet\"";

    private static final String findAllPetsByUserIdSQL =
            "SELECT * FROM public.\"pet\" WHERE user_id = :user_id";

    @Override
    public Mono<PetEntity> findById(UUID id) {
        return databaseClient.sql(findPetByIdSQL)
                .bind("id", id)
                .map(MAPPING_FUNCTION)
                .one();
    }

    @Override
    public Flux<PetEntity> findAll() {
        return databaseClient.sql(findAllPetsSQL)
                .filter((statement, executeFunction) -> statement.fetchSize(10).execute())
                .map(MAPPING_FUNCTION)
                .all();
    }

    public Flux<PetEntity> findAllByUserId(UUID userId) {
        return databaseClient.sql(findAllPetsByUserIdSQL)
                .bind("user_id", userId)
                .map(MAPPING_FUNCTION)
                .all();
    }

    public Flux<ServerSentEvent<Object>> listenToGeoData() {
        return this.findAll()
                .map(pet -> ServerSentEvent.builder()
                        .retry(Duration.ofSeconds(5L))
                        .event(pet.getClass().getName())
                        .data(pet)
                        .build());
    }
}

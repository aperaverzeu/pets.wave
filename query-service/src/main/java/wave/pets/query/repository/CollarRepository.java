package wave.pets.query.repository;

import io.r2dbc.spi.Row;
import io.r2dbc.spi.RowMetadata;
import lombok.RequiredArgsConstructor;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import wave.pets.query.repository.spi.Repository;
import wave.pets.utilities.entity.CollarEntity;

import java.util.UUID;
import java.util.function.BiFunction;

@Component
@RequiredArgsConstructor
public class CollarRepository implements Repository<CollarEntity> {
    private final DatabaseClient databaseClient;

    public static final BiFunction<Row, RowMetadata, CollarEntity> MAPPING_FUNCTION = (row, rowMetaData) ->
            CollarEntity.builder()
                    .id(row.get("id", UUID.class))
                    .model(row.get("model", String.class))
                    .petId(row.get("pet_id", UUID.class))
                    .build();

    private static final String findCollarByIdSQL =
            "SELECT * FROM public.\"collar\" WHERE id = :id";

    private static final String findAllCollarSQL =
            "SELECT * FROM public.\"collar\"";

    @Override
    public Mono<CollarEntity> findById(UUID id) {
        return databaseClient.sql(findCollarByIdSQL)
                .bind("id", id)
                .map(MAPPING_FUNCTION)
                .one();
    }

    @Override
    public Flux<CollarEntity> findAll() {
        return databaseClient.sql(findAllCollarSQL)
                .filter((statement, executeFunction) -> statement.fetchSize(10).execute())
                .map(MAPPING_FUNCTION)
                .all();
    }
}

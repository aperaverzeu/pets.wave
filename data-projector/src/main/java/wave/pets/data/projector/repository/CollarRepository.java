package wave.pets.data.projector.repository;


import lombok.RequiredArgsConstructor;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import wave.pets.utilities.entity.CollarEntity;
import wave.pets.data.projector.repository.spi.Repository;

import java.util.Objects;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class CollarRepository implements Repository<CollarEntity> {
    private final DatabaseClient databaseClient;

    private static final String createCollarSQL =
            "INSERT INTO public.\"collar\" (id, model, petId) VALUES (:id, :model, :petId)";

    private static final String updateCollarSQL =
            "UPDATE public.\"collar\" " +
                    "SET id = :id, model = :model, petId = :petId, height = :height, " +
                    "WHERE id = :id";

    @Override
    public Mono<UUID> save(CollarEntity collarEntity) {
        return databaseClient.sql(createCollarSQL)
                .bind("id", Objects.requireNonNull(collarEntity.getId()))
                .bind("model", collarEntity.getModel())
                .bind("petId", Objects.requireNonNull(collarEntity.getPetId()))
                .fetch()
                .first()
                .map(r -> (UUID) r.get("id"));
    }

    @Override
    public Mono<Long> update(CollarEntity collarEntity) {
        return databaseClient.sql(updateCollarSQL)
                .bind("id", Objects.requireNonNull(collarEntity.getId()))
                .bind("model", collarEntity.getModel())
                .bind("petId", Objects.requireNonNull(collarEntity.getPetId()))
                .fetch()
                .rowsUpdated();
    }

    @Override
    public Mono<Long> delete(CollarEntity userEntity) {
        throw new UnsupportedOperationException();
    }
}

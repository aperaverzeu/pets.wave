package wave.pets.data.projector.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import wave.pets.utilities.entity.PetEntity;
import wave.pets.data.projector.repository.spi.Repository;

import java.util.Objects;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class PetRepository implements Repository<PetEntity> {
    private final DatabaseClient databaseClient;

    private static final String createUserSQL =
            "INSERT INTO public.\"pet\" (id, name, weight, height, age, petType, userId) " +
                    "VALUES (:id, :name, :weight, :height, :age, :petType, :userId)";

    private static final String updateUserSQL =
            "UPDATE public.\"pet\" " +
                    "SET id = :id, name = :name, weight = :weight, height = :height, " +
                    "age = :age, petType = :petType, userId = :userId " +
                    "WHERE id = :id";

    @Override
    public Mono<UUID> save(PetEntity petEntity) {
        return databaseClient.sql(createUserSQL)
                .bind("id", Objects.requireNonNull(petEntity.getId()))
                .bind("name", petEntity.getName())
                .bind("weight", petEntity.getWeight())
                .bind("height", petEntity.getHeight())
                .bind("age", petEntity.getAge())
                .bind("petType", petEntity.getPetType())
                .bind("userId", petEntity.getUserId())
                .fetch()
                .first()
                .map(r -> (UUID) r.get("id"));
    }

    @Override
    public Mono<Long> update(PetEntity petEntity) {
        return databaseClient.sql(updateUserSQL)
                .bind("id", Objects.requireNonNull(petEntity.getId()))
                .bind("name", petEntity.getName())
                .bind("weight", petEntity.getWeight())
                .bind("height", petEntity.getHeight())
                .bind("age", petEntity.getAge())
                .bind("petType", petEntity.getPetType())
                .bind("userId", petEntity.getUserId())
                .fetch()
                .rowsUpdated();
    }

    @Override
    public Mono<Long> delete(PetEntity petEntity) {
        throw new UnsupportedOperationException();
    }
}

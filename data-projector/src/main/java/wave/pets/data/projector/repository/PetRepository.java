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
            "INSERT INTO public.\"pet\" (id, name, weight, height, age, pet_type, user_id, collar_id) " +
                    "VALUES (:id, :name, :weight, :height, :age, :pet_type, :user_id, :collar_id)";

    private static final String updateUserSQL =
            "UPDATE public.\"pet\" " +
                    "SET id = :id, name = :name, weight = :weight, height = :height, " +
                    "age = :age, pet_type = :pet_type, user_id = :user_id, collar_id = :collar_id " +
                    "WHERE id = :id";

    @Override
    public Mono<UUID> save(PetEntity petEntity) {
        return databaseClient.sql(createUserSQL)
                .bind("id", Objects.requireNonNull(petEntity.getId()))
                .bind("name", petEntity.getName())
                .bind("weight", petEntity.getWeight())
                .bind("height", petEntity.getHeight())
                .bind("age", petEntity.getAge())
                .bind("pet_type", petEntity.getPetType())
                .bind("user_id", petEntity.getUserId())
                .bind("collar_id", petEntity.getCollarId())
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
                .bind("pet_type", petEntity.getPetType())
                .bind("user_id", petEntity.getUserId())
                .bind("collar_id", petEntity.getCollarId())
                .fetch()
                .rowsUpdated();
    }

    @Override
    public Mono<Long> delete(PetEntity petEntity) {
        throw new UnsupportedOperationException();
    }
}

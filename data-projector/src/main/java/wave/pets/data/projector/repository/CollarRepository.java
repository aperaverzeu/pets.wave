package wave.pets.data.projector.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import wave.pets.data.projector.model.entity.CollarEntity;

import java.util.UUID;

@Repository
public interface CollarRepository extends ReactiveCrudRepository<CollarEntity, UUID> {}

package wave.pets.data.publisher.repository;

import org.springframework.data.cassandra.repository.ReactiveCassandraRepository;
import org.springframework.stereotype.Repository;
import wave.pets.data.publisher.model.entity.CollarEventEntity;

import java.util.UUID;

@Repository
public interface CollarEventRepository extends ReactiveCassandraRepository<CollarEventEntity, UUID> {}

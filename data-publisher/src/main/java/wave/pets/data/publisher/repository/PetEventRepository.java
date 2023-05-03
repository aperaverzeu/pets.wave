package wave.pets.data.publisher.repository;

import org.springframework.data.cassandra.repository.ReactiveCassandraRepository;
import org.springframework.stereotype.Repository;
import wave.pets.data.publisher.model.event.PetEvent;

import java.util.UUID;

@Repository
public interface PetEventRepository extends ReactiveCassandraRepository<PetEvent, UUID> {}

package wave.pets.data.publisher.repository;

import org.springframework.data.cassandra.repository.ReactiveCassandraRepository;
import org.springframework.stereotype.Repository;
import wave.pets.data.publisher.model.entity.MessageEventEntity;

import java.util.UUID;

@Repository
public interface MessageEventRepository extends ReactiveCassandraRepository<MessageEventEntity, UUID> {}

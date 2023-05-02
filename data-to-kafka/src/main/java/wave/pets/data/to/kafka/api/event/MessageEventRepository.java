package wave.pets.data.to.kafka.api.event;

import org.springframework.data.cassandra.repository.ReactiveCassandraRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface MessageEventRepository extends ReactiveCassandraRepository<MessageEvent, UUID> {}

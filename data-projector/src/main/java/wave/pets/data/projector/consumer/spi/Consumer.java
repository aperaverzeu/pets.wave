package wave.pets.data.projector.consumer.spi;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import reactor.core.publisher.Mono;
import wave.pets.utilities.event.UserEvent;

import java.util.UUID;

@Slf4j
public abstract class Consumer<T, E> {
    protected abstract void publishToDataStore(E event);

    protected abstract Mono<UUID> create(T entity);

    protected abstract Mono<Long> update(T entity);

    protected abstract Mono<Long> delete(T entity);

    public void logConsumerRecord(ConsumerRecord<String, E> consumerRecord) {
        log.info("received key={}, value={} from topic={}, offset={}",
                consumerRecord.key(),
                consumerRecord.value(),
                consumerRecord.topic(),
                consumerRecord.offset());
    }
}

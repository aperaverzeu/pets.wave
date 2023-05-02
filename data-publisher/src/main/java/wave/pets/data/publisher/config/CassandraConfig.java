package wave.pets.data.publisher.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.cassandra.config.AbstractReactiveCassandraConfiguration;
import org.springframework.data.cassandra.repository.config.EnableReactiveCassandraRepositories;
import org.springframework.lang.NonNull;
import wave.pets.data.publisher.model.MessageEvent;

import java.util.Objects;

@Configuration
@EnableReactiveCassandraRepositories(basePackageClasses = {MessageEvent.class})
public class CassandraConfig extends AbstractReactiveCassandraConfiguration {
    @Value("${spring.cassandra.keyspace-name}")
    private String keyspaceName;

    @Override
    @NonNull
    protected String getKeyspaceName() {
        return Objects.requireNonNullElse(keyspaceName, "petswave");
    }
}

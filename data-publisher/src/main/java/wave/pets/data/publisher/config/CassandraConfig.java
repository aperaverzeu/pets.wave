package wave.pets.data.publisher.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.cassandra.config.AbstractReactiveCassandraConfiguration;
import org.springframework.data.cassandra.repository.config.EnableReactiveCassandraRepositories;
import org.springframework.lang.NonNull;

import java.util.Objects;

@Configuration
@EnableReactiveCassandraRepositories(basePackages = {"wave.pets.data.publisher.model.entity"})
public class CassandraConfig extends AbstractReactiveCassandraConfiguration {
    @Value("${spring.cassandra.keyspace-name}")
    private String keyspaceName;

    @Value("${spring.cassandra.contact-points}")
    private String contactPoints;

    @Override
    @NonNull
    protected String getKeyspaceName() {
        return Objects.requireNonNullElse(keyspaceName, "petswave");
    }

    @Override
    @NonNull
    protected String getContactPoints() {
        return Objects.requireNonNullElse(contactPoints, "localhost");
    }
}

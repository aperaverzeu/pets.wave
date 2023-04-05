package wave.pets.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.cassandra.config.AbstractCassandraConfiguration;
import org.springframework.data.cassandra.repository.config.EnableCassandraRepositories;
import org.springframework.lang.NonNull;

import java.util.Objects;

@Configuration
@PropertySource(value = {"classpath:application.yml"})
@EnableCassandraRepositories(basePackages = "wave.pets.repository")
@RequiredArgsConstructor
public class CassandraConfiguration extends AbstractCassandraConfiguration {
    private final Environment environment;

    @Override
    @NonNull
    protected String getKeyspaceName() {
        return Objects.requireNonNull(environment.getProperty("spring.cassandra.keyspace-name"));
    }
}

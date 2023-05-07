package wave.pets.data.projector.config;

import io.r2dbc.spi.ConnectionFactories;
import io.r2dbc.spi.ConnectionFactory;
import io.r2dbc.spi.ConnectionFactoryOptions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

import static io.r2dbc.pool.PoolingConnectionFactoryProvider.INITIAL_SIZE;
import static io.r2dbc.pool.PoolingConnectionFactoryProvider.MAX_SIZE;
import static io.r2dbc.pool.PoolingConnectionFactoryProvider.MAX_IDLE_TIME;
import static io.r2dbc.spi.ConnectionFactoryOptions.DATABASE;
import static io.r2dbc.spi.ConnectionFactoryOptions.DRIVER;
import static io.r2dbc.spi.ConnectionFactoryOptions.HOST;
import static io.r2dbc.spi.ConnectionFactoryOptions.PORT;
import static io.r2dbc.spi.ConnectionFactoryOptions.USER;
import static io.r2dbc.spi.ConnectionFactoryOptions.PASSWORD;

@Configuration
public class R2dbcConfig {
    @Bean
    ConnectionFactory connectionFactory() {
        return ConnectionFactories.get(
                ConnectionFactoryOptions.builder()
                        .option(DRIVER, "postgresql")
                        .option(HOST, "localhost")
                        .option(PORT, 5433)
                        .option(USER, "user")
                        .option(PASSWORD, "Pa55word")
                        .option(DATABASE, "petswave")
                        .option(INITIAL_SIZE, 10)
                        .option(MAX_IDLE_TIME, Duration.ofMinutes(1L))
                        .option(MAX_SIZE, 30)
                        .build());
    }
}

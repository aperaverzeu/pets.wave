package wave.pets.query;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import wave.pets.config.CassandraConfiguration;

@SpringBootApplication
@Import(CassandraConfiguration.class)
public class QueryServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(QueryServiceApplication.class, args);
    }
}

package wave.pets.data.to.cassandra;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import wave.pets.config.CassandraConfiguration;

@SpringBootApplication
@Import(CassandraConfiguration.class)
public class DataToCassandraApplication {
    public static void main(String[] args) {
        SpringApplication.run(DataToCassandraApplication.class, args);
    }
}

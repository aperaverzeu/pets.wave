package wave.pets.data.projector;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;

@SpringBootApplication
@EnableR2dbcRepositories
public class DataProjectorApplication {
    public static void main(String[] args) {
        SpringApplication.run(DataProjectorApplication.class, args);
    }
}
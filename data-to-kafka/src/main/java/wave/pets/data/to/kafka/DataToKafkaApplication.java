package wave.pets.data.to.kafka;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(
        title = "Data to Kafka service",
        version = "1.0",
        description = "Sample Docs for producing data from resources"))
public class DataToKafkaApplication {
    public static void main(String[] args) {
        SpringApplication.run(DataToKafkaApplication.class, args);
    }
}

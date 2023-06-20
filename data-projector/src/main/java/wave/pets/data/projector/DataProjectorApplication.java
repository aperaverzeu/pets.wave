package wave.pets.data.projector;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class DataProjectorApplication {
    public static void main(String[] args) {
        SpringApplication.run(DataProjectorApplication.class, args);
    }
}

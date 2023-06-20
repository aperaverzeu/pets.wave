package wave.pets.data.publisher;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class DataPublisherApplication {
    public static void main(String[] args) {
        SpringApplication.run(DataPublisherApplication.class, args);
    }
}

package wave.pets.data.to.kafka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.cassandra.CassandraAutoConfiguration;
import org.springframework.boot.autoconfigure.data.cassandra.CassandraDataAutoConfiguration;

@SpringBootApplication(exclude = {CassandraDataAutoConfiguration.class, CassandraAutoConfiguration.class})
public class DataToKafkaApplication {
    public static void main(String[] args) {
        SpringApplication.run(DataToKafkaApplication.class, args);
    }
}

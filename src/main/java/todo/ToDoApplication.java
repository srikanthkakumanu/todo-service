package todo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
public class ToDoApplication {
    public static void main(String... args) {
        SpringApplication.run(ToDoApplication.class, args);
    }
}

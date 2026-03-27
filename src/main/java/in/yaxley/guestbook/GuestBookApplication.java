package in.yaxley.guestbook;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class GuestBookApplication {

    public static void main(String[] args) {
        SpringApplication.run(GuestBookApplication.class, args);
    }

    @Bean
    public CommandLineRunner helloRunner() {
        return _ -> {
            System.out.println("Hello, world!");
        };
    }
}

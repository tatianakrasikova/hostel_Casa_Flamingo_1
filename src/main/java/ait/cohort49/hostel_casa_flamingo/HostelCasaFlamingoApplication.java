package ait.cohort49.hostel_casa_flamingo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class HostelCasaFlamingoApplication {

    public static void main(String[] args) {
        SpringApplication.run(HostelCasaFlamingoApplication.class, args);
    }

}

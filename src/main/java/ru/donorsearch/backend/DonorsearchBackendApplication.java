package ru.donorsearch.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;

@SpringBootApplication
public class DonorsearchBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(DonorsearchBackendApplication.class, args);
    }

}

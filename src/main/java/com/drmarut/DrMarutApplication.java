package com.drmarut;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
public class DrMarutApplication {

    public static void main(String[] args) {
        SpringApplication.run(DrMarutApplication.class, args);
    }
}

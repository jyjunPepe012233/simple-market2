package com.jyjun.simplemarket2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class SimpleMarket2Application {

    public static void main(String[] args) {
        SpringApplication.run(SimpleMarket2Application.class, args);
    }

}

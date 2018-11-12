package com.eixampledb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class EixampledbApiApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext run = SpringApplication.run(EixampledbApiApplication.class, args);
    }

}

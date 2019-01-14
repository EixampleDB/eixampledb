package com.eixampledb;

import com.eixampledb.api.monitoring.LogStorageServiceImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class EixampledbApiApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext run = SpringApplication.run(EixampledbApiApplication.class, args);

    }

}

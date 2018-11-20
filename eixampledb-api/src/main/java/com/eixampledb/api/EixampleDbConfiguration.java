package com.eixampledb.api;

import com.eixampledb.core.EixampleDbBuilder;
import com.eixampledb.core.api.EixampleDb;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EixampleDbConfiguration {

    @Bean
    public EixampleDb eixampleDb() {
        return EixampleDbBuilder.builder()
                .withMapBackend()
                .build();
    }

}

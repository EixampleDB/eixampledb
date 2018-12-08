package com.eixampledb.api;

import com.eixampledb.api.monitoring.middleware.HitMonitoringMiddleware;
import com.eixampledb.core.EixampleDbBuilder;
import com.eixampledb.core.api.EixampleDb;
import com.eixampledb.api.monitoring.middleware.LatencyMonitoringMiddleware;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class EixampleDbConfiguration implements WebMvcConfigurer {

    @Bean
    public EixampleDb eixampleDb() {
        return EixampleDbBuilder.builder()
                .withMapBackend()
                .build();
    }

    @Bean
    public LatencyMonitoringMiddleware latencyMonitoringMiddleware() {
        return new LatencyMonitoringMiddleware();
    }

    @Bean
    public HitMonitoringMiddleware hitMonitoringMiddleware() {
        return new HitMonitoringMiddleware();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // Latency middleware
        registry.addInterceptor(latencyMonitoringMiddleware())
                .excludePathPatterns("/monitoring/*");

        registry.addInterceptor(hitMonitoringMiddleware())
                .excludePathPatterns("/monitoring/*");
    }

}

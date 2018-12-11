package com.eixampledb.api.monitoring.middleware;

import com.eixampledb.api.monitoring.PerformanceMonitoringService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class OPMMonitoringMiddleware implements HandlerInterceptor {
    @Autowired
    PerformanceMonitoringService performanceMonitoringService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        performanceMonitoringService.increaseOperationCounter();
        return true;
    }

}
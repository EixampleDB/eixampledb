package com.eixampledb.api.monitoring.middleware;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.eixampledb.api.monitoring.PerformanceMonitoringService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

@Component
public class LatencyMonitoringMiddleware implements HandlerInterceptor {
    @Autowired
    PerformanceMonitoringService performanceMonitoringService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        long startTime = System.currentTimeMillis();
        System.out.println("Start Time: " + System.currentTimeMillis());
        request.setAttribute("startTime", startTime);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        System.out.println("Post Handle method is Calling");
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception exception) throws Exception {
        long startTime = (Long) request.getAttribute("startTime");
        long endTime = System.currentTimeMillis();

        performanceMonitoringService.updateLatency(endTime - startTime);
        System.out.println("Latency: " + (performanceMonitoringService.getLatency()));
    }
}
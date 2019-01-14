package com.eixampledb.api.monitoring.middleware;

import com.eixampledb.api.monitoring.PerformanceMonitoringService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class HitMonitoringMiddleware implements HandlerInterceptor {
    @Autowired
    PerformanceMonitoringService performanceMonitoringService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

        if (request.getMethod().equals("GET")) {
            if (response.getStatus() == 200) {
                performanceMonitoringService.addHit(true);
            } else {
                performanceMonitoringService.addHit(false);
            }
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception exception) throws Exception {
    }
}
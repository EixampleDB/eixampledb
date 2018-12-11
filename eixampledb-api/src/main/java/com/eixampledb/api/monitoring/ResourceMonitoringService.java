package com.eixampledb.api.monitoring;

public interface ResourceMonitoringService {
    float getCpuUsage();
    float getRamUsage();
    float getDiskUsage();
}

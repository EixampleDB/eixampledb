package com.eixampledb.api.monitoring;

public interface ResourceMonitoringService {
    float getCpuUsage();
    void updateCpuUsage(float usage);

    float getRamUsage();
    void updateRamUsage(float usage);

    float getDiskUsage();
    void updateDiskUsage(float usage);
}

package com.eixampledb.api.monitoring;
import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicInteger;

@Service
public class ResourceMonitoringServiceImpl implements ResourceMonitoringService{

    private volatile float cpuUsage = 0;
    private volatile float ramUsage = 0;
    private volatile float diskUsage = 0;


    @Override
    public float getCpuUsage() {
        return cpuUsage;
    }

    @Override
    public synchronized void updateCpuUsage(float usage) {
        cpuUsage = usage;
    }

    @Override
    public float getRamUsage() {
        return ramUsage;
    }

    @Override
    public synchronized void updateRamUsage(float usage) {
        ramUsage = usage;
    }

    @Override
    public float getDiskUsage() {
        return diskUsage;
    }

    @Override
    public synchronized void updateDiskUsage(float usage) {
        diskUsage = usage;
    }



}

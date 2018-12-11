package com.eixampledb.api.monitoring;
import org.springframework.stereotype.Service;

import java.io.File;
import java.lang.management.ManagementFactory;
import com.sun.management.OperatingSystemMXBean;

@Service
public class ResourceMonitoringServiceImpl implements ResourceMonitoringService{

    private OperatingSystemMXBean systemBean = (com.sun.management.OperatingSystemMXBean) ManagementFactory
            .getOperatingSystemMXBean();

    @Override
    public float getCpuUsage() {
        return (float)systemBean.getSystemCpuLoad();
    }

    @Override
    public float getRamUsage() {
        double totalUsed = systemBean.getTotalPhysicalMemorySize() - systemBean.getFreePhysicalMemorySize();
        float percent_used = (float) totalUsed / systemBean.getTotalPhysicalMemorySize();
        return percent_used;
    }

    @Override
    public float getDiskUsage() {
        File baseFile = new File("/");
        long total_space = baseFile.getTotalSpace();
        long free_space = baseFile.getUsableSpace();

        return (float) free_space / total_space;
    }

}

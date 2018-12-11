package com.eixampledb.api;

import com.eixampledb.api.monitoring.PerformanceMonitoringService;
import com.eixampledb.api.monitoring.ResourceMonitoringService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequiredArgsConstructor
@RequestMapping("/monitor")
public class MonitoringController {
    @Autowired
    private PerformanceMonitoringService performanceMonitoringService;

    @Autowired
    private ResourceMonitoringService resourceMonitoringService;

    @RequestMapping(path = "/used_memory", method = RequestMethod.GET)
    public ResponseEntity<Float> getUsedMemory() {
        return new ResponseEntity<>(resourceMonitoringService.getRamUsage(), HttpStatus.OK);
    }

    @RequestMapping(path = "/used_cpu", method = RequestMethod.GET)
    public ResponseEntity<Float> getUsedCPU() {
        return new ResponseEntity<>(resourceMonitoringService.getCpuUsage(), HttpStatus.OK);
    }

    @RequestMapping(path = "/disk_free_space", method = RequestMethod.GET)
    public ResponseEntity<Float> getFreeDiskSpace() {
        return new ResponseEntity<>(resourceMonitoringService.getDiskUsage(), HttpStatus.OK);
    }

    @RequestMapping(path = "/latency", method = RequestMethod.GET)
    public ResponseEntity<Float> getLatency() {
        return new ResponseEntity<>(performanceMonitoringService.getLatency(), HttpStatus.OK);
    }

    @RequestMapping(path = "/opm", method = RequestMethod.GET)
    public ResponseEntity<Integer> getOPM() {
        return new ResponseEntity<>(performanceMonitoringService.getOPM(), HttpStatus.OK);
    }

    @RequestMapping(path = "/hit_rate", method = RequestMethod.GET)
    public ResponseEntity<Float> getHitRate() {
        return new ResponseEntity<>(performanceMonitoringService.getHitRate(), HttpStatus.OK);
    }

}

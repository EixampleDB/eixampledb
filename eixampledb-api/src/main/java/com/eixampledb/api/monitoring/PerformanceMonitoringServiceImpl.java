package com.eixampledb.api.monitoring;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicInteger;

@Service
public class PerformanceMonitoringServiceImpl implements PerformanceMonitoringService{
    private volatile float maxLastMinuteLatency = 0;
    private final AtomicInteger currentOPM = new AtomicInteger(0);
    private volatile float currentHitRate = 0;
    private final AtomicInteger totalReq = new AtomicInteger(0);

    @Autowired
    private LogStorageService logStorageService;

    @Override
    public float getLatency() {
        return maxLastMinuteLatency;
    }

    @Override
    public synchronized void updateLatency(float elapsedTime) {
        maxLastMinuteLatency = maxLastMinuteLatency < elapsedTime ? elapsedTime : maxLastMinuteLatency;
    }

    @Override
    public int getOPM() {
        return currentOPM.get();
    }

    @Override
    public void increaseOperationCounter() {
        currentOPM.incrementAndGet();
    }

    @Override
    public float getHitRate() {
        return currentHitRate;
    }

    @Override
    public void addHit(boolean hit) {
        int hitInt = hit? 1 : 0;
        int currentAmount = totalReq.getAndIncrement();
        float newHitRate = currentHitRate + ((hitInt - currentHitRate)/totalReq.get());
        setHitRate(newHitRate);
    }

    @Scheduled(cron = "*/59 * * * * *")
    private void storeData(){
        logStorageService.infoMessage("Latency: " + Float.toString(maxLastMinuteLatency) + " ms");
        logStorageService.infoMessage("OPM: " + currentOPM.toString() );
        logStorageService.infoMessage("Hit Rate: " + Float.toString(currentHitRate*100) + "%");

        maxLastMinuteLatency = 0;
        currentOPM.set(0);
        currentHitRate = 0;
        totalReq.set(0);
    }


    private synchronized void setHitRate(float hitRate) {
        currentHitRate = hitRate;
    }
}

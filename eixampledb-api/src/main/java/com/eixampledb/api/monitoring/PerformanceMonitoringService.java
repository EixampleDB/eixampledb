package com.eixampledb.api.monitoring;

public interface PerformanceMonitoringService {
    public abstract float getLatency();
    public abstract void updateLatency(float elapsedTime);

    public abstract int getOPM();
    public abstract void increaseOperationCounter();

    public abstract float getHitRate();
    public abstract void addHit(boolean hit);
}

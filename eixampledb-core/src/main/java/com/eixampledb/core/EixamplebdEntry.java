package com.eixampledb.core;

public class EixamplebdEntry {

    private final String key;
    private final String value;
    private final long creationTimestamp;
    private final long lastupdateTimestamp;

    public EixamplebdEntry(String key, String value, long creationTimestamp, long lastupdateTimestamp) {
        this.key = key;
        this.value = value;
        this.creationTimestamp = creationTimestamp;
        this.lastupdateTimestamp = lastupdateTimestamp;
    }

    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }

    public long getCreationTimestamp() {
        return creationTimestamp;
    }

    public long getLastupdateTimestamp() {
        return lastupdateTimestamp;
    }

}

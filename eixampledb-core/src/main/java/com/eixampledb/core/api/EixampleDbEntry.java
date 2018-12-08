package com.eixampledb.core.api;

public class EixampleDbEntry {

    private final String key;
    private final String value;
    private final long creationTimestamp;
    private final long lastupdateTimestamp;
    private final int type;

    public EixampleDbEntry(String key, String value, long creationTimestamp, long lastupdateTimestamp, int type) {
        this.key = key;
        this.value = value;
        this.creationTimestamp = creationTimestamp;
        this.lastupdateTimestamp = lastupdateTimestamp;
        this.type = type;
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

    public int getType(){ return this.type; }
}

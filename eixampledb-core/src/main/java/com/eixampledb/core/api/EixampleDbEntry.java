package com.eixampledb.core.api;


public class EixampleDbEntry {

    private final String key;
    private final Object value;
    private final long creationTimestamp;
    private final long lastupdateTimestamp;
    private final ValueType type;

    public EixampleDbEntry(String key, Object value, long creationTimestamp, long lastupdateTimestamp, ValueType type) {
        this.key = key;
        this.value = value;
        this.creationTimestamp = creationTimestamp;
        this.lastupdateTimestamp = lastupdateTimestamp;
        this.type = type;
    }

    public String getKey() {
        return key;
    }

    public Object getValue() {
        return value;
    }

    public long getCreationTimestamp() {
        return creationTimestamp;
    }

    public long getLastupdateTimestamp() {
        return lastupdateTimestamp;
    }

    public ValueType getType(){ return this.type; }
}

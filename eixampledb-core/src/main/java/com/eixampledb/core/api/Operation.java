package com.eixampledb.core.api;

public enum Operation {

    INCR,
    DECR;

    public boolean isIncrement() {
        return INCR.equals(this);
    }
    public boolean isDecrement() {
        return DECR.equals(this);
    }

}

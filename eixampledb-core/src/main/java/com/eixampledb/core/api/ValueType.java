package com.eixampledb.core.api;

public enum ValueType {
    STR,
    NUM;

    public boolean isString() {
        return STR.equals(this);
    }
    public boolean isNumber() {
        return NUM.equals(this);
    }
}

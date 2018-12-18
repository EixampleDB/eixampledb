package com.eixampledb.core.api;

public enum SearchType {
    DEF,
    STARTS,
    REGEX;

    public boolean isStarts() {return STARTS.equals(this); }
    public boolean isRegex() {return REGEX.equals(this); }

}
package com.eixampledb.core.impl;

import com.eixampledb.core.api.OperationValueTypeException;

public class NumberUtils {

    private NumberUtils() {}

    public static Number parse(String value) {
        try {
            return Long.valueOf(value);
        } catch (NumberFormatException ignored) { }
        try {
            return Double.valueOf(value);
        } catch (NumberFormatException ignored) { }
        throw new OperationValueTypeException("Can not parse " + value + " as number");
    }
    public static Number incr(Object object) {
        if (! (object instanceof Number)) {
            throw new OperationValueTypeException("Value " + object + " was not a number");
        }
        Number number = (Number) object;
        if (number instanceof Double) {
            return number.doubleValue() + 1.0;
        } else if (number instanceof Long) {
            return number.longValue() + 1;
        }
        throw new OperationValueTypeException("Can not process " + number);
    }

    public static Number decr(Object object) {
        if (! (object instanceof Number)) {
            throw new OperationValueTypeException("Value " + object + " was not a number");
        }
        Number number = (Number) object;
        if (number instanceof Double) {
            return number.doubleValue() - 1.0;
        } else if (number instanceof Long) {
            return number.longValue() - 1;
        }
        throw new OperationValueTypeException("Can not process " + number);
    }
}

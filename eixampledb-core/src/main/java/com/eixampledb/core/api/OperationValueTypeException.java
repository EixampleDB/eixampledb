package com.eixampledb.core.api;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class OperationValueTypeException extends RuntimeException {
    public OperationValueTypeException() {
    }

    public OperationValueTypeException(String message) {
        super(message);
    }

    public OperationValueTypeException(String message, Throwable cause) {
        super(message, cause);
    }

    public OperationValueTypeException(Throwable cause) {
        super(cause);
    }

    public OperationValueTypeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}

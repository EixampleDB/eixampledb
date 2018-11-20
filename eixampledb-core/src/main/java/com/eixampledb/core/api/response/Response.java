package com.eixampledb.core.api.response;

import com.eixampledb.core.api.request.Request;
import lombok.Getter;

@Getter
public abstract class Response<R extends Request> {

    private final R request;
    private final boolean success;

    Response(R request, boolean success) {
        this.request = request;
        this.success = success;
    }
}

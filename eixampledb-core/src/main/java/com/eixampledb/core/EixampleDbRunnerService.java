package com.eixampledb.core;

import com.eixampledb.core.api.EixampleDb;
import com.eixampledb.core.api.EixampleDbBackend;
import com.eixampledb.core.api.Middleware;
import com.eixampledb.core.api.request.DeleteRequest;
import com.eixampledb.core.api.request.GetRequest;
import com.eixampledb.core.api.request.SetRequest;
import com.eixampledb.core.api.response.DeleteResponse;
import com.eixampledb.core.api.response.GetResponse;
import com.eixampledb.core.api.response.SetResponse;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class EixampleDbRunnerService implements EixampleDb {

    private final List<Middleware> middlewares;
    private final EixampleDbBackend backend;

    @Override
    public GetResponse get(GetRequest getRequest) {
        for (Middleware middleware : middlewares) {
            middleware.beforeGet(getRequest);
        }
        GetResponse getResponse = backend.get(getRequest);
        for (Middleware middleware : middlewares) {
            middleware.afterGet(getResponse);
        }
        return getResponse;
    }

    @Override
    public SetResponse set(SetRequest setRequest) {
        for (Middleware middleware : middlewares) {
            middleware.beforeSet(setRequest);
        }
        SetResponse setResponse = backend.set(setRequest);
        for (Middleware middleware : middlewares) {
            middleware.afterSet(setResponse);
        }
        return setResponse;
    }

    @Override
    public DeleteResponse delete(DeleteRequest deleteRequest) {
        for (Middleware middleware : middlewares) {
            middleware.beforeDelete(deleteRequest);
        }
        DeleteResponse deleteResponse = backend.delete(deleteRequest);
        for (Middleware middleware : middlewares) {
            middleware.afterDelete(deleteResponse);
        }
        return deleteResponse;
    }
}

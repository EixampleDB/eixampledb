package com.eixampledb.core;

import com.eixampledb.core.api.EixampleDb;
import com.eixampledb.core.api.EixampleDbBackend;
import com.eixampledb.core.api.Middleware;
import com.eixampledb.core.api.request.*;
import com.eixampledb.core.api.response.*;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Function;

@RequiredArgsConstructor
public class EixampleDbRunnerService implements EixampleDb {

    private final List<Middleware> middlewares;
    private final EixampleDbBackend backend;

    @Override
    public GetResponse get(GetRequest getRequest) {
        return exec(getRequest, Middleware::beforeGet, Middleware::afterGet, backend::get);
    }

    @Override
    public SetResponse set(SetRequest setRequest) {
        return exec(setRequest, Middleware::beforeSet, Middleware::afterSet, backend::set);
    }

    @Override
    public IncrResponse incr(IncrRequest incrRequest) {
        return exec(incrRequest, Middleware::beforeIncr, Middleware::afterIncr, backend::incr);
    }

    @Override
    public DecrResponse decr(DecrRequest decrRequest) {
        return exec(decrRequest, Middleware::beforeDecr, Middleware::afterDecr, backend::decr);
    }

    @Override
    public DeleteResponse delete(DeleteRequest deleteRequest) {
        return exec(deleteRequest, Middleware::beforeDelete, Middleware::afterDelete, backend::delete);
    }

    private <RQ,RP> RP exec(RQ request, BiConsumer<Middleware, RQ> before, BiConsumer<Middleware, RP> after, Function<RQ,RP> delegate) {
        for (Middleware middleware : middlewares) {
            before.accept(middleware, request);
        }
        RP response = delegate.apply(request);
        for (Middleware middleware : middlewares) {
            after.accept(middleware, response);
        }
        return response;
    }

}

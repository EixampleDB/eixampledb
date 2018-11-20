package com.eixampledb.core;

import com.eixampledb.core.api.EixampleDb;
import com.eixampledb.core.api.EixampleDbBackend;
import com.eixampledb.core.api.Middleware;
import com.eixampledb.core.impl.EixampleDbMapImplementation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class EixampleDbBuilder {

    private EixampleDbBackend backend;
    private List<Middleware> middlewares = new ArrayList<>();

    public EixampleDbBuilder withBacked(EixampleDbBackend backend) {
        this.backend = backend;
        return this;
    }

    public EixampleDbBuilder withMapBackend() {
        return withBacked(new EixampleDbMapImplementation());
    }

    public EixampleDbBuilder withMiddlewares(Middleware ... newMiddlewares) {
        middlewares.addAll(Arrays.asList(newMiddlewares));
        return this;
    }

    public EixampleDb build() {
        if (backend == null) {
            throw new NullPointerException("Can not build a EixampleDb instance without a backend. Use withBackend to add one.");
        }
        return new EixampleDbRunnerService(middlewares, backend);
    }

    public static EixampleDbBuilder builder() {
        return new EixampleDbBuilder();
    }

}

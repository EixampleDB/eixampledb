package com.eixampledb.core.api;

import com.eixampledb.core.api.request.SetRequest;
import com.eixampledb.core.api.response.SetResponse;

public interface CoreServices {
    static final int NUM_TYPE = 1;

    void operation_increment(String key, String value);

    void operation_decrement(String key, String value);
}

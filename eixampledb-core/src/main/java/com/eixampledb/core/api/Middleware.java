package com.eixampledb.core.api;

import com.eixampledb.core.api.request.DeleteRequest;
import com.eixampledb.core.api.request.GetRequest;
import com.eixampledb.core.api.request.SetRequest;
import com.eixampledb.core.api.response.DeleteResponse;
import com.eixampledb.core.api.response.GetResponse;
import com.eixampledb.core.api.response.SetResponse;

public interface Middleware {

    void beforeGet(GetRequest getRequest);
    void afterGet(GetResponse getResponse);

    void beforeSet(SetRequest setRequest);
    void afterSet(SetResponse setResponse);

    void beforeDelete(DeleteRequest deleteRequest);
    void afterDelete(DeleteResponse deleteResponse);

}

package com.eixampledb.core.api;

import com.eixampledb.core.api.request.*;
import com.eixampledb.core.api.response.*;

public interface Middleware {

    void beforeGet(GetRequest getRequest);
    void afterGet(GetResponse getResponse);

    void beforeSet(SetRequest setRequest);
    void afterSet(SetResponse setResponse);

    void beforeDelete(DeleteRequest deleteRequest);
    void afterDelete(DeleteResponse deleteResponse);

    void beforeIncr(IncrRequest request);
    void afterIncr(IncrResponse response);

    void beforeDecr(DecrRequest request);
    void afterDecr(DecrResponse response);
    
}

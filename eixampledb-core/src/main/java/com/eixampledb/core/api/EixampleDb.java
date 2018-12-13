package com.eixampledb.core.api;

import com.eixampledb.core.api.request.*;
import com.eixampledb.core.api.response.*;

public interface EixampleDb {

    GetResponse get(GetRequest getRequest);

    SetResponse set(SetRequest setRequest);

    IncrResponse incr(IncrRequest incrRequest);

    DecrResponse decr(DecrRequest decrRequest);

    DeleteResponse delete(DeleteRequest deleteRequest);

}

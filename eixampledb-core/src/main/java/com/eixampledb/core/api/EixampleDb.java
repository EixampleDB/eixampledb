package com.eixampledb.core.api;

import com.eixampledb.core.api.request.DeleteRequest;
import com.eixampledb.core.api.request.GetRequest;
import com.eixampledb.core.api.request.SetRequest;
import com.eixampledb.core.api.response.DeleteResponse;
import com.eixampledb.core.api.response.GetResponse;
import com.eixampledb.core.api.response.SetResponse;

public interface EixampleDb {

    GetResponse get(GetRequest getRequest);

    SetResponse set(SetRequest setRequest);

    DeleteResponse delete(DeleteRequest deleteRequest);

}

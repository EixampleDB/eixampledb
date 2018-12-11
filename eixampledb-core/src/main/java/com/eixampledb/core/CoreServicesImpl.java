package com.eixampledb.core;

import com.eixampledb.core.api.CoreServices;
import com.eixampledb.core.api.EixampleDb;
import com.eixampledb.core.api.request.SetRequest;
import com.eixampledb.core.api.response.SetResponse;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CoreServicesImpl implements CoreServices {
    private final EixampleDb eixampledb;

    public void operation_increment(String key, String value){

        if(value.contains(".")){
            value = (Double.parseDouble(value)+1.) + "";
        }else{
            value = (Long.parseLong(value)+1) + "";
        }

        SetResponse setResponse = eixampledb.set(new SetRequest(key, value, NUM_TYPE));
    }

    public void operation_decrement(String key, String value){

        if(value.contains(".")){
            value = (Double.parseDouble(value)-1.) + "";
        }else{
            value = (Long.parseLong(value)-1) + "";
        }

        SetResponse setResponse = eixampledb.set(new SetRequest(key, value, NUM_TYPE));
    }
}

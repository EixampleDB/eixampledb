package com.eixampledb.core.impl;

import com.eixampledb.core.api.EixampleDbBackend;
import com.eixampledb.core.api.EixampleDbEntry;
import com.eixampledb.core.api.request.DeleteRequest;
import com.eixampledb.core.api.request.GetRequest;
import com.eixampledb.core.api.request.SetRequest;
import com.eixampledb.core.api.response.DeleteResponse;
import com.eixampledb.core.api.response.GetResponse;
import com.eixampledb.core.api.response.SetResponse;

import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class EixampleDbMapImplementation implements EixampleDbBackend {

    private final ConcurrentMap<String, EixampleDbEntry> map = new ConcurrentHashMap<>();

    @Override
    public GetResponse get(GetRequest getRequest) {
        Optional<EixampleDbEntry> eixampledbEntry = Optional.ofNullable(map.get(getRequest.getKey()));
        return new GetResponse(getRequest, eixampledbEntry.isPresent(), eixampledbEntry);
    }

    @Override
    public SetResponse set(SetRequest setRequest) {
        EixampleDbEntry newEntry = map.compute(setRequest.getKey(), (key, entry) -> {
            long updateTimestamp = System.currentTimeMillis();
            if (entry != null) {
                updateTimestamp = entry.getLastupdateTimestamp();
            }
           return new EixampleDbEntry(setRequest.getKey(), setRequest.getValue(), updateTimestamp, System.currentTimeMillis());
        });
        return new SetResponse(setRequest, true, newEntry);
    }

    @Override
    public DeleteResponse delete(DeleteRequest deleteRequest) {
        Optional<EixampleDbEntry> entry =  Optional.ofNullable(map.remove(deleteRequest.getKey()));
        return new DeleteResponse(deleteRequest, entry.isPresent(), entry);

    }
}

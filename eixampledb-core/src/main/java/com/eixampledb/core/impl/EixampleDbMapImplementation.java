package com.eixampledb.core.impl;

import com.eixampledb.core.api.EixampleDbBackend;
import com.eixampledb.core.api.EixampleDbEntry;
import com.eixampledb.core.api.request.DeleteRequest;
import com.eixampledb.core.api.request.GetRequest;
import com.eixampledb.core.api.request.SetRequest;
import com.eixampledb.core.api.response.DeleteResponse;
import com.eixampledb.core.api.response.GetResponse;
import com.eixampledb.core.api.response.SetResponse;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class EixampleDbMapImplementation implements EixampleDbBackend {

    private final Map<String, EixampleDbEntry> map = new HashMap<>();

    @Override
    public GetResponse get(GetRequest getRequest) {
        Optional<EixampleDbEntry> eixampledbEntry = Optional.ofNullable(map.get(getRequest.getKey()));
        return new GetResponse(getRequest, eixampledbEntry.isPresent(), eixampledbEntry);
    }

    @Override
    public SetResponse set(SetRequest setRequest) {
        Optional<EixampleDbEntry> entry = Optional.ofNullable(map.get(setRequest.getKey()));
        EixampleDbEntry newEntry;
        if (entry.isPresent()) {
            newEntry = new EixampleDbEntry(setRequest.getKey(), setRequest.getValue(), entry.get().getCreationTimestamp(), System.currentTimeMillis());
        } else {
            newEntry = new EixampleDbEntry(setRequest.getKey(), setRequest.getValue(), System.currentTimeMillis(), System.currentTimeMillis());
        }
        map.put(setRequest.getKey(), newEntry);
        return new SetResponse(setRequest, true, newEntry);
    }

    @Override
    public DeleteResponse delete(DeleteRequest deleteRequest) {
        Optional<EixampleDbEntry> entry =  Optional.ofNullable(map.remove(deleteRequest.getKey()));
        return new DeleteResponse(deleteRequest, entry.isPresent(), entry);

    }
}

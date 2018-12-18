package com.eixampledb.core.impl;

import com.eixampledb.core.api.EixampleDbBackend;
import com.eixampledb.core.api.EixampleDbEntry;
import com.eixampledb.core.api.request.*;
import com.eixampledb.core.api.response.*;

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
    Object value = setRequest.getType().isNumber() ? NumberUtils.parse(setRequest.getValue()) : setRequest.getValue();
    int searchType = 0;
    if(setRequest.getSearchType().isStarts()) searchType = 1;
    else if (setRequest.getSearchType().isRegex()) searchType = 2;
    switch(searchType){
        case 1:
            //TODO Crear arbol para busqueda prefijos
            //BUSQUEDA KEYS -> OPERAR LAS KEYS
            break;

        case 2:
            //TODO Busqueda Regular expression en BD
            //BUSQUEDA KEYS -> OPERAR LAS KEYS
            break;
        default:  EixampleDbEntry newEntry = map.compute(setRequest.getKey(), (key, entry) -> new EixampleDbEntry(
                setRequest.getKey(),
                value,
                creationTimestamp(entry),
                System.currentTimeMillis(),
                setRequest.getType()
        ));
    }

        return new SetResponse(setRequest, true, newEntry);
    }

    @Override
    public IncrResponse incr(IncrRequest request) {
        EixampleDbEntry newEntry = map.computeIfPresent(request.getKey(), (key, entry) -> new EixampleDbEntry(
                request.getKey(),
                NumberUtils.incr(entry.getValue()),
                creationTimestamp(entry),
                System.currentTimeMillis(),
                entry.getType()));
        if (newEntry != null) {
            return new IncrResponse(request, true, Optional.of(newEntry));
        } else {
            return new IncrResponse(request, false, Optional.empty());
        }
    }

    @Override
    public DecrResponse decr(DecrRequest request) {
        EixampleDbEntry newEntry = map.computeIfPresent(request.getKey(), (key, entry) -> new EixampleDbEntry(
                request.getKey(),
                NumberUtils.decr(entry.getValue()),
                creationTimestamp(entry),
                System.currentTimeMillis(),
                entry.getType()));
        if (newEntry != null) {
            return new DecrResponse(request, true, Optional.of(newEntry));
        } else {
            return new DecrResponse(request, false, Optional.empty());
        }
    }

    @Override
    public DeleteResponse delete(DeleteRequest deleteRequest) {
        Optional<EixampleDbEntry> entry =  Optional.ofNullable(map.remove(deleteRequest.getKey()));
        return new DeleteResponse(deleteRequest, entry.isPresent(), entry);

    }

    private long creationTimestamp(EixampleDbEntry entry) {
        long creationTimestamp = System.currentTimeMillis();
        if (entry != null) {
            creationTimestamp = entry.getCreationTimestamp();
        }
        return creationTimestamp;
    }
}

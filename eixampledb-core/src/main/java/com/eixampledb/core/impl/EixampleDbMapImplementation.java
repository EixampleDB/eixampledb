package com.eixampledb.core.impl;

import com.eixampledb.core.api.EixampleDbBackend;
import com.eixampledb.core.api.EixampleDbEntry;
import com.eixampledb.core.api.KeyTree;
import com.eixampledb.core.api.request.*;
import com.eixampledb.core.api.response.*;

import java.util.ArrayList;
import java.util.NavigableSet;
import java.util.Optional;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EixampleDbMapImplementation implements EixampleDbBackend {

    private final ConcurrentMap<String, EixampleDbEntry> map = new ConcurrentHashMap<>();
    private final KeyTree treeMapKeys = new KeyTree();

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

        EixampleDbEntry newEntry = null;
        NavigableSet<String> setKeys;
        switch(searchType){
            case 1:
                //TODO Make the set with the given values
                // need to iterate over the set to get the values

                // this navigabeSet is an iterable with the keys with the given prefix ( setRequest.getKey() )
                setKeys = treeMapKeys.withPrefix(setRequest.getKey());
                break;

            case 2:
                //TODO Busqueda Regular expression en BD
                //BUSQUEDA KEYS -> OPERAR LAS KEYS
                Pattern pat = Pattern.compile(""); // replace the quotes with the pattern given by the user
                setKeys = new TreeSet<>();
                for(String s : treeMapKeys.sortedList()){
                    Matcher m = pat.matcher(s);
                    if(m.matches()){
                        setKeys.add(s);
                    }
                }
                // here we have all the entries that matched our pattern inside "setKeys", which is an iterable
                break;
            default:
                newEntry  = map.compute(setRequest.getKey(), (key, entry) -> new EixampleDbEntry(
                    setRequest.getKey(),
                    value,
                    creationTimestamp(entry),
                    System.currentTimeMillis(),
                    setRequest.getType()
                ));
        }

        treeMapKeys.add(setRequest.getKey());
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

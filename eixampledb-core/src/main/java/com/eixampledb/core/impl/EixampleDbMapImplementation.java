package com.eixampledb.core.impl;

import com.eixampledb.core.api.EixampleDbBackend;
import com.eixampledb.core.api.EixampleDbEntry;
import com.eixampledb.core.api.KeyTree;
import com.eixampledb.core.api.ValueType;
import com.eixampledb.core.api.request.*;
import com.eixampledb.core.api.response.*;
import com.eixampledb.core.enums.OperationType;
import com.eixampledb.core.model.OperationDTO;

import java.lang.reflect.Array;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EixampleDbMapImplementation implements EixampleDbBackend {

    private final ConcurrentMap<String, EixampleDbEntry> map = new ConcurrentHashMap<>();
    private final KeyTree treeMapKeys = new KeyTree();

    @Override
    public GetResponse get(GetRequest getRequest) {
        int searchType = 0;
        if(getRequest.getSearchType().isStarts()) searchType = 1;
        else if (getRequest.getSearchType().isRegex()) searchType = 2;

        EixampleDbEntry newEntry = null;
        NavigableSet<String> setKeys;
        switch(searchType){
            case 1:
                // this navigabeSet is an iterable with the keys with the given prefix ( setRequest.getKey() )
                setKeys = treeMapKeys.withPrefix(getRequest.getKey());
                for(String llave: setKeys){
                    EixampleDbEntry entry = map.get(llave);

                }

                break;

            case 2:
                //TODO Busqueda Regular expression en BD
                //BUSQUEDA KEYS -> OPERAR LAS KEYS
                Pattern pat = Pattern.compile(getRequest.getKey()); // replace the quotes with the pattern given by the user
                setKeys = new TreeSet<>();
                for(String s : treeMapKeys.sortedList()){
                    Matcher m = pat.matcher(s);
                    if(m.matches()){
                        setKeys.add(s);
                    }
                }
                for(String llave: setKeys) {
                    EixampleDbEntry entry = map.get(llave);
                }

                // here we have all the entries that matched our pattern inside "setKeys", which is an iterable
                break;
            default:
                Optional<EixampleDbEntry> eixampledbEntry = Optional.ofNullable(map.get(getRequest.getKey()));
                return new GetResponse(getRequest, eixampledbEntry.isPresent(), eixampledbEntry);
        }

        return new BulkResponse(BulkRequest.builder().operatioons(operations),true,report);
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
                for(String llave: setKeys){
                    newEntry  = map.compute(llave, (key, entry) -> new EixampleDbEntry(
                            llave,
                            value,
                            creationTimestamp(entry),
                            System.currentTimeMillis(),
                            setRequest.getType()
                    ));
                }
                break;

            case 2:
                //TODO Busqueda Regular expression en BD
                //BUSQUEDA KEYS -> OPERAR LAS KEYS
                Pattern pat = Pattern.compile(setRequest.getKey()); // replace the quotes with the pattern given by the user
                setKeys = new TreeSet<>();
                for(String s : treeMapKeys.sortedList()){
                    Matcher m = pat.matcher(s);
                    if(m.matches()){
                        setKeys.add(s);
                    }
                }
                for(String llave: setKeys) {
                    newEntry = map.compute(llave, (key, entry) -> new EixampleDbEntry(
                            llave,
                            value,
                            creationTimestamp(entry),
                            System.currentTimeMillis(),
                            setRequest.getType()
                    ));
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
                treeMapKeys.add(setRequest.getKey());
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

    @Override
    public BulkResponse bulkOperation(BulkRequest bulkRequest){
        List<OperationDTO> operations = bulkRequest.getOperatioons();
        String report = "";
        for(OperationDTO operationDTO : operations){
            if(operationDTO.getType().equals(OperationType.GET)){

                EixampleDbEntry entry = map.get(operationDTO.getValue());
                report += "GET, KEY:"+operationDTO.getKey()+", VALUE: "+entry.getValue()+"\n";

            }else if(operationDTO.getType().equals(OperationType.DELETE)){

                EixampleDbEntry entry = map.remove(operationDTO.getKey());
                if(entry != null){
                    report += "DELETE, KEY:"+operationDTO.getKey()+", VALUE: OK\n";
                }else{
                    report += "DELETE, KEY:"+operationDTO.getKey()+", VALUE: FAIL\n";
                }

            }else if (operationDTO.getType().equals(OperationType.SET)){
                String type = operationDTO.getParameters().get(0);
                ValueType valueType = ValueType.valueOf(type);

                EixampleDbEntry entryToPut = new EixampleDbEntry(operationDTO.getKey(),operationDTO.getValue(),
                        System.currentTimeMillis(),System.currentTimeMillis(),valueType
                        );

                SetRequest setRequest = SetRequest.builder()
                        .key(operationDTO.getKey())
                        .type(valueType)
                        .value(operationDTO.getValue())
                        .build();
                SetResponse setResponse = this.set(setRequest);

                if(setResponse.isSuccess()){
                    report += "SET, KEY:"+operationDTO.getKey()+", VALUE: OK\n";
                }else{
                    report += "SET, KEY:"+operationDTO.getKey()+", VALUE: FAIL\n";
                }

            }else if(operationDTO.getType().equals(OperationType.INCR)){

                IncrRequest incrRequest = new IncrRequest(operationDTO.getKey());
                IncrResponse incrResponse = this.incr(incrRequest);
                if(incrResponse.getEntry().isPresent()){
                    report += "INCR, KEY:"+operationDTO.getKey()+", VALUE:"+incrResponse.getEntry().get().getValue().toString()+ "\n";
                }else{
                    report += "INCR, KEY:"+operationDTO.getKey()+", VALUE: not found\n";
                }

            }else if(operationDTO.getType().equals(OperationType.DECR)){

                DecrRequest decrRequest = new DecrRequest(operationDTO.getKey());
                DecrResponse decrResponse = this.decr(decrRequest);
                if(decrResponse.getEntry().isPresent()){
                    report += "DECR, KEY:"+operationDTO.getKey()+", VALUE: "+decrResponse.getEntry().get().getValue().toString()+ "\n";

                }else{
                    report += "DECR, KEY:"+operationDTO.getKey()+", VALUE: not found\n";
                }
            }
        }
        return new BulkResponse(bulkRequest,true,report);
    }



    private long creationTimestamp(EixampleDbEntry entry) {
        long creationTimestamp = System.currentTimeMillis();
        if (entry != null) {
            creationTimestamp = entry.getCreationTimestamp();
        }
        return creationTimestamp;
    }
}

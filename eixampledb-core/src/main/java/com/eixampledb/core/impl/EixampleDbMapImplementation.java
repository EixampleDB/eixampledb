package com.eixampledb.core.impl;

import com.eixampledb.core.Snapshot;
import com.eixampledb.core.api.EixampleDbBackend;
import com.eixampledb.core.api.EixampleDbEntry;
import com.eixampledb.core.api.ValueType;
import com.eixampledb.core.api.request.*;
import com.eixampledb.core.api.response.*;
import com.eixampledb.core.enums.OperationType;
import com.eixampledb.core.model.OperationDTO;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class EixampleDbMapImplementation implements EixampleDbBackend {

    private final ConcurrentMap<String, EixampleDbEntry> map = new ConcurrentHashMap<>();
    // For now files are saved to "log.json"
    String now = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
    private final Snapshot snapshot = new Snapshot("log" + ".json", map);

    // Create snapshot
    public void doSnapshot() {
        snapshot.run();
    }

    /**
     * Load snapshot
     * @param filename path to the snapshot file
     */
    public void loadSnapshot(String filename) {
        snapshot.lock_and_read(filename, this.map);
    }

    @Override
    public GetResponse get(GetRequest getRequest) {
        Optional<EixampleDbEntry> eixampledbEntry = Optional.ofNullable(map.get(getRequest.getKey()));
        return new GetResponse(getRequest, eixampledbEntry.isPresent(), eixampledbEntry);
    }

    @Override
    public SetResponse set(SetRequest setRequest) {
        Object value = setRequest.getType().isNumber() ? NumberUtils.parse(setRequest.getValue()) : setRequest.getValue();
        EixampleDbEntry newEntry = map.compute(setRequest.getKey(), (key, entry) -> new EixampleDbEntry(
                setRequest.getKey(),
                value,
                creationTimestamp(entry),
                System.currentTimeMillis(),
                setRequest.getType()
        ));
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

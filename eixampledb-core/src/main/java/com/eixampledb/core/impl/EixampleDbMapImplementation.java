package com.eixampledb.core.impl;

import com.eixampledb.core.api.EixampleDbBackend;
import com.eixampledb.core.api.EixampleDbEntry;
import com.eixampledb.core.api.request.BulkRequest;
import com.eixampledb.core.api.request.DeleteRequest;
import com.eixampledb.core.api.request.GetRequest;
import com.eixampledb.core.api.request.SetRequest;
import com.eixampledb.core.api.response.BulkResponse;
import com.eixampledb.core.api.response.DeleteResponse;
import com.eixampledb.core.api.response.GetResponse;
import com.eixampledb.core.api.response.SetResponse;
import com.eixampledb.core.enums.TipoOperacion;
import com.eixampledb.core.model.OperationDTO;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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

    @Override
    public BulkResponse bulkOperation(BulkRequest bulkRequest){
        List<OperationDTO> operations = bulkRequest.getOperatioons();
        for(OperationDTO operationDTO : operations){
            if(operationDTO.getKey().equals(TipoOperacion.GET)){
                //TODO llamar para hacer el get
            }else if(operationDTO.getKey().equals(TipoOperacion.DELETE)){

            }else if (operationDTO.getKey().equals(TipoOperacion.SET)){

            }
        }
        return null;
    }


}

package com.eixampledb.api;

import com.eixampledb.core.api.EixampleDb;
import com.eixampledb.core.api.request.BulkRequest;
import com.eixampledb.core.api.response.BulkResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Controller
@RequiredArgsConstructor
@RequestMapping(path = "/bulk/")
public class BulkOperationController {

    private final EixampleDb eixampledb;
    private final ExecutorService executorService = Executors.newFixedThreadPool(5);
    private final Map<Long,BulkResponse> idsOperations = new ConcurrentHashMap<>();
    private final Random randomGenerator = new Random();

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Long> processBulkOperation(
            @RequestBody BulkRequest bulkRequest
    ){

        final Long idBulk =  System.currentTimeMillis()+randomGenerator.nextLong();

        executorService.submit(() -> {
            idsOperations.put(idBulk,eixampledb.bulkOperation(bulkRequest));
        });
        return ResponseEntity.ok(idBulk);

    }

    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<String> checkBulkOperationStatus(
            @PathVariable("id") Long idOperation
    ) {
        if (idsOperations.containsKey(idOperation)) {
            return ResponseEntity.ok(idsOperations.get(idOperation).getReport());
        }
        return ResponseEntity.notFound().build();
    }
}

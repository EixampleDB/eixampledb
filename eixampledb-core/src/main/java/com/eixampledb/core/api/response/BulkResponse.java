package com.eixampledb.core.api.response;

import com.eixampledb.core.api.request.BulkRequest;
import lombok.Getter;

@Getter
public class BulkResponse extends Response<BulkRequest> {

    private String informe;

    BulkResponse(BulkRequest request, boolean success) {
        super(request, success);
    }

}

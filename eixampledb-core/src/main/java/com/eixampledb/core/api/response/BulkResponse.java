package com.eixampledb.core.api.response;

import com.eixampledb.core.api.request.BulkRequest;
import lombok.Getter;

@Getter
public class BulkResponse extends Response<BulkRequest> {

    private String informe;

    public BulkResponse(BulkRequest request, boolean success, String informe) {
        super(request, success);
        this.informe = informe;
    }

}

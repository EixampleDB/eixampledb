package com.eixampledb.core.api.response;

import com.eixampledb.core.api.request.BulkRequest;
import lombok.Getter;

@Getter
public class BulkResponse extends Response<BulkRequest> {

    private String report;

    public BulkResponse(BulkRequest request, boolean success, String report) {
        super(request, success);
        this.report = report;
    }

}

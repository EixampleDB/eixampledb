package com.eixampledb.core.api.response;

import com.eixampledb.core.api.EixampleDbEntry;
import com.eixampledb.core.api.request.IncrRequest;
import lombok.Getter;

import java.util.Optional;

@Getter
public class IncrResponse extends Response<IncrRequest> {

    private final Optional<EixampleDbEntry> entry;

    public IncrResponse(IncrRequest request, boolean success, Optional<EixampleDbEntry> entry) {
        super(request, success);
        this.entry = entry;
    }

}

package com.eixampledb.core.api.response;

import com.eixampledb.core.api.EixampleDbEntry;
import com.eixampledb.core.api.request.DecrRequest;
import lombok.Getter;

import java.util.Optional;

@Getter
public class DecrResponse extends Response<DecrRequest> {

    private final Optional<EixampleDbEntry> entry;

    public DecrResponse(DecrRequest request, boolean success, Optional<EixampleDbEntry> entry) {
        super(request, success);
        this.entry = entry;
    }

}

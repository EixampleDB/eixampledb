package com.eixampledb.core.api.response;

import com.eixampledb.core.api.EixampleDbEntry;
import com.eixampledb.core.api.request.GetRequest;
import lombok.Getter;

import java.util.Optional;

@Getter
public class GetResponse extends Response<GetRequest> {

    private final Optional<EixampleDbEntry> entry;

    public GetResponse(GetRequest request, boolean success, Optional<EixampleDbEntry> entry) {
        super(request, success);
        this.entry = entry;
    }

}

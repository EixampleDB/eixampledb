package com.eixampledb.core.api.response;

import com.eixampledb.core.api.EixampleDbEntry;
import com.eixampledb.core.api.request.SetRequest;
import lombok.Getter;

@Getter
public class SetResponse extends Response<SetRequest> {

    private final EixampleDbEntry entry;

    public SetResponse(SetRequest request, boolean success, EixampleDbEntry entry) {
        super(request, success);
        this.entry = entry;
    }

}

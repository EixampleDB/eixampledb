package com.eixampledb.core.api.response;

import com.eixampledb.core.api.EixampleDbEntry;
import com.eixampledb.core.api.request.DeleteRequest;
import lombok.Getter;

import java.util.Optional;

@Getter
public class DeleteResponse extends Response<DeleteRequest> {

    private final Optional<EixampleDbEntry> entry;

    public DeleteResponse(DeleteRequest request, boolean success, Optional<EixampleDbEntry> entry) {
        super(request, success);
        this.entry = entry;
    }

}

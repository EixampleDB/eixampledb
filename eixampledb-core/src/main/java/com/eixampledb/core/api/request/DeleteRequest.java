package com.eixampledb.core.api.request;

import com.eixampledb.core.api.SearchType;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class DeleteRequest extends Request implements RequestWithKey {

    private final String key;
    private final SearchType searchType;

    public DeleteRequest(String dKey){
        this.key = dKey;
        this.searchType = SearchType.DEF;
    }
}

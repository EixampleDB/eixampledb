package com.eixampledb.core.api.request;

import com.eixampledb.core.api.SearchType;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class DecrRequest extends Request implements RequestWithKey {

    private final String key;
    private final SearchType searchType;

}

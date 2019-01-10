package com.eixampledb.core.api.request;

import com.eixampledb.core.api.SearchType;
import com.eixampledb.core.api.ValueType;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
@Builder
public class SetRequest extends Request implements RequestWithKey {

    private final String key;
    private final String value;
    private final ValueType type;
    private final SearchType searchType;


}

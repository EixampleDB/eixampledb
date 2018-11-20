package com.eixampledb.core.api.request;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class SetRequest extends Request implements RequestWithKey {

    private final String key;
    private final String value;

}

package com.eixampledb.core.api.request;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class GetRequest extends Request implements RequestWithKey {

    private final String key;

}

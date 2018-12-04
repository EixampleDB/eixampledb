package com.eixampledb.core.model;

import com.eixampledb.core.enums.TipoOperacion;
import lombok.Getter;

@Getter
public class OperationDTO {

    private String key;

    private TipoOperacion tipo;

    private String parameters;
}

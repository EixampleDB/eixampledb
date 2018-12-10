package com.eixampledb.core.model;

import com.eixampledb.core.enums.TipoOperacion;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OperationDTO {

    private String key;

    private TipoOperacion tipo;

    private String parameters;
}

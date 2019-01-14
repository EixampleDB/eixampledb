package com.eixampledb.core.model;

import com.eixampledb.core.enums.OperationType;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OperationDTO {

    private String key;

    private OperationType type;

    private String value;

    private List<String> parameters;
}

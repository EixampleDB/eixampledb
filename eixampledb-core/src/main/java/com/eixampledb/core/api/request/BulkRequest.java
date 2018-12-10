package com.eixampledb.core.api.request;

import com.eixampledb.core.model.OperationDTO;
import lombok.*;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BulkRequest extends Request {
    List<OperationDTO> operatioons;

}

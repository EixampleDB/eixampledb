package com.eixampledb.core.api.request;

import com.eixampledb.core.model.OperationDTO;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import java.util.List;

@Getter
@RequiredArgsConstructor
public class BulkRequest extends Request {
    List<OperationDTO> operatioons;

}

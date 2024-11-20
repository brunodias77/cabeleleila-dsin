package com.brunodias.dsin_cabeleleila_server.useCases.admin.createServiceAdmin;

import com.brunodias.dsin_cabeleleila_server.dtos.BaseResponseDTO;
import com.brunodias.dsin_cabeleleila_server.dtos.requests.RequestCreateServiceAdmin;

public interface ICreateServiceAdminUseCase {
    BaseResponseDTO execute(RequestCreateServiceAdmin request);
}

package com.brunodias.dsin_cabeleleila_server.useCases.authentications.loginUser;

import com.brunodias.dsin_cabeleleila_server.dtos.BaseResponseDTO;
import com.brunodias.dsin_cabeleleila_server.dtos.requests.RequestLoginUser;

public interface ILoginUserUseCase {
    public BaseResponseDTO execute(RequestLoginUser request);
}

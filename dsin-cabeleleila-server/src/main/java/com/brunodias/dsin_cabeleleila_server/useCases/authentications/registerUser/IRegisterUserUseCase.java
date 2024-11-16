package com.brunodias.dsin_cabeleleila_server.useCases.authentications.registerUser;

import com.brunodias.dsin_cabeleleila_server.dtos.BaseResponseDTO;
import com.brunodias.dsin_cabeleleila_server.dtos.requests.RequestRegisterUser;

public interface IRegisterUserUseCase {

    public BaseResponseDTO execute(RequestRegisterUser request);

}

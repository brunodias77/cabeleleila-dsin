package com.brunodias.dsin_cabeleleila_server.useCases.users.getUserByToken;

import com.brunodias.dsin_cabeleleila_server.dtos.BaseResponseDTO;

import java.util.UUID;

public interface IGetUserByTokenUseCase {
    public BaseResponseDTO execute(UUID request);
}

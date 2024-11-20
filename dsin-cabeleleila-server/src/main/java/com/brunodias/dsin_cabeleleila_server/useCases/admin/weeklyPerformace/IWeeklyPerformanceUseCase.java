package com.brunodias.dsin_cabeleleila_server.useCases.admin.weeklyPerformace;

import com.brunodias.dsin_cabeleleila_server.dtos.BaseResponseDTO;
import com.brunodias.dsin_cabeleleila_server.dtos.requests.RequestWeeklyPerformaceAdmin;

public interface IWeeklyPerformanceUseCase {
    public BaseResponseDTO execute(RequestWeeklyPerformaceAdmin request);
}

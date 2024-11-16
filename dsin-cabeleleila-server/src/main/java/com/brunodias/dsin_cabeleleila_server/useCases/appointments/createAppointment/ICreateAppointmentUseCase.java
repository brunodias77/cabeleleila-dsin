package com.brunodias.dsin_cabeleleila_server.useCases.appointments.createAppointment;

import com.brunodias.dsin_cabeleleila_server.dtos.BaseResponseDTO;
import com.brunodias.dsin_cabeleleila_server.dtos.requests.RequestCreateAppointment;

public interface ICreateAppointmentUseCase {
    public BaseResponseDTO execute(RequestCreateAppointment request);

}

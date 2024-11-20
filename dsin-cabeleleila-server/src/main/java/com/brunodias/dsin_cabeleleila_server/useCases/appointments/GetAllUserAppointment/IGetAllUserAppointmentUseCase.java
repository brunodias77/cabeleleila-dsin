package com.brunodias.dsin_cabeleleila_server.useCases.appointments.GetAllUserAppointment;

import com.brunodias.dsin_cabeleleila_server.dtos.AppointmentDetailsDTO;
import com.brunodias.dsin_cabeleleila_server.dtos.BaseResponseDTO;

import java.util.List;

public interface IGetAllUserAppointmentUseCase {
    public BaseResponseDTO execute();
}

package com.brunodias.dsin_cabeleleila_server.useCases.appointments.UpdateAppointment;

import com.brunodias.dsin_cabeleleila_server.dtos.AppointmentDetailsDTO;
import com.brunodias.dsin_cabeleleila_server.dtos.BaseResponseDTO;
import com.brunodias.dsin_cabeleleila_server.dtos.requests.RequestUpdateAppointment;

import java.util.List;
import java.util.UUID;

public interface IUpdateAppointmentUseCase {
    public BaseResponseDTO execute(UUID id, RequestUpdateAppointment request);
}

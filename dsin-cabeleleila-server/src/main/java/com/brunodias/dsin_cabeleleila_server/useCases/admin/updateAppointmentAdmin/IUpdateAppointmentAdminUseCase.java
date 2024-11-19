package com.brunodias.dsin_cabeleleila_server.useCases.admin.updateAppointmentAdmin;

import com.brunodias.dsin_cabeleleila_server.dtos.BaseResponseDTO;
import com.brunodias.dsin_cabeleleila_server.dtos.requests.RequestUpdateAppointment;
import com.brunodias.dsin_cabeleleila_server.dtos.requests.RequestUpdateAppointmentAdmin;

import java.util.UUID;

public interface IUpdateAppointmentAdminUseCase {
    public BaseResponseDTO execute(UUID appointmentId, RequestUpdateAppointmentAdmin request);
}

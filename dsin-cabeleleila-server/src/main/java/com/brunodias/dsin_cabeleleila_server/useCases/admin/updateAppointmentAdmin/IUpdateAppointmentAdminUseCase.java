package com.brunodias.dsin_cabeleleila_server.useCases.admin.updateAppointmentAdmin;

import com.brunodias.dsin_cabeleleila_server.dtos.BaseResponseDTO;
import com.brunodias.dsin_cabeleleila_server.dtos.requests.RequestUpdateAppointment;

import java.util.UUID;

public interface IUpdateAppointmentAdmin {
    public BaseResponseDTO execute(UUID appointmentId, RequestUpdateAppointment request);
}

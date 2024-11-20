package com.brunodias.dsin_cabeleleila_server.useCases.admin.confirmAppointment;

import com.brunodias.dsin_cabeleleila_server.dtos.BaseResponseDTO;

import java.util.UUID;

public interface IConfirmAppointmentAdminUseCase {
    public BaseResponseDTO execute(UUID appointmentId);
}

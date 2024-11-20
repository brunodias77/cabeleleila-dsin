package com.brunodias.dsin_cabeleleila_server.useCases.admin.cancelAppointmentAdmin;

import com.brunodias.dsin_cabeleleila_server.dtos.BaseResponseDTO;
import com.brunodias.dsin_cabeleleila_server.repositories.AppointmentRepository;

import java.util.UUID;

public interface ICancelAppointmentAdminUseCase {

    public BaseResponseDTO execute(UUID appointmentId);
}

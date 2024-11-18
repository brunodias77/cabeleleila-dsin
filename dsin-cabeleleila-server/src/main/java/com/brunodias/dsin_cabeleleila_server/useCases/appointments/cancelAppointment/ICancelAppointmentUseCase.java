package com.brunodias.dsin_cabeleleila_server.useCases.appointments.cancelAppointment;

import com.brunodias.dsin_cabeleleila_server.dtos.BaseResponseDTO;

import java.util.UUID;

public interface ICancelAppointmentUseCase {

    public BaseResponseDTO execute(UUID appointmentId);
}

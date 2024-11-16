package com.brunodias.dsin_cabeleleila_server.useCases.appointments.GetAllUserAppointment;

import com.brunodias.dsin_cabeleleila_server.dtos.AppointmentDetailsDTO;

import java.util.List;

public interface IGetAllUserAppointmentUseCase {
    public List<AppointmentDetailsDTO> execute();
}

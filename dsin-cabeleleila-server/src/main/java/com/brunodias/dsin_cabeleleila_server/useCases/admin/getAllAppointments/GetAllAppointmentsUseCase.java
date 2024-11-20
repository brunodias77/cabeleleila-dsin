package com.brunodias.dsin_cabeleleila_server.useCases.admin.getAllAppointments;

import com.brunodias.dsin_cabeleleila_server.dtos.AppointmentDetailsDTO;
import com.brunodias.dsin_cabeleleila_server.dtos.BaseResponseDTO;
import com.brunodias.dsin_cabeleleila_server.dtos.ServiceDTO;
import com.brunodias.dsin_cabeleleila_server.entities.Appointment;
import com.brunodias.dsin_cabeleleila_server.enums.AppointmentStatus;
import com.brunodias.dsin_cabeleleila_server.repositories.AppointmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GetAllAppointmentsUseCase implements IGetAllAppointmentsUseCase {
    private final AppointmentRepository _appointmentRepository;

    @Override
    public BaseResponseDTO execute() {
        List<Appointment> appointments = _appointmentRepository.findAll();

        List<AppointmentDetailsDTO> appointmentDetails = appointments.stream()
                .map(appointment -> new AppointmentDetailsDTO(
                        appointment.getId(),
                        appointment.getAppointmentDate(),
                        appointment.getAppointmentTime(),
                        appointment.getStatus(),
                        appointment.getServices().stream()
                                .map(service -> new ServiceDTO(
                                        service.getId(),
                                        service.getName(),
                                        service.getPrice()))
                                .collect(Collectors.toList())
                ))
                .collect(Collectors.toList());

        return  BaseResponseDTO.builder().status(200).data(appointmentDetails).build();

    }


}

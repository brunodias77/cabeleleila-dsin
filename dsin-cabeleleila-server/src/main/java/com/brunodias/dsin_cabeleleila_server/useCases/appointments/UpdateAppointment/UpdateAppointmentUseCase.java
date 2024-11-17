package com.brunodias.dsin_cabeleleila_server.useCases.appointments.UpdateAppointment;

import com.brunodias.dsin_cabeleleila_server.dtos.BaseResponseDTO;
import com.brunodias.dsin_cabeleleila_server.dtos.requests.RequestUpdateAppointment;
import com.brunodias.dsin_cabeleleila_server.entities.Appointment;
import com.brunodias.dsin_cabeleleila_server.enums.AppointmentStatus;
import com.brunodias.dsin_cabeleleila_server.exceptions.AppointmentNotFound;
import com.brunodias.dsin_cabeleleila_server.exceptions.AppointmentUpdateException;
import com.brunodias.dsin_cabeleleila_server.repositories.AppointmentRepository;
import com.brunodias.dsin_cabeleleila_server.repositories.ServiceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UpdateAppointmentUseCase implements IUpdateAppointmentUseCase {

    private final AppointmentRepository _appointmentRepository;
    private final ServiceRepository _serviceRepository;

    @Transactional
    @Override
    public BaseResponseDTO execute(UUID id, RequestUpdateAppointment request) {
        Appointment appointment = _appointmentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Agendamento não encontrado"));

        LocalDate today = LocalDate.now();
        if (!appointment.getAppointmentDate().isAfter(today.plusDays(2))) {
            throw new AppointmentUpdateException("Agendamentos só podem ser alterados com pelo menos dois dias de antecedência");
        }

        appointment.setAppointmentDate(request.getAppointmentDate());

        if (request.getServiceId() != null) {
            com.brunodias.dsin_cabeleleila_server.entities.Service service = _serviceRepository.findById(request.getServiceId())
                    .orElseThrow(() -> new IllegalArgumentException("Serviço com ID " + request.getServiceId() + " não encontrado"));
            Set<com.brunodias.dsin_cabeleleila_server.entities.Service> services = new HashSet<>();
            services.add(service);
            appointment.setServices(services);
        }

        Appointment updatedAppointment = _appointmentRepository.save(appointment);

        return BaseResponseDTO.builder()
                .status(200)
                .message("Agendamento atualizado com sucesso")
                .data(updatedAppointment)
                .build();
    }
}

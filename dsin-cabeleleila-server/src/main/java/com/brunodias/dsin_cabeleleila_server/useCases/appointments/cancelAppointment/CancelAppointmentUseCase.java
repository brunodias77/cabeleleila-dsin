package com.brunodias.dsin_cabeleleila_server.useCases.appointments.cancelAppointment;

import com.brunodias.dsin_cabeleleila_server.dtos.BaseResponseDTO;
import com.brunodias.dsin_cabeleleila_server.entities.Appointment;
import com.brunodias.dsin_cabeleleila_server.enums.AppointmentStatus;
import com.brunodias.dsin_cabeleleila_server.repositories.AppointmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CancelAppointmentUseCase implements ICancelAppointmentUseCase{

    private final AppointmentRepository _appointmentRepository;
    @Override
    public BaseResponseDTO execute(UUID appointmentId) {
        Appointment appointment = _appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new IllegalArgumentException("Agendamento não encontrado"));

        if (appointment.getAppointmentDate().isBefore(LocalDate.now().plusDays(2))) {
            throw new IllegalArgumentException("O agendamento só pode ser cancelado com pelo menos 2 dias de antecedência");
        }

        appointment.setStatus(AppointmentStatus.CANCELADO);

        _appointmentRepository.save(appointment);

        return BaseResponseDTO.builder().status(200).message("Agendamento cancelado com sucesso").build();
    }
}
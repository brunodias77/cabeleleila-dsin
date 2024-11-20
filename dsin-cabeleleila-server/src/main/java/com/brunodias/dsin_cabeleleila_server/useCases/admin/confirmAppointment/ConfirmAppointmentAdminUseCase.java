package com.brunodias.dsin_cabeleleila_server.useCases.admin.confirmAppointment;

import com.brunodias.dsin_cabeleleila_server.dtos.BaseResponseDTO;
import com.brunodias.dsin_cabeleleila_server.entities.Appointment;
import com.brunodias.dsin_cabeleleila_server.enums.AppointmentStatus;
import com.brunodias.dsin_cabeleleila_server.repositories.AppointmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ConfirmAppointmentAdminUseCase implements IConfirmAppointmentAdminUseCase{
    private final AppointmentRepository _appointmentRepository;

    @Override
    @Transactional
    public BaseResponseDTO execute(UUID appointmentId) {

        Appointment appointment = _appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new IllegalArgumentException("Agendamento não encontrado"));
        appointment.setStatus(AppointmentStatus.CONFIRMADO);
        var appointmentSaved = _appointmentRepository.save(appointment);
        return BaseResponseDTO.builder().status(200).data(appointmentSaved).message("Agendamento confirmado com sucesso").build();    }
}

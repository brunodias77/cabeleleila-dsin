package com.brunodias.dsin_cabeleleila_server.useCases.appointments.UpdateAppointment;

import com.brunodias.dsin_cabeleleila_server.dtos.BaseResponseDTO;
import com.brunodias.dsin_cabeleleila_server.dtos.requests.RequestUpdateAppointment;
import com.brunodias.dsin_cabeleleila_server.entities.Appointment;
import com.brunodias.dsin_cabeleleila_server.exceptions.AppointmentNotFound;
import com.brunodias.dsin_cabeleleila_server.repositories.AppointmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UpdateAppointmentUseCase implements IUpdateAppointmentUseCase{
    private final AppointmentRepository _appointmentRepository;
    @Override
    public BaseResponseDTO execute(UUID id, RequestUpdateAppointment request) {
        // Buscar o agendamento no banco de dados
        var appointmentOptional = _appointmentRepository.findById(id);

        if (appointmentOptional.isEmpty()) {
            throw new AppointmentNotFound();
        }

        Appointment appointment = appointmentOptional.get();

        // Atualizar a data do agendamento
        if (request.getAppointmentDate() != null) {
            appointment.setAppointmentDate(request.getAppointmentDate());
        }

        if(request.getServiceId() != null){
            // Se ele quiser mudar de servico
        }

        // Atualizar os serviços do agendamento

        // Salvar o agendamento atualizado
        _appointmentRepository.save(appointment);

        return null;
    }
}

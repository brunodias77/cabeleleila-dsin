package com.brunodias.dsin_cabeleleila_server.useCases.admin.updateAppointmentAdmin;

import com.brunodias.dsin_cabeleleila_server.dtos.BaseResponseDTO;
import com.brunodias.dsin_cabeleleila_server.dtos.requests.RequestUpdateAppointment;
import com.brunodias.dsin_cabeleleila_server.dtos.requests.RequestUpdateAppointmentAdmin;
import com.brunodias.dsin_cabeleleila_server.entities.Appointment;
import com.brunodias.dsin_cabeleleila_server.exceptions.AppointmentUpdateException;
import com.brunodias.dsin_cabeleleila_server.mappers.EntityToDtoMapper;
import com.brunodias.dsin_cabeleleila_server.repositories.AppointmentRepository;
import com.brunodias.dsin_cabeleleila_server.repositories.ServiceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UpdateAppointmentAdminUseCase implements IUpdateAppointmentAdminUseCase{
    private final AppointmentRepository _appointmentRepository;
    private final ServiceRepository _serviceRepository;
    @Override
    public BaseResponseDTO execute(UUID appointmentId, RequestUpdateAppointmentAdmin request) {
        Appointment appointment = _appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new IllegalArgumentException("Agendamento não encontrado"));

        appointment.setAppointmentDate(request.getAppointmentDate());
        appointment.setAppointmentTime(request.getAppointmentTime());

        Set<UUID> serviceIds = request.getServiceId();
        if (serviceIds != null && !serviceIds.isEmpty()) {
            Set<com.brunodias.dsin_cabeleleila_server.entities.Service> updatedServices = new HashSet<>(_serviceRepository.findAllById(serviceIds));

            if (updatedServices.size() != serviceIds.size()) {
                throw new AppointmentUpdateException("Alguns serviços não foram encontrados");
            }
            appointment.setServices(updatedServices);
        }

        Appointment updatedAppointment = _appointmentRepository.save(appointment);
        var updateAppointmentDTO = EntityToDtoMapper.mapAppointmentToDtoBasic(updatedAppointment);

        return BaseResponseDTO.builder()
                .status(200)
                .message("Agendamento atualizado com sucesso")
                .data(updateAppointmentDTO)
                .build();    }
}

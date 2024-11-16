package com.brunodias.dsin_cabeleleila_server.useCases.appointments.createAppointment;

import com.brunodias.dsin_cabeleleila_server.dtos.BaseResponseDTO;
import com.brunodias.dsin_cabeleleila_server.dtos.requests.RequestCreateAppointment;
import com.brunodias.dsin_cabeleleila_server.entities.Appointment;
import com.brunodias.dsin_cabeleleila_server.enums.AppointmentStatus;
import com.brunodias.dsin_cabeleleila_server.exceptions.ResourceNotFoundException;
import com.brunodias.dsin_cabeleleila_server.mappers.EntityToDtoMapper;
import com.brunodias.dsin_cabeleleila_server.repositories.AppointmentRepository;
import com.brunodias.dsin_cabeleleila_server.repositories.ServiceRepository;
import com.brunodias.dsin_cabeleleila_server.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CreateAppointmentUseCase implements ICreateAppointmentUseCase{

    private final ServiceRepository _serviceRepository;
    private final AppointmentRepository _appointmentRepository;
    private final UserService _userService;

    @Override
    public BaseResponseDTO execute(RequestCreateAppointment request) {
        List<com.brunodias.dsin_cabeleleila_server.entities.Service> services = new ArrayList<>();

        for (UUID serviceId : request.serviceId()) {
            com.brunodias.dsin_cabeleleila_server.entities.Service service = _serviceRepository.findById(serviceId)
                    .orElseThrow(ResourceNotFoundException::new);
            services.add(service);
        }

        if (services.size() != request.serviceId().size()) {
            throw new ResourceNotFoundException();
        }

        LocalDate appointmentDate = request.appointmentDate();
        if (appointmentDate.isBefore(LocalDate.now())) {
            return BaseResponseDTO.builder().message("Não é possível agendar para uma data passada.").build();
        }

        var user = _userService.getLoginUser();
        Appointment appointment = Appointment.builder()
                .status(AppointmentStatus.AGENDADO)
                .appointmentDate(request.appointmentDate())
                .client(user)
                .services(new HashSet<>()) // Inicialize um HashSet para garantir que é uma coleção gerenciada
                .build();

        // Aqui, as entidades `Service` já estão gerenciadas
        services.forEach(service -> appointment.getServices().add(service));

        // Salve o appointment e as entidades associadas
        Appointment savedAppointment = _appointmentRepository.save(appointment);
        var appointmentDTO = EntityToDtoMapper.mapAppointmentToDtoBasic(savedAppointment);

        return BaseResponseDTO.builder().status(201).message("Agendamento feito com sucesso !").data(appointmentDTO).build();
    }


//    @Override
//    public BaseResponseDTO execute(RequestCreateAppointment request) {
//        List<com.brunodias.dsin_cabeleleila_server.entities.Service> services = _serviceRepository.findAllById(request.serviceId());
//        if (services.size() != request.serviceId().size()) {
//            throw new ResourceNotFoundException();
//        }
//
//        LocalDate appointmentDate = request.appointmentDate();
//        if (appointmentDate.isBefore(LocalDate.now())) {
//            return BaseResponseDTO.builder().message("Não é possível agendar para uma data passada.").build();
//        }
//
//
//
//        var user = _userService.getLoginUser();
//        Appointment appointment = Appointment.builder()
//                .status(AppointmentStatus.AGENDADO)
//                .appointmentDate(request.appointmentDate())
//                .client(user)
//                .services(new HashSet<>())
//                .build();
//        services.forEach(service -> appointment.getServices().add(service));
//        Appointment savedAppointment = _appointmentRepository.save(appointment);
//        var appointmentDTO = EntityToDtoMapper.mapAppointmentToDtoBasic(savedAppointment);
//
//        return BaseResponseDTO.builder().status(201).message("Agendamento feito com sucesso !").data(appointmentDTO).build();
//    }
}

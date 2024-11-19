package com.brunodias.dsin_cabeleleila_server.useCases.appointments.createAppointment;

import com.brunodias.dsin_cabeleleila_server.dtos.BaseResponseDTO;
import com.brunodias.dsin_cabeleleila_server.dtos.requests.RequestCreateAppointment;
import com.brunodias.dsin_cabeleleila_server.entities.Appointment;
import com.brunodias.dsin_cabeleleila_server.entities.User;
import com.brunodias.dsin_cabeleleila_server.enums.AppointmentStatus;
import com.brunodias.dsin_cabeleleila_server.exceptions.ResourceNotFoundException;
import com.brunodias.dsin_cabeleleila_server.mappers.EntityToDtoMapper;
import com.brunodias.dsin_cabeleleila_server.repositories.AppointmentRepository;
import com.brunodias.dsin_cabeleleila_server.repositories.ServiceRepository;
import com.brunodias.dsin_cabeleleila_server.repositories.UserRepository;
import com.brunodias.dsin_cabeleleila_server.services.UserService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CreateAppointmentUseCase implements ICreateAppointmentUseCase{

    private final ServiceRepository _serviceRepository;
    private final AppointmentRepository _appointmentRepository;
    private final UserService _userService;
    private final UserRepository _userRepository;

    @Override
    @Transactional
    public BaseResponseDTO execute(RequestCreateAppointment request) {

        var user = _userService.getLoginUser();

        Set<com.brunodias.dsin_cabeleleila_server.entities.Service> services = request.serviceId().stream()
                .map(id -> _serviceRepository.findById(id)
                        .orElseThrow(() -> new EntityNotFoundException("Serviço com ID " + id + " não encontrado")))
                .collect(Collectors.toSet());


        Appointment appointment = Appointment.builder()
                .appointmentDate(request.appointmentDate())
                .appointmentTime(request.appointmentTime())
                .status(AppointmentStatus.AGENDADO)
                .client(user)
                .services(services)
                .build();
        var appointmentSaved = _appointmentRepository.save(appointment);

        return BaseResponseDTO.builder().status(201).data(appointmentSaved).build();





//        List<com.brunodias.dsin_cabeleleila_server.entities.Service> services = request.serviceId().stream()
//                .map(serviceId -> _serviceRepository.findById(serviceId)
//                        .orElseThrow(() -> new ResourceNotFoundException()))
//                .toList();
//
//        LocalDate appointmentDate = request.appointmentDate();
//        if (appointmentDate.isBefore(LocalDate.now())) {
//            return BaseResponseDTO.builder().message("Não é possível agendar para uma data passada.").build();
//        }
//
//        var user = _userService.getLoginUser();
//
//        Appointment appointment = Appointment.builder()
//                .status(AppointmentStatus.AGENDADO)
//                .appointmentDate(appointmentDate)
//                .client(user)
//                .services(new HashSet<>()) // Inicializa o HashSet
//                .build();
//
//        services.forEach(appointment.getServices()::add);
//
//        Appointment savedAppointment = _appointmentRepository.save(appointment);
//
//        var appointmentDTO = EntityToDtoMapper.mapAppointmentToDtoBasic(savedAppointment);
//
//        return BaseResponseDTO.builder()
//                .status(201)
//                .message("Agendamento feito com sucesso!")
//                .data(appointmentDTO)
//                .build();
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

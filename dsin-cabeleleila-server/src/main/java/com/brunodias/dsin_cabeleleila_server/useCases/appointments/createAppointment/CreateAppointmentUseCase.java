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
import java.time.LocalTime;
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

        boolean appointmentExists = _appointmentRepository.existsByAppointmentDateAndAppointmentTime(request.appointmentDate(), request.appointmentTime());
        if (appointmentExists) {
            return BaseResponseDTO.builder()
                    .status(400)
                    .message("Já existe um agendamento para essa data e hora. Por favor, escolha outro horário.")
                    .build();
        }

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
        var appointmentDTO = EntityToDtoMapper.mapAppointmentToDtoBasic(appointmentSaved);
        return BaseResponseDTO.builder().status(201).data(appointmentDTO).build();

    }
}
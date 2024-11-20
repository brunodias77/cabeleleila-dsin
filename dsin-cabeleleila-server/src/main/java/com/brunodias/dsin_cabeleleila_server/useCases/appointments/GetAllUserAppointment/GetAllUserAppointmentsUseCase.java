package com.brunodias.dsin_cabeleleila_server.useCases.appointments.GetAllUserAppointment;

import com.brunodias.dsin_cabeleleila_server.dtos.AppointmentDetailsDTO;
import com.brunodias.dsin_cabeleleila_server.dtos.BaseResponseDTO;
import com.brunodias.dsin_cabeleleila_server.dtos.ServiceDTO;
import com.brunodias.dsin_cabeleleila_server.entities.Appointment;
import com.brunodias.dsin_cabeleleila_server.repositories.AppointmentRepository;
import com.brunodias.dsin_cabeleleila_server.services.UserService;
import com.brunodias.dsin_cabeleleila_server.useCases.appointments.GetAllUserAppointment.IGetAllUserAppointmentUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GetAllUserAppointmentsUseCase implements IGetAllUserAppointmentUseCase {
    private final AppointmentRepository _appointmentRepository;
    private final UserService _userService;

    @Override
    public BaseResponseDTO execute() {
        var user = _userService.getLoginUser();
        List<Appointment> appointments = _appointmentRepository.findAllByClientId(user.getId());

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

        return BaseResponseDTO.builder().status(200).data(appointmentDetails).build();
    }
}


//package com.brunodias.dsin_cabeleleila_server.useCases.appointments.GetAllUserAppointment;
//
//import com.brunodias.dsin_cabeleleila_server.dtos.AppointmentDetailsDTO;
//import com.brunodias.dsin_cabeleleila_server.dtos.ServiceDTO;
//import com.brunodias.dsin_cabeleleila_server.enums.AppointmentStatus;
//import com.brunodias.dsin_cabeleleila_server.repositories.AppointmentRepository;
//import com.brunodias.dsin_cabeleleila_server.services.UserService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//
//import java.time.LocalDate;
//import java.time.LocalTime;
//import java.util.List;
//import java.util.UUID;
//import java.util.stream.Collectors;
//
//@Service
//@RequiredArgsConstructor
//public class GetAllUserAppointmentUseCase implements IGetAllUserAppointmentUseCase{
//    private final AppointmentRepository _appointmentRepository;
//    private final UserService _userService;
//    @Override
//    public List<AppointmentDetailsDTO> execute() {
//        var user = _userService.getLoginUser();
//        var appointments = _appointmentRepository.findAllAppointmentsDetailsByClientId(user.getId());
//        return appointments.stream()
//                .map(this::mapToAppointmentDetailsDTO)
//                .collect(Collectors.toList());
//    }
//
//    private AppointmentDetailsDTO mapToAppointmentDetailsDTO(Object[] result) {
//        UUID id = (UUID) result[2];  // Corrigido para UUID
//        LocalDate appointmentDate = (LocalDate) result[0];
//        LocalTime appointmentTime = (LocalTime) result[1];
//        AppointmentStatus status = (AppointmentStatus) result[3];
//        String serviceName = (String) result[4];
//        double servicePrice = (double) result[5];
//
//        boolean isInThePast = appointmentDate.isBefore(LocalDate.now());
//
//        if (isInThePast) {
//            System.out.println("Este agendamento já está no passado: " + appointmentDate);
//        }
//
//        ServiceDTO serviceDTO = new ServiceDTO(serviceName, servicePrice);
//        List<ServiceDTO> services = List.of(serviceDTO);
//
//        // Passando a hora junto com a data
//        return new AppointmentDetailsDTO(id, appointmentDate, appointmentTime, status, services);
//    }
//
//}
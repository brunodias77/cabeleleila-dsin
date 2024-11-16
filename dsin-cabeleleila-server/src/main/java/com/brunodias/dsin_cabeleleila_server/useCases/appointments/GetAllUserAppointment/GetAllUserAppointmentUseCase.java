package com.brunodias.dsin_cabeleleila_server.useCases.appointments.GetAllUserAppointment;

import com.brunodias.dsin_cabeleleila_server.dtos.AppointmentDetailsDTO;
import com.brunodias.dsin_cabeleleila_server.dtos.ServiceDTO;
import com.brunodias.dsin_cabeleleila_server.enums.AppointmentStatus;
import com.brunodias.dsin_cabeleleila_server.repositories.AppointmentRepository;
import com.brunodias.dsin_cabeleleila_server.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GetAllUserAppointmentUseCase implements IGetAllUserAppointmentUseCase{
    private final AppointmentRepository _appointmentRepository;
    private final UserService _userService;
    @Override
    public List<AppointmentDetailsDTO> execute() {
        var user = _userService.getLoginUser();
        var appointments = _appointmentRepository.findAllAppointmentsDetailsByClientId(user.getId());
        return appointments.stream()
                .map(this::mapToAppointmentDetailsDTO)
                .collect(Collectors.toList());
    }

    private AppointmentDetailsDTO mapToAppointmentDetailsDTO(Object[] result) {
        UUID id = (UUID) result[1];  // Corrigido para UUID
        LocalDate appointmentDate = (LocalDate) result[0];
        AppointmentStatus status = (AppointmentStatus) result[2];
        String serviceName = (String) result[3];
        double servicePrice = (double) result[4];

        boolean isInThePast = appointmentDate.isBefore(LocalDate.now());

        if (isInThePast) {
            System.out.println("Este agendamento já está no passado: " + appointmentDate);
        }
        ServiceDTO serviceDTO = new ServiceDTO(serviceName, servicePrice);
        List<ServiceDTO> services = List.of(serviceDTO);
        return new AppointmentDetailsDTO(id, appointmentDate, status, services);
    }

}

package com.brunodias.dsin_cabeleleila_server.mappers;

import com.brunodias.dsin_cabeleleila_server.dtos.AppointmentDetailsDTO;
import com.brunodias.dsin_cabeleleila_server.dtos.ServiceDTO;
import com.brunodias.dsin_cabeleleila_server.dtos.UserDTO;
import com.brunodias.dsin_cabeleleila_server.entities.Appointment;
import com.brunodias.dsin_cabeleleila_server.entities.User;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class EntityToDtoMapper {

    public static UserDTO mapUserToDtoBasic(User user) {
        // Criação de DTO básico para o usuário
        return new UserDTO(user.getEmail(), user.getName(), user.getPhoneNumber(),  user.getRoles());
    }

    public static AppointmentDetailsDTO mapAppointmentToDtoBasic(Appointment appointment) {
        List<ServiceDTO> serviceDTOList = appointment.getServices().stream()
                .map(service -> new ServiceDTO(service.getName(), service.getPrice())) // Cria o ServiceDTO
                .collect(Collectors.toList());

        // Criação e retorno do DTO do agendamento
        return new AppointmentDetailsDTO(
                appointment.getId(),
                appointment.getAppointmentDate(),
                appointment.getAppointmentTime(),
                appointment.getStatus(),
                serviceDTOList);
    }
}
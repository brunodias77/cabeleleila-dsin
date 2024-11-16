package com.brunodias.dsin_cabeleleila_server.mappers;

import com.brunodias.dsin_cabeleleila_server.dtos.AppointmentDetailsDTO;
import com.brunodias.dsin_cabeleleila_server.dtos.ServiceDTO;
import com.brunodias.dsin_cabeleleila_server.dtos.UserDTO;
import com.brunodias.dsin_cabeleleila_server.entities.Appointment;
import com.brunodias.dsin_cabeleleila_server.entities.Service;
import com.brunodias.dsin_cabeleleila_server.entities.User;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class EntityToDtoMapper {

    public static UserDTO mapUserToDtoBasic(User user) {
        UserDTO userDto = new UserDTO();
        userDto.setName(user.getName());
        userDto.setPhoneNumber(user.getPhoneNumber());
        userDto.setEmail(user.getEmail());
        return userDto;
    }

    public static AppointmentDetailsDTO mapAppointmentToDtoBasic(Appointment appointment) {
        // Criar uma lista de ServiceDTO para todos os serviços associados ao agendamento
        List<ServiceDTO> serviceDTOList = appointment.getServices().stream()
                .map(service -> new ServiceDTO(service.getName(), service.getPrice())) // Mapeia o serviço para ServiceDTO
                .collect(Collectors.toList());

        // Criar o DTO com os serviços
        return new AppointmentDetailsDTO(
                appointment.getId(),
                appointment.getAppointmentDate(),
                appointment.getStatus(),
                serviceDTOList
        );
    }
}

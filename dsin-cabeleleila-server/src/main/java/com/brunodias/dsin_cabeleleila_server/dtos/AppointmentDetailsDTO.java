package com.brunodias.dsin_cabeleleila_server.dtos;

import com.brunodias.dsin_cabeleleila_server.enums.AppointmentStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
public class AppointmentDetailsDTO {
    private UUID id;
    private LocalDate appointmentDate;
    private LocalTime appointmentTime;
    private AppointmentStatus status;
    private List<ServiceDTO> services;


    // Construtor completo (com appointmentTime)
    public AppointmentDetailsDTO(UUID id, LocalDate appointmentDate, LocalTime appointmentTime, AppointmentStatus status, List<ServiceDTO> services) {
        this.id = id;
        this.appointmentDate = appointmentDate;
        this.appointmentTime = appointmentTime;
        this.status = status;
        this.services = services;
    }
}
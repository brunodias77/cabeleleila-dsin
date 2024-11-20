package com.brunodias.dsin_cabeleleila_server.dtos;

import com.brunodias.dsin_cabeleleila_server.enums.AppointmentStatus;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
public class AppointmentDetailsAdminDTO {
    private UUID id;
    private LocalDate appointmentDate;
    private LocalTime appointmentTime;
    private AppointmentStatus status;
    private List<ServiceDTO> services;
    private String clientName;
    private String clientEmail;
    private String clientPhone;
}

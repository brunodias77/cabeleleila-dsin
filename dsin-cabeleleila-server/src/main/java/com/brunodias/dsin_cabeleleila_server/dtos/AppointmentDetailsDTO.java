package com.brunodias.dsin_cabeleleila_server.dtos;

import com.brunodias.dsin_cabeleleila_server.entities.Service;
import com.brunodias.dsin_cabeleleila_server.enums.AppointmentStatus;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public class AppointmentDetailsDTO {
    private UUID id;
    private LocalDate appointmentDate;
    private AppointmentStatus status;
    private List<ServiceDTO> services; // Agora é uma lista de ServiceDTO

    // Construtores
    public AppointmentDetailsDTO(UUID id, LocalDate appointmentDate, AppointmentStatus status, List<ServiceDTO> services) {
        this.id = id;
        this.appointmentDate = appointmentDate;
        this.status = status;
        this.services = services;
    }

    public AppointmentDetailsDTO(LocalDate appointmentDate, AppointmentStatus status, List<ServiceDTO> services) {
        this.appointmentDate = appointmentDate;
        this.status = status;
        this.services = services;
    }

    // Getters e setters
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public LocalDate getAppointmentDate() {
        return appointmentDate;
    }

    public void setAppointmentDate(LocalDate appointmentDate) {
        this.appointmentDate = appointmentDate;
    }

    public AppointmentStatus getStatus() {
        return status;
    }

    public void setStatus(AppointmentStatus status) {
        this.status = status;
    }

    public List<ServiceDTO> getServices() {
        return services;
    }

    public void setServices(List<ServiceDTO> services) {
        this.services = services;
    }
}

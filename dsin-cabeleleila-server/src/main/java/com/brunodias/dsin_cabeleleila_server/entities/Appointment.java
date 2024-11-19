package com.brunodias.dsin_cabeleleila_server.entities;
import com.brunodias.dsin_cabeleleila_server.enums.AppointmentStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Set;

import org.hibernate.mapping.Collection;

import java.util.HashSet;

@Entity
@Table(name = "appointments")
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Getter
@Setter
public class Appointment extends BaseEntity {

    @Column(nullable = false)
    private LocalTime appointmentTime;

    @Column(nullable = false)

    private LocalDate appointmentDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AppointmentStatus status;

    @ManyToMany(fetch = FetchType.EAGER, cascade = { CascadeType.PERSIST,
            CascadeType.MERGE, CascadeType.DETACH })
    @JoinTable(name = "appointment_service", joinColumns = @JoinColumn(name = "appointment_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "service_id", referencedColumnName = "id"))
    @JsonManagedReference
    private Set<Service> services = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User client;

}
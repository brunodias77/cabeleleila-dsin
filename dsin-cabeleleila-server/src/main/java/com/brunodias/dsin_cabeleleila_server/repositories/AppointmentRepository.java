package com.brunodias.dsin_cabeleleila_server.repositories;

import com.brunodias.dsin_cabeleleila_server.entities.Appointment;
import com.brunodias.dsin_cabeleleila_server.enums.AppointmentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

public interface AppointmentRepository extends JpaRepository<Appointment, UUID> {

    @Query("SELECT a.appointmentDate AS appointmentDate, " +
            "a.appointmentTime as appointmentTime," +
            "a.id AS id, " +
            "a.status AS status, " +
            "s.name AS serviceName, " +
            "s.price AS servicePrice " +
            "FROM Appointment a " +
            "JOIN a.client u " +
            "JOIN a.services s " +
            "WHERE u.id = :clientId")
    List<Object[]> findAllAppointmentsDetailsByClientId(UUID clientId);

    boolean existsByAppointmentDateAndAppointmentTime(LocalDate appointmentDate, LocalTime appointmentTime);

    int countByAppointmentDateBetweenAndStatus(LocalDate startDate, LocalDate endDate, AppointmentStatus status);

    @Query("SELECT a FROM Appointment a " +
            "WHERE a.appointmentDate BETWEEN :startDate AND :endDate")
    List<Appointment> findAppointmentsBetweenDates(LocalDate startDate, LocalDate endDate);



}
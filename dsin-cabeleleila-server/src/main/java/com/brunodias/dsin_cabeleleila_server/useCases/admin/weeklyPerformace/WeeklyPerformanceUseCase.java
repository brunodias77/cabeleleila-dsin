package com.brunodias.dsin_cabeleleila_server.useCases.admin.weeklyPerformace;

import com.brunodias.dsin_cabeleleila_server.dtos.BaseResponseDTO;
import com.brunodias.dsin_cabeleleila_server.dtos.WeeklyPerformanceDTO;
import com.brunodias.dsin_cabeleleila_server.dtos.requests.RequestWeeklyPerformaceAdmin;
import com.brunodias.dsin_cabeleleila_server.entities.Appointment;
import com.brunodias.dsin_cabeleleila_server.enums.AppointmentStatus;
import com.brunodias.dsin_cabeleleila_server.repositories.AppointmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class WeeklyPerformanceUseCase implements IWeeklyPerformanceUseCase{
    private final AppointmentRepository _appointmentRepository;
    @Override
    public BaseResponseDTO execute(RequestWeeklyPerformaceAdmin request) {
           if (request.getStartDate() == null || request.getEndDate() == null || request.getStartDate().isAfter(request.getEndDate())) {
                return BaseResponseDTO.builder()
                    .status(400)
                    .message("As datas fornecidas são inválidas")
                    .build();
            }

        AppointmentStatus statusScheduled = AppointmentStatus.AGENDADO;
        AppointmentStatus statusCanceled = AppointmentStatus.CANCELADO;

        int totalScheduledServices = _appointmentRepository.countByAppointmentDateBetweenAndStatus(request.getStartDate(), request.getEndDate(), statusScheduled);
        int totalCancelledServices = _appointmentRepository.countByAppointmentDateBetweenAndStatus(request.getStartDate(), request.getEndDate(), statusCanceled);

       var appointments = _appointmentRepository.findAppointmentsBetweenDates(request.getStartDate(), request.getEndDate());
        double totalRevenue = 0.0;

        for (var appointment : appointments) {
            if(appointment.getStatus() == AppointmentStatus.AGENDADO){
                for (var service : appointment.getServices()) {
                    totalRevenue += service.getPrice();
                }
            }

        }
        WeeklyPerformanceDTO weeklyPerformanceDTO = WeeklyPerformanceDTO.builder()
                .totalRevenue(totalRevenue)
                .totalCancelledServices(totalCancelledServices)
                .totalScheduledServices(totalScheduledServices)
                .build();


        return BaseResponseDTO.builder()
                .status(200)
                .data(weeklyPerformanceDTO)
                .build();
    }
}

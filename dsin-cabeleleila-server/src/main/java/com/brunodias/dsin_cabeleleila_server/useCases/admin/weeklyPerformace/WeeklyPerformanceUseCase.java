package com.brunodias.dsin_cabeleleila_server.useCases.admin.weeklyPerformace;

import com.brunodias.dsin_cabeleleila_server.dtos.BaseResponseDTO;
import com.brunodias.dsin_cabeleleila_server.dtos.WeeklyPerformanceDTO;
import com.brunodias.dsin_cabeleleila_server.enums.AppointmentStatus;
import com.brunodias.dsin_cabeleleila_server.repositories.AppointmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class WeeklyPerformanceUseCase implements IWeeklyPerformanceUseCase{
    private final AppointmentRepository _appointmentRepository;
    @Override
    public BaseResponseDTO execute(RequestWeeklyPerformaceAdmin request) {
        LocalDate today = LocalDate.now();
        LocalDate startOfWeek = today.minusDays(today.getDayOfWeek().getValue() - 1);
        LocalDate endOfWeek = startOfWeek.plusDays(6);
        AppointmentStatus statusScheduled = AppointmentStatus.AGENDADO;
        AppointmentStatus statusCanceled = AppointmentStatus.CANCELADO;

        int totalScheduledServices = _appointmentRepository.countByAppointmentDateBetweenAndStatus(startOfWeek, endOfWeek, statusScheduled);
        int totalCancelledServices = _appointmentRepository.countByAppointmentDateBetweenAndStatus(startOfWeek, endOfWeek, statusCanceled);
        BigDecimal totalRevenue = _appointmentRepository.calculateRevenueByAppointmentDateBetween(startOfWeek, endOfWeek);

        WeeklyPerformanceDTO weeklyPerformanceDTO = WeeklyPerformanceDTO.builder().totalRevenue(totalRevenue).totalCancelledServices(totalCancelledServices).totalScheduledServices(totalScheduledServices).build();
           // Verifica se as datas são válidas
           if (startDate == null || endDate == null || startDate.isAfter(endDate)) {
                return BaseResponseDTO.builder()
                    .status(400)
                    .message("As datas fornecidas são inválidas")
                    .build();
            }

        // Estatus dos agendamentos
        AppointmentStatus statusScheduled = AppointmentStatus.AGENDADO;
        AppointmentStatus statusCanceled = AppointmentStatus.CANCELADO;

        // Contagem de serviços agendados e cancelados
        int totalScheduledServices = _appointmentRepository.countByAppointmentDateBetweenAndStatus(startDate, endDate, statusScheduled);
        int totalCancelledServices = _appointmentRepository.countByAppointmentDateBetweenAndStatus(startDate, endDate, statusCanceled);

        // Cálculo da receita total no período
        BigDecimal totalRevenue = _appointmentRepository.calculateRevenueByAppointmentDateBetween(startDate, endDate);

        // Criando o DTO com os dados calculados
        WeeklyPerformanceDTO weeklyPerformanceDTO = WeeklyPerformanceDTO.builder()
                .totalRevenue(totalRevenue)
                .totalCancelledServices(totalCancelledServices)
                .totalScheduledServices(totalScheduledServices)
                .build();

        return BaseResponseDTO.builder()
                .status(200)
                .data(weeklyPerformanceDTO)
                .build();
        
        return BaseResponseDTO.builder().status(200).data(weeklyPerformanceDTO).build();
    }
}

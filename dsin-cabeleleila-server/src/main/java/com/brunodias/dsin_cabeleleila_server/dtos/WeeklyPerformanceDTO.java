package com.brunodias.dsin_cabeleleila_server.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WeeklyPerformanceDTO {
    private int totalScheduledServices;
    private int totalCancelledServices;
    private BigDecimal totalRevenue;
}
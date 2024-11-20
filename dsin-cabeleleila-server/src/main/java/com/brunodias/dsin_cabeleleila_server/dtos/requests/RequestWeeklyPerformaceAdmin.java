package com.brunodias.dsin_cabeleleila_server.dtos.requests;

import lombok.Data;

import java.time.LocalDate;

@Data
public class RequestWeeklyPerformaceAdmin {
    LocalDate startDate;
    LocalDate endDate;
}

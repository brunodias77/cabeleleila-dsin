package com.brunodias.dsin_cabeleleila_server.dtos.requests;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

public record RequestCreateAppointment(

        @NotNull(message = "A data do agendamento é obrigatória.")
        @Future(message = "A data do agendamento deve ser no futuro.")
        LocalDate appointmentDate,

        @NotNull(message = "A hora do agendamento é obrigatória.")
        LocalTime appointmentTime,

        @NotNull(message = "A lista de serviços não pode estar vazia.")
        @NotEmpty(message = "É necessário selecionar ao menos um serviço.")
        List<UUID> serviceId

) {
}
package com.brunodias.dsin_cabeleleila_server.dtos.requests;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public record RequestCreateAppointment(
        @NotNull(message = "A lista de serviços não pode estar vazia.")
        @NotEmpty(message = "É necessário selecionar ao menos um serviço.")
        List<UUID> serviceId,

        @NotNull(message = "A data e hora do agendamento são obrigatórias.")
        @Future(message = "A data do agendamento deve ser no futuro.")
        LocalDate appointmentDate
) {
}

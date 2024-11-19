package com.brunodias.dsin_cabeleleila_server.dtos.requests;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Set;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RequestUpdateAppointmentAdmin {
    @NotNull(message = "A data e hora do agendamento são obrigatórias.")
    @Future(message = "A data e hora do agendamento devem ser no futuro.")
     LocalDate appointmentDate;

    @NotNull(message = "A hora do agendamento é obrigatória.")
    LocalTime appointmentTime;

    @NotNull(message = "A lista de serviços não pode estar vazia.")
    @NotEmpty(message = "É necessário selecionar ao menos um serviço.")
     Set<UUID> serviceId;

}

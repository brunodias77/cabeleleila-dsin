package com.brunodias.dsin_cabeleleila_server.controllers;


import com.brunodias.dsin_cabeleleila_server.dtos.AppointmentDetailsDTO;
import com.brunodias.dsin_cabeleleila_server.dtos.BaseResponseDTO;
import com.brunodias.dsin_cabeleleila_server.dtos.requests.RequestCreateAppointment;
import com.brunodias.dsin_cabeleleila_server.dtos.requests.RequestUpdateAppointment;
import com.brunodias.dsin_cabeleleila_server.useCases.appointments.GetAllUserAppointment.GetAllUserAppointmentUseCase;
import com.brunodias.dsin_cabeleleila_server.useCases.appointments.UpdateAppointment.UpdateAppointmentUseCase;
import com.brunodias.dsin_cabeleleila_server.useCases.appointments.createAppointment.CreateAppointmentUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/appointment")
@RequiredArgsConstructor
@Tag(name = "Agendamentos", description = "Controller de agendamentos")
public class AppointmentController {

    private final GetAllUserAppointmentUseCase _getAllUserAppointmentUseCase;
    private final UpdateAppointmentUseCase _updateAppointmentUseCase;
    private final CreateAppointmentUseCase _createAppointmentUseCase;

    @PostMapping("/create")
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Agendamento", description = "Rota para criar um novo agendamento. O usuário deve estar logado e o agendamento deve ocorrer no prazo mínimo de 2 dias.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Agendamento bem sucedido", content = {
                    @Content(schema = @Schema(implementation = BaseResponseDTO.class))
            }),
            @ApiResponse(responseCode = "401", description = "Credenciais inválidas"),
            @ApiResponse(responseCode = "400", description = "Erro de validação do agendamento"),
            @ApiResponse(responseCode = "404", description = "Serviço não encontrado")
    })
    public ResponseEntity<BaseResponseDTO> createAppointment(@RequestBody RequestCreateAppointment request) {
        try{
            var result = this._createAppointmentUseCase.execute(request);
            return ResponseEntity.status(result.getStatus()).body(result);
        }catch (Exception err) {
            return ResponseEntity.status(400).body(BaseResponseDTO.builder()
                    .status(400)
                    .message(err.getMessage())
                    .build());
        }

    }
    @GetMapping("/all")
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Listar Agendamentos", description = "Rota para listar todos os agendamentos.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista de agendamentos obtida com sucesso", content = {
                    @Content(schema = @Schema(implementation = AppointmentDetailsDTO[].class))
            }),
            @ApiResponse(responseCode = "404", description = "Nenhum agendamento encontrado")
    })
    public ResponseEntity<List<AppointmentDetailsDTO>> getAllUserAppointment() {
        try {
            var appointments = this._getAllUserAppointmentUseCase.execute();
            return ResponseEntity.ok(appointments);
        } catch (Exception err) {
            return ResponseEntity.status(404).body(null);
        }
    }

    @PutMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Atualizar Agendamento", description = "Rota para atualizar um agendamento existente pelo ID. O usuário deve estar logado.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Agendamento atualizado com sucesso", content = {
                    @Content(schema = @Schema(implementation = BaseResponseDTO.class))
            }),
            @ApiResponse(responseCode = "400", description = "Erro de validação ao atualizar o agendamento"),
            @ApiResponse(responseCode = "404", description = "Agendamento não encontrado")
    })
    public ResponseEntity<BaseResponseDTO> updateAppointment(@PathVariable UUID id, @RequestBody @Valid RequestUpdateAppointment request) {
        try {
            var result = this._updateAppointmentUseCase.execute(id, request);
            return ResponseEntity.status(result.getStatus()).body(result);
        } catch (Exception err) {
            return ResponseEntity.status(400).body(BaseResponseDTO.builder()
                    .status(400)
                    .message(err.getMessage())
                    .build());
        }
    }
}

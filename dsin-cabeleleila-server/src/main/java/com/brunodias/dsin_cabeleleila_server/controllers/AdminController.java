package com.brunodias.dsin_cabeleleila_server.controllers;

import com.brunodias.dsin_cabeleleila_server.dtos.AppointmentDetailsDTO;
import com.brunodias.dsin_cabeleleila_server.dtos.BaseResponseDTO;
import com.brunodias.dsin_cabeleleila_server.dtos.requests.RequestCreateServiceAdmin;
import com.brunodias.dsin_cabeleleila_server.dtos.requests.RequestUpdateAppointmentAdmin;
import com.brunodias.dsin_cabeleleila_server.dtos.requests.RequestWeeklyPerformaceAdmin;
import com.brunodias.dsin_cabeleleila_server.useCases.admin.cancelAppointmentAdmin.CancelAppointmentAdminUseCase;
import com.brunodias.dsin_cabeleleila_server.useCases.admin.confirmAppointment.ConfirmAppointmentAdminUseCase;
import com.brunodias.dsin_cabeleleila_server.useCases.admin.createServiceAdmin.CreateServiceAdminUseCase;
import com.brunodias.dsin_cabeleleila_server.useCases.admin.getAllAppointments.GetAllAppointmentsUseCase;
import com.brunodias.dsin_cabeleleila_server.useCases.admin.updateAppointmentAdmin.UpdateAppointmentAdminUseCase;
import com.brunodias.dsin_cabeleleila_server.useCases.admin.weeklyPerformace.WeeklyPerformanceUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {
    private final GetAllAppointmentsUseCase _getAllAppointmentsUseCase;
    private final WeeklyPerformanceUseCase _weeklyPerformanceUseCase;
    private final UpdateAppointmentAdminUseCase _updateAppointmentAdminUseCase;
    private final CancelAppointmentAdminUseCase _cancelAppointmentAdminUseCase;
    private final CreateServiceAdminUseCase _createServiceAdminUseCase;
    private final ConfirmAppointmentAdminUseCase _confirmAppointmentAdminUseCase;
    @GetMapping("/all-appointments")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Operation(summary = "Listar Agendamentos", description = "Rota excluvisa para administradores para listar todos os agendamentos.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista de agendamentos obtida com sucesso", content = {
                    @Content(schema = @Schema(implementation = AppointmentDetailsDTO[].class))
            }),
            @ApiResponse(responseCode = "404", description = "Nenhum agendamento encontrado")
    })
    public ResponseEntity<BaseResponseDTO> getAllAppointments() {
        try {
            var appointments = this._getAllAppointmentsUseCase.execute();
            return ResponseEntity.status(200).body(appointments);
        } catch (Exception err) {
            return ResponseEntity.status(404).body(null);
        }
    }

    @PostMapping("/performace")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity getWeeklyPerformance(@RequestBody @Valid RequestWeeklyPerformaceAdmin request) {

        try {
            BaseResponseDTO performance = _weeklyPerformanceUseCase.execute(request);
            return ResponseEntity.status(200).body(performance);
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }

    @PutMapping("/update-appointment/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity updateAppointmentAdmin(@PathVariable UUID id, @RequestBody @Valid RequestUpdateAppointmentAdmin request){
        try{

            var response = _updateAppointmentAdminUseCase.execute(id, request);
            return ResponseEntity.status(200).body(response);
        }catch (Exception e){
            return ResponseEntity.status(500).build();
        }
    }


    @PutMapping("/cancel-appointment/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity cancelAppointmentAdmin(@PathVariable UUID id){
        try{
            var response = _cancelAppointmentAdminUseCase.execute(id);
            return ResponseEntity.status(200).body(response);
        }catch (Exception e){
            return ResponseEntity.status(500).build();
        }
    }

    @PostMapping("/create-service")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity createServiceAdmin(@RequestBody RequestCreateServiceAdmin request){
        try{
            var response = _createServiceAdminUseCase.execute(request);
            return ResponseEntity.status(200).body(response);
        }catch (Exception e){
            return ResponseEntity.status(500).build();
        }
    }

    @PutMapping("/confirm-appointment/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity confirmAppointmentAdmin(@PathVariable UUID id){
        try{
            var response = _confirmAppointmentAdminUseCase.execute(id);
            return ResponseEntity.status(200).body(response);
        }catch (Exception e){
            return ResponseEntity.status(500).build();
        }
    }

}

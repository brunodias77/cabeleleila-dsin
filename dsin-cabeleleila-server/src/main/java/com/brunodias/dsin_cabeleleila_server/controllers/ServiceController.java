package com.brunodias.dsin_cabeleleila_server.controllers;

import com.brunodias.dsin_cabeleleila_server.dtos.BaseResponseDTO;
import com.brunodias.dsin_cabeleleila_server.entities.Service;
import com.brunodias.dsin_cabeleleila_server.entities.User;
import com.brunodias.dsin_cabeleleila_server.useCases.services.getAllServices.GetAllServicesUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/service")
@RequiredArgsConstructor
@Tag(name = "Services", description = "Controller para os servicos prestados no salao de beleza")
public class ServiceController {
    private final GetAllServicesUseCase _getAllServicesUseCase;
    @GetMapping("/all")
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Obter todos os serviços", description = "Rota para buscar todos os serviços do salão de beleza")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Busca realizada com sucesso", content = {
                    @Content(schema = @Schema(implementation = BaseResponseDTO.class))
            }),
            @ApiResponse(responseCode = "401", description = "Credenciais inválidas"),
            @ApiResponse(responseCode = "400", description = "Erro de validação"),
            @ApiResponse(responseCode = "404", description = "Serviço não encontrado")
    })
    public ResponseEntity<BaseResponseDTO> getAllServices() {
        try {
            BaseResponseDTO result = _getAllServicesUseCase.execute();
            return ResponseEntity.status(result.getStatus()).body(result);
        } catch (Exception err) {
            return  ResponseEntity.status(401).body(BaseResponseDTO.builder()
                    .status(400)
                    .message(err.getMessage())
                    .build());
        }
    }
}

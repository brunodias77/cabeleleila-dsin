package com.brunodias.dsin_cabeleleila_server.controllers;

import com.brunodias.dsin_cabeleleila_server.dtos.BaseResponseDTO;
import com.brunodias.dsin_cabeleleila_server.entities.User;
import com.brunodias.dsin_cabeleleila_server.mappers.EntityToDtoMapper;
import com.brunodias.dsin_cabeleleila_server.repositories.UserRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
@Tag(name = "Usuarios", description = "Controller de usuarios")
public class UserController {
    private final UserRepository _userRepository;

    @GetMapping("")
    @Operation(summary = "busca por usuario", description = "Esse metoodo busca um usuario pelo token jwt")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Busca bem-sucedida", content = {
                    @Content(schema = @Schema(implementation = User.class))
            }),
            @ApiResponse(responseCode = "401", description = "Credenciais inválidas")
    })
    public ResponseEntity<?> getUserByToken() {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String email = authentication.getName();
            var user = _userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("Usuario nao foi encontrado !"));
            var userDTO = EntityToDtoMapper.mapUserToDtoBasic(user);
            return ResponseEntity.status(200).body(BaseResponseDTO.builder()
                    .status(200)
                    .message("Usuario encontrado com sucesso !").data(userDTO).build());
        } catch (Exception err) {
            return ResponseEntity.status(401).body(err.getMessage());
        }
    }

}


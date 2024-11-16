package com.brunodias.dsin_cabeleleila_server.controllers;
import com.brunodias.dsin_cabeleleila_server.dtos.requests.RequestLoginUser;
import com.brunodias.dsin_cabeleleila_server.dtos.requests.RequestRegisterUser;
import com.brunodias.dsin_cabeleleila_server.entities.User;
import com.brunodias.dsin_cabeleleila_server.useCases.authentications.loginUser.LoginUserUseCase;
import com.brunodias.dsin_cabeleleila_server.useCases.authentications.registerUser.RegisterUserUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Tag(name = "Autenticação", description = "Controller de autenticacao de usuarios")
public class AuthenticationController {

    private final RegisterUserUseCase  _registerUserUseCase;
    private final LoginUserUseCase _loginUserUseCase;
    @PostMapping("/register-user")
    @Operation(summary = "Cadastro de candidato", description = "Essa função é responsável por cadastrar um candidato")
    @ApiResponses({
            @ApiResponse(responseCode = "201", content = {
                    @Content(schema = @Schema(implementation = User.class))
            }),
            @ApiResponse(responseCode = "400", description = "Usuário já existe")
    })
    public ResponseEntity<?> registerUser(@RequestBody @Valid RequestRegisterUser request) {
        try {
            var result = this._registerUserUseCase.execute(request);
            return ResponseEntity.status(201).body(result);
        } catch (Exception err) {
            return ResponseEntity.badRequest().body(err.getMessage());
        }
    }

    @PostMapping("/login")
    @Operation(summary = "Login de usuário", description = "Essa função autentica um usuário no sistema")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Login bem-sucedido", content = {
                    @Content(schema = @Schema(implementation = User.class))
            }),
            @ApiResponse(responseCode = "401", description = "Credenciais inválidas")
    })
    public ResponseEntity<?> loginUser(@RequestBody @Valid RequestLoginUser request) {
        try {
            var result = this._loginUserUseCase.execute(request);
            return ResponseEntity.ok().body(result);
        } catch (Exception err) {
            return ResponseEntity.status(401).body(err.getMessage());
        }
    }
}

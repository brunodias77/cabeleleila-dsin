package com.brunodias.dsin_cabeleleila_server.dtos.requests;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RequestLoginUser(
        @NotBlank(message = "Email é obrigatório.")
        @Email(message = "Email deve ser um endereço de email válido.")
        String email,

        @NotBlank(message = "Senha é obrigatória.")
        @Size(min = 6, message = "A senha deve ter pelo menos 6 caracteres.")
        String password)  {
}

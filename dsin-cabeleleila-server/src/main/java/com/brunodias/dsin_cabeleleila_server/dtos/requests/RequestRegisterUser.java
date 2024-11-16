package com.brunodias.dsin_cabeleleila_server.dtos.requests;

public record RequestRegisterUser(
        String name,
        String email,
        String phoneNumber,
        String password
) {
}

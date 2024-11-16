package com.brunodias.dsin_cabeleleila_server.dtos;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BaseResponseDTO<T> {
    private int status;
    private String message;
    private String jwt;
    private String role;
    private final LocalDateTime timestamp = LocalDateTime.now();
    private T data;
}


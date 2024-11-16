package com.brunodias.dsin_cabeleleila_server.exceptions;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException() {
        super("Um ou mais serviços não encontrados.");
    }
}

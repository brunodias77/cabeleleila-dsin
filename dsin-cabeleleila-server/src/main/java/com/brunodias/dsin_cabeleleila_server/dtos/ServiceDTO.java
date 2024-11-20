package com.brunodias.dsin_cabeleleila_server.dtos;

import lombok.Data;

import java.util.UUID;

@Data
public class ServiceDTO {
    private UUID id;
    private String serviceName;
    private double servicePrice;

    // Construtores
    public ServiceDTO(UUID id, String serviceName, double servicePrice) {
        this.id = id;
        this.serviceName = serviceName;
        this.servicePrice = servicePrice;
    }

}
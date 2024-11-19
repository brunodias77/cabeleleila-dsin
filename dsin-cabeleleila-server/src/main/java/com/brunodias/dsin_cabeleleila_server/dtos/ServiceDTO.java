package com.brunodias.dsin_cabeleleila_server.dtos;

public class ServiceDTO {
    private String serviceName;
    private double servicePrice;

    // Construtores
    public ServiceDTO(String serviceName, double servicePrice) {
        this.serviceName = serviceName;
        this.servicePrice = servicePrice;
    }

    // Getters e setters
    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public double getServicePrice() {
        return servicePrice;
    }

    public void setServicePrice(double servicePrice) {
        this.servicePrice = servicePrice;
    }
}

package com.brunodias.dsin_cabeleleila_server.dtos.requests;

import lombok.Data;

@Data
public class RequestCreateServiceAdmin {
    private String name;

    private String description;

    private double price;
}

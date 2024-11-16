package com.brunodias.dsin_cabeleleila_server.exceptions;


public class AppointmentNotFound extends RuntimeException {
    public AppointmentNotFound(){
        super("Agendamento nao foi encontrado !");
    }
}

package com.brunodias.dsin_cabeleleila_server.exceptions;

public class UserFoundException extends RuntimeException {
    public UserFoundException(){
        super("Usuario ja existe !");
    }
}

package com.example.transport.request;

public record PassageiroRequest (

        String nome,
        String sobrenome,
        String phone,
        String email,
        String password,
        Integer idade

){
}

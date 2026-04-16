package com.example.transport.request;

public record PassageiroRequest (

        String nome,
        String sobrenome,
        Integer phone,
        String email,
        String password,
        Integer idade

){
}

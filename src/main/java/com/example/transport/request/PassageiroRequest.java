package com.example.transport.request;

public record PassageiroRequest (
        Long id,
        String nome,
        String sobrenome,
        String phone,
        String email,
        Double cpf,
        String password,
        Integer idade

){
}

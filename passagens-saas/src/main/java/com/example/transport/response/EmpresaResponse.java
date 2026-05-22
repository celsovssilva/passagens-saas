package com.example.transport.response;

public record EmpresaResponse(
        String email,
        String telefone,
        String endereco,
        String cnpj,
        String razaoSocial
) {
}

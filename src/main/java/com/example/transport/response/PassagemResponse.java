package com.example.transport.response;

import com.example.transport.entity.Passagem;

public record PassagemResponse(
        String nomePassageiro,
        String email,
        String documento,
        String origem,
        String Destino
) {
    public PassagemResponse(Passagem passagem){
        this(
            passagem.getNomePassageiro(),
                passagem.getUser().getEmail(),
                passagem.getCpf(),
                passagem.getViagem().getRota().getOrigem(),
                passagem.getViagem().getRota().getDestino()
        );
    }

}

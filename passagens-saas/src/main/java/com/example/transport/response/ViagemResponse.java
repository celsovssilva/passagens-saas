package com.example.transport.response;

import com.example.transport.entity.Viagem;

import java.time.LocalDateTime;

public record ViagemResponse(

        String origem,
        String destino,
        LocalDateTime dataSaida
) {
    public ViagemResponse(Viagem v){
        this(v.getOrigem(),v.getDestino(),v.getDataSaida());
    }
}

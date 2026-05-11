package com.example.transport.response;

import com.example.transport.entity.Viagem;

import java.time.LocalDateTime;

public record ViagemResponse(

        Integer capacidade,
        LocalDateTime dataSaida
) {
    public ViagemResponse(Viagem v){
        this(v.getCapacidade(),v.getDataSaida());
    }
}

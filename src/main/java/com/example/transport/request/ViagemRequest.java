package com.example.transport.request;

import java.time.LocalDateTime;
public record ViagemRequest(
        Long id,
        String origem,
        String destino,
        LocalDateTime dataSaida,
        Double valorTotal,
        Long passageiroId,
        Long transportId,
        String ufOrigem,
        String ufDestino,
        Integer capacidade
) {
}
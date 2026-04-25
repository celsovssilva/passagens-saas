package com.example.transport.request;

import java.time.LocalDateTime;
public record ViagemRequest(
        Long id,
        String origem,
        String destino,
        LocalDateTime dataSaida,
        Long passageiroId,
        Long transportId
) {
}
package com.example.transport.response;

import com.example.transport.entity.Rotas;

public record RotaResponse(
        Long id,
        String origem,
        String ufOrigem,
        String destino,
        String ufDestino,
        Double valorBase,
        String horarioFormatado // Ex: "08:00"
) {

    public RotaResponse(Rotas rota) {
        this(
                rota.getId(),
                rota.getOrigem(),
                rota.getUfOrigem(),
                rota.getDestino(),
                rota.getUfDestino(),
                rota.getValor(),
                rota.getHorario().toString()
        );
    }
}
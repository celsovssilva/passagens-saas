package com.example.transport.response;

import com.example.transport.entity.Passagem;
import com.example.transport.entity.User;
import com.example.transport.request.PassageiroRequest;

import java.time.LocalDateTime;
import java.util.List;

public record CompraResponse(
        Long id,
        String status,
        LocalDateTime dataCompra,
        List<Passagem> passagens
) {
}

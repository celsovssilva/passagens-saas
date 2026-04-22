package com.example.transport.service;

import com.example.transport.entity.Viagem;
import com.example.transport.request.ViagemRequest;
import com.example.transport.response.ViagemResponse;

import java.util.Optional;

public interface ViagemService {
     ViagemResponse agendarViagem(ViagemRequest viagemRequest);
     Optional<Viagem> buscarViagemPorId(Long id);

     void deleteViagem(Long idViagem);
}

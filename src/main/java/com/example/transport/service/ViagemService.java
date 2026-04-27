package com.example.transport.service;

import com.example.transport.entity.Viagem;
import com.example.transport.request.ViagemRequest;
import com.example.transport.response.PassageiroResponse;
import com.example.transport.response.ViagemResponse;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public interface ViagemService {
     ViagemResponse agendarViagem(ViagemRequest viagemRequest);
     Optional<Viagem> buscarViagemPorId(Long id);
     ViagemResponse cadastrarViagem(ViagemRequest viagemRequest);
     List<PassageiroResponse> buscarPassageirosporViagem(Long viagemId);
     void deleteViagem(Long idViagem);
}

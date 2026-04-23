package com.example.transport.service.IMPL;

import com.example.transport.entity.Passageiro;
import com.example.transport.entity.Viagem;
import com.example.transport.repository.PassageiroRepository;
import com.example.transport.repository.ViagemRepository;
import com.example.transport.request.ViagemRequest;
import com.example.transport.response.ViagemResponse;
import com.example.transport.service.ViagemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public class ViagemServiceIMPL implements ViagemService {
    @Autowired
    private ViagemRepository viagemRepository;
    @Autowired
    private PassageiroRepository passageiroRepository;

    @Override
    public ViagemResponse agendarViagem(ViagemRequest viagemRequest) {
        Viagem viagem = viagemRepository.findById(viagemRequest.id())
                .orElseThrow(() -> new RuntimeException("viagem não encontrada"));
        Passageiro passageiro = passageiroRepository.findById(viagemRequest.passageiroId())
                .orElseThrow(()-> new RuntimeException("passageiro não encontrado"));
        Integer capacidade = viagem.getTransport().getVagas();
        if (viagem.getPassageiro().size() >= capacidade) {
            throw new RuntimeException("o veiculo " + viagem.getTransport().getName() + "está lotado");

        }
        viagem.getPassageiro().add(passageiro);
        viagemRepository.save(viagem);
        return new ViagemResponse(viagem);
    }

    @Override
    public Optional<Viagem> buscarViagemPorId(Long id) {
        Viagem viagem = viagemRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("viagem não encontrada"));

        return viagemRepository.findById(id);
    }

    @Override
    public void deleteViagem(Long idViagem) {

        viagemRepository.deleteById(idViagem);

    }
}

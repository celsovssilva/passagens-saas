package com.example.transport.service.IMPL;

import com.example.transport.entity.Passageiro;
import com.example.transport.entity.Transport;
import com.example.transport.entity.Viagem;
import com.example.transport.repository.PassageiroRepository;
import com.example.transport.repository.TransportRepository;
import com.example.transport.repository.ViagemRepository;
import com.example.transport.request.ViagemRequest;
import com.example.transport.response.PassageiroResponse;
import com.example.transport.response.ViagemResponse;
import com.example.transport.service.LocalidadeService;
import com.example.transport.service.ViagemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Service
public class ViagemServiceIMPL implements ViagemService {
    @Autowired
    private ViagemRepository viagemRepository;
    @Autowired
    private PassageiroRepository passageiroRepository;
    @Autowired
    private TransportRepository transportRepository;
    @Autowired
    private LocalidadeService localidadeService;

    @Override
    public ViagemResponse agendarViagem(ViagemRequest viagemRequest) {
        Viagem viagem = viagemRepository.findById(viagemRequest.id())
                .orElseThrow(() -> new RuntimeException("viagem não encontrada"));
        Passageiro passageiro = passageiroRepository.findById(viagemRequest.passageiroId())
                .orElseThrow(()-> new RuntimeException("passageiro não encontrado"));
        Integer capacidade = viagem.getTransport().getVagas();
        if (viagem.getPassageiro().size() >= capacidade) {
            throw new RuntimeException("o veiculo " + viagem.getTransport().getModelo() + "está lotado");

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
    public ViagemResponse cadastrarViagem(ViagemRequest viagemRequest) {
        if(!localidadeService.validarCidade(viagemRequest.ufOrigem(), viagemRequest.origem()) ||
        !localidadeService.validarCidade(viagemRequest.ufDestino(), viagemRequest.destino())){
            throw new RuntimeException("Localidade não reconhecida pelo IBGE,Cadastro cancelado !");

        }
        Transport transport= transportRepository.findById(viagemRequest.transportId())
                .orElseThrow(()-> new RuntimeException("transporte não encontrada"));
        Viagem v = new Viagem();
        v.setOrigem(viagemRequest.origem());
        v.setDestino(viagemRequest.destino());
        v.setDataSaida(viagemRequest.dataSaida());
        v.setTransport(transport);
        v.setCapacidade(viagemRequest.capacidade());
        v.setValorTotal(viagemRequest.valorTotal());

        viagemRepository.save(v);
        return new  ViagemResponse(v);
    }

    @Override
    public List<PassageiroResponse> buscarPassageirosporViagem(Long viagemId) {
        Viagem v = viagemRepository.findById(viagemId)
                .orElseThrow(() -> new RuntimeException("Viagem não encontrada"));

        return v.getPassageiro().stream()
                .map(p -> new PassageiroResponse(
                        p.getNome(),
                        p.getUser().getEmail(),
                        p.getPhone(),
                        p.getIdade()
                ))
                .toList();
    }

    @Override
    public void deleteViagem(Long idViagem) {

        viagemRepository.deleteById(idViagem);

    }

    @Override
    public List<ViagemResponse> buscarViagem(String origem, String destino, LocalDateTime data) {
        List<Viagem> viagensEncontradas = viagemRepository.buscarPorDataERota(origem,destino,data);
        return viagensEncontradas.stream()
                .map(ViagemResponse::new).toList();
    }
}

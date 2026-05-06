package com.example.transport.service.IMPL;

import com.example.transport.entity.*;
import com.example.transport.repository.CompraRepository;
import com.example.transport.repository.PassageiroRepository;
import com.example.transport.repository.UserRepository;
import com.example.transport.repository.ViagemRepository;
import com.example.transport.request.CompraRequest;
import com.example.transport.response.CompraResponse;
import com.example.transport.service.CompraService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.UUID;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class CompraServiceIMPL implements CompraService {
    @Autowired
    CompraRepository compraRepository;
    @Autowired
    ViagemRepository viagemRepository;
    @Autowired
    PassageiroRepository passageiroRepository;
    @Autowired
    UserRepository userRepository;
    @Transactional
    @Override
    public CompraResponse comprar(CompraRequest compra) {
        Viagem v = viagemRepository.findById(compra.viagemId())
                .orElseThrow(()-> new RuntimeException("Viagem não encontrada"));

        User comprador = userRepository.findById(compra.usuarioId())
                .orElseThrow(()-> new RuntimeException("usuário não encontrado"));
        int quantidadeDePassagens = compra.passageiro().size();
        Double valorTotal = v.getValorTotal() * quantidadeDePassagens;
        if(quantidadeDePassagens > v.getCapacidade()) {
            throw new RuntimeException("Não há assentos suficientes");
        }
        Compra compra1 = new Compra();
        compra1.setUser(comprador);
        compra1.setValor(valorTotal);
        compra1.setDataCompra(LocalDateTime.now());
        compra1.setMetodoPagamento(compra.metodo());


        List<Passagem> passagens = compra.passageiro().stream().map(p -> {
            Passagem passagem = new Passagem();
            passagem.setNomePassageiro(p.nome());
            passagem.setCpf(String.valueOf(p.cpf()));
            passagem.setCompra(compra1);
            passagem.setViagem(v);
            passagem.setDataHoraDaCompra(LocalDateTime.now());
            return passagem;
        }).toList();

        compra1.setPassagens(passagens);
        compra1.setValor(v.getValorTotal() * quantidadeDePassagens);


        if(compra.metodo() == MetodoPagamento.PIX){
                compra1.setStatus(StatusPagamento.PENDENTE);
                compra1.setPixCopiaECola("PIX-COPIA-COLA" + UUID.randomUUID());
        } else if (compra.metodo() == MetodoPagamento.CARTAO_CREDITO) {
            compra1.setStatus(StatusPagamento.APROVADO);
        }
        v.setCapacidade(v.getCapacidade() - quantidadeDePassagens);
        viagemRepository.save(v);
        Compra compra2 = compraRepository.save(compra1);
        return new CompraResponse(compra2);
    }

    @Override
    public void excluir(Long id) {
        Compra compra = compraRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("compra não encontrada"));
        // calcula a diferença entre dias desde a data da compra até a data atual
        long diaDaCompra = ChronoUnit.DAYS.between(compra.getDataCompra(),LocalDateTime.now());
        if(diaDaCompra >=3) {
            throw new RuntimeException("O prazo para cancelamento expirou");
        }
        System.out.println(" Solicitando extorno do valor" + compra.getValor() + "para cliente");
        compra.setStatus(StatusPagamento.CANCELADO);

        Viagem v = compra.getPassagens().get(0).getViagem();
        // devolve as vagas
        int vagasDevolvidas = compra.getPassagens().size();
        v.devolverVagas(vagasDevolvidas);
        viagemRepository.save(v);
        compraRepository.save(compra);


    }


    @Override
    public List<CompraResponse> historico(Long userId) {
            List<Compra> compras = compraRepository.findByUserId(userId);
        return compras.stream().map(CompraResponse::new).toList();
    }

    @Override
    public void confirmarPagamento(Long idCompra) {
        Compra c = compraRepository.findById(idCompra)
                .orElseThrow(() -> new RuntimeException("compra inexistente"));
        if(!c.getStatus().equals(StatusPagamento.PENDENTE)) {
            throw  new RuntimeException("Operação inválida. O status atual é" + StatusPagamento.CANCELADO);
        }
        c.setStatus(StatusPagamento.APROVADO);
        compraRepository.save(c);
    }
}

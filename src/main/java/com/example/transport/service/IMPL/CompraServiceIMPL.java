package com.example.transport.service.IMPL;

import com.example.transport.entity.Compra;
import com.example.transport.entity.Viagem;
import com.example.transport.repository.CompraRepository;
import com.example.transport.repository.ViagemRepository;
import com.example.transport.request.CompraRequest;
import com.example.transport.response.CompraResponse;
import com.example.transport.service.CompraService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class CompraServiceIMPL implements CompraService {
    @Autowired
    CompraRepository compraRepository;
    @Autowired
    ViagemRepository viagemRepository;
    @Override
    public CompraResponse comprar(CompraRequest compra) {
        Viagem v = viagemRepository.findById(compra.viagemId())
                .orElseThrow(()-> new RuntimeException("Viagem não encontrada"));
        int quantidadeDePassagens = compra.passageiro().size();
        Double valorTotal = v.getValorTotal() * quantidadeDePassagens;
        if(quantidadeDePassagens > v.getCapacidade()) {
            throw new RuntimeException("Não há assentos suficientes");
        }
        Compra compra1 = new Compra();
        compra1.setValor(valorTotal);
        compra1.setDataCompra(LocalDateTime.now());
        compra1.setStatus("Aguardando Pagamento");
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
        compra.setStatus("COMPRA CANCELADA");

        Viagem v = compra.getPassagens().get(0).getViagem();
        // devolve as vagas
        int vagasDevolvidas = compra.getPassagens().size();
        v.devolverVagas(vagasDevolvidas);
        viagemRepository.save(v);
        compraRepository.save(compra);


    }


    @Override
    public List<CompraResponse> historico(Long id) {
            List<Compra> compras = compraRepository.findByUserId(id);
        return compras.stream().map(compra -> new CompraResponse(compra)).toList();
    }

    @Override
    public void confirmarPagamento(Long idCompra) {
        Compra c = compraRepository.findById(idCompra)
                .orElseThrow(() -> new RuntimeException("compra inexistente"));
        if(!c.getStatus().equals("AGUARDANDO PAGAMENTO")) {
            throw  new RuntimeException("Operação inválida. O status atual é" + c.getStatus());
        }
        c.setStatus("PAGAMENTO COCLUIDO");
        compraRepository.save(c);
    }
}

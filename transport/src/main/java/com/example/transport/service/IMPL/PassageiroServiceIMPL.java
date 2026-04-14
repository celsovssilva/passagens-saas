package com.example.transport.service.IMPL;

import com.example.transport.entity.Passageiro;
import com.example.transport.entity.User;
import com.example.transport.repository.PassageiroRepository;
import com.example.transport.service.PassageiroService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class PassageiroServiceIMPL  implements PassageiroService {

@Autowired
private PassageiroRepository passageiroRepository;

    @Override
    public List<Passageiro> buscarPassageiros() {
        return passageiroRepository.findAll();
    }

    @Override
    public Passageiro cadastrarPassageiro(Passageiro passageiro) {
        return passageiroRepository.save(passageiro);
    }

    @Override
    public void removerPassageiro(Passageiro passageiro) {
        passageiroRepository.delete(passageiro);
    }

    @Override
    public Passageiro atualizarPassageiro(Passageiro passageiro) {
        Passageiro p = passageiroRepository.findById(passageiro.getId())
                .orElseThrow(() -> new RuntimeException("Passageiro não encontrado"));
        User user = p.getUser();
        if(user != null && passageiro.getUser() != null){
            user.setEmail(passageiro.getUser().getEmail());
            user.setPassword(passageiro.getUser().getPassword());
        }
        p.setNome(passageiro.getNome());
        p.setSobrenome(passageiro.getSobrenome());



        return passageiroRepository.save(passageiro);
    }
}

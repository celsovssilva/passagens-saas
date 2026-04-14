package com.example.transport.service;

import com.example.transport.entity.Passageiro;

import java.util.List;

public interface PassageiroService {
    public List<Passageiro> buscarPassageiros();
    public Passageiro cadastrarPassageiro(Passageiro passageiro);
    public void removerPassageiro(Passageiro passageiro);
    public Passageiro atualizarPassageiro(Passageiro passageiro);
}

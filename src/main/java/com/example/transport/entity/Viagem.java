package com.example.transport.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Viagem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime dataSaida;
    private Integer capacidade;
    private  Integer vagasDisponiveis;


    @ManyToMany
    private List<Passageiro> passageiro ;
    @ManyToOne
    private Transport transport;
    @ManyToOne
    @JoinColumn(name ="rota_id")
    private Rotas rotas;
    public void devolverVagas(int quantidade) {
        this.capacidade += quantidade;
    }
}

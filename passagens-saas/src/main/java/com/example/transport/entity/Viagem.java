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


    private String origem;
    private String destino;
    private LocalDateTime dataSaida;
    private Integer capacidade;
    private Double valorTotal;


    @ManyToMany
    private List<Passageiro> passageiro ;
    @ManyToOne
    private Transport transport;

    public void devolverVagas(int quantidade) {
        this.capacidade += quantidade;
    }
}

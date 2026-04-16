package com.example.transport.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Passageiro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    private String nome;
    private String sobrenome;
    private String cpf;
    private Integer idade;
    private Integer phone;

    @OneToOne(cascade = CascadeType.ALL)
    private Transport transports;
    @OneToMany(cascade = CascadeType.ALL)
    private Viagem viages;
    @OneToMany
    private User user;


}

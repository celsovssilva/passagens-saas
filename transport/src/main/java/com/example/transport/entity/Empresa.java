package com.example.transport.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Empresa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String email;
    private String telefone;
    private String endereco;
    private String cnpj;
    private String razaoSocial;

    @OneToMany
    private Transport transporte;
    @OneToMany
    private Compra compra;
    @OneToMany
    private User user;

}

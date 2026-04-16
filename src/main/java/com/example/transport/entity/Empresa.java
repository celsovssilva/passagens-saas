package com.example.transport.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.List;

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
    private List<Transport> transporte;
    @OneToMany
    private List<Compra> compra;
    @OneToMany
    private List<User> user;

}

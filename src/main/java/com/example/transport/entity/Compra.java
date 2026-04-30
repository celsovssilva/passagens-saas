package com.example.transport.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Compra {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User comprador;
    private double valor;
    private String status;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Passagem> passagens;

    private LocalDateTime dataCompra;
}
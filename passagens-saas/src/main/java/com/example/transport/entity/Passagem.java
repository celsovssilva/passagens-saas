package com.example.transport.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Passagem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime dataHoraDaCompra;
    private Integer quantidadeDeAssentos;
    private  Integer numeroAssentos;

    @ManyToOne
    private User user;
    @OneToOne
    private Viagem viagem;
    @ManyToOne
    private Transport transport;
    @ManyToOne
    private Compra compra;


}

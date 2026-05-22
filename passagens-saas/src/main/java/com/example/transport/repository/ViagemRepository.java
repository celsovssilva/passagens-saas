package com.example.transport.repository;

import com.example.transport.entity.Viagem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ViagemRepository extends JpaRepository<Viagem, Long> {
}

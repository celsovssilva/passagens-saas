package com.example.transport.entity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PassagemRepository extends JpaRepository<Passagem,Long> {
}

package com.example.transport.repository;

import com.example.transport.entity.Passageiro;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PassageiroRepository extends JpaRepository<Passageiro,Long> {

}

package com.example.transport.repository;

import com.example.transport.entity.Passageiro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PassageiroRepository extends JpaRepository<Passageiro,Long> {
    boolean findByCpf(String cpf);

}

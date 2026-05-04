package com.example.transport.repository;

import com.example.transport.entity.Viagem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ViagemRepository extends JpaRepository<Viagem, Long> {
    @Query("SELECT v FROM Viagem v WHERE v.origem = :origem" + " AND v.destino = :destino " +
    "AND CAST(v.dataSaida AS date) = CAST(:data AS date)")
    List<Viagem> buscarPorDataERota(
            @Param("origem") String origem,
            @Param("destino") String destino,
            @Param("data") LocalDateTime data
    );

}

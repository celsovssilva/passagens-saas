package com.example.transport.controller;

import com.example.transport.repository.EmpresaRepository;
import com.example.transport.repository.PassageiroRepository;
import com.example.transport.repository.UserRepository;
import com.example.transport.response.DashboardResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/dashboard")
public class DashboardController {
    @Autowired
    private EmpresaRepository empresaRepository;
    @Autowired
    private PassageiroRepository passageiroRepository;
    @GetMapping("/estatisticas")
    public ResponseEntity<DashboardResponse> getEstatisticas() {


        long passageirosCount = passageiroRepository.count(); // Conta linhas da tabela de passageiros
        long empresasCount = empresaRepository.count();       // Conta linhas da tabela de empresas

        double faturamento = 0.0;


        DashboardResponse response = new DashboardResponse(
                passageirosCount,
                empresasCount,
                faturamento
        );

        return ResponseEntity.ok(response);
    }
}

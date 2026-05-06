package com.example.transport.controller;

import com.example.transport.repository.CompraRepository;
import com.example.transport.request.CompraRequest;
import com.example.transport.response.CompraResponse;
import com.example.transport.service.CompraService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/compra")
public class CompraController {
    @Autowired
    private CompraService compraService;

    @PostMapping("/comprar")
    public ResponseEntity<CompraResponse> comprar(@Valid @RequestBody CompraRequest compraRequest) {
        CompraResponse response = compraService.comprar(compraRequest);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/atualizar/{idCompra}")
    public ResponseEntity<CompraResponse> atualizar(@PathVariable Long idCompra) {
        compraService.confirmarPagamento(idCompra);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/cancelar/{id}")
    public ResponseEntity<Void> cancelar(@PathVariable Long idCompra) {
        compraService.excluir(idCompra);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/historico/{userId}")
    public ResponseEntity<List<CompraResponse>> historico(@PathVariable Long userId) {
        List<CompraResponse> compras = compraService.historico(userId);
        return ResponseEntity.ok(compras);
    }
}
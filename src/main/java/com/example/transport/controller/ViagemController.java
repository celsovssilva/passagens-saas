package com.example.transport.controller;

import com.example.transport.entity.Viagem;
import com.example.transport.request.ViagemRequest;
import com.example.transport.response.ViagemResponse;
import com.example.transport.service.ViagemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("api/viagem")
public class ViagemController {
    @Autowired
    ViagemService viagemService;

    @PostMapping("/agendar")
    public ResponseEntity<ViagemResponse> agendarViagem(@RequestBody ViagemRequest viagem){
        return ResponseEntity.ok(viagemService.agendarViagem(viagem));
    }

    @GetMapping("/buscar/{id}")
    public Optional<Viagem> buscarViagem(@PathVariable Long id){
        return viagemService.buscarViagemPorId(id);
    }

    @DeleteMapping("/deletar/{idViagem}")
    public void deletarViagem(@PathVariable Long idViagem){
        viagemService.deleteViagem(idViagem);
    }
}

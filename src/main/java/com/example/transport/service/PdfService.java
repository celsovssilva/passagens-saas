package com.example.transport.service;

public interface PdfService {
    byte[] gerarPdfPassagem( String nomePassageiro, String documento, String origem,String Destino);
}

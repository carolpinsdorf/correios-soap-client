package com.example.correiossoapclient.controllers;


import com.example.correiossoapclient.services.CorreiosService;
import com.example.correiossoapclient.EnderecoERP;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CorreiosController {
    private final CorreiosService correiosService;

    public CorreiosController(CorreiosService correiosService) {
        this.correiosService = correiosService;
    }

    @GetMapping("/endereco")
    public EnderecoERP getEndereco() {
        return correiosService.getEndereco();
    }
}

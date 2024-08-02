package com.ufs.projeto.test.projeto_teste_software.controllers;

import com.ufs.projeto.test.projeto_teste_software.models.Pessoa;
import com.ufs.projeto.test.projeto_teste_software.services.PessoaService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static java.lang.String.format;

@RestController
@RequestMapping(value = "/testes-junit5", produces = {"application/json"})
@Slf4j
public class PessoaController {

    private final PessoaService service;

    public PessoaController(PessoaService service) {
        this.service = service;
    }

    @GetMapping("/cpf")
    @CrossOrigin(allowedHeaders = "*")
    public ResponseEntity<List<Pessoa>> buscaDadosProfissionais(@RequestParam("cpf") String cpf) {
        log.info(format("Buscando dados de pessoa por cpf = %s!", cpf));

        return ResponseEntity.ok(service.buscaPessoasPorCpf(cpf));

    }
}



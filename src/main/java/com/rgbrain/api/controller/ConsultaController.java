package com.rgbrain.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rgbrain.api.domain.consulta.AgendaDeConsultas;
import com.rgbrain.api.domain.consulta.DadosAgendamentoConsulta;
import com.rgbrain.api.domain.consulta.DadosDetalhamentoConsulta;

import jakarta.validation.Valid;

@RestController
@RequestMapping("consultas")
public class ConsultaController {

    @Autowired
    private AgendaDeConsultas agendaDeConsultas;
    
    @PostMapping("")
    @Transactional
    public ResponseEntity<DadosDetalhamentoConsulta> agendar(@RequestBody @Valid DadosAgendamentoConsulta dados) {
        agendaDeConsultas.agendar(dados);
        return ResponseEntity.ok(new DadosDetalhamentoConsulta(null, null, null, null));
    }
    
}

package com.rgbrain.api.domain.consulta.validacoes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.rgbrain.api.domain.consulta.DadosAgendamentoConsulta;
import com.rgbrain.api.domain.consulta.ValidacaoException;
import com.rgbrain.api.domain.paciente.PacienteRepository;

@Component
public class ValidadorPacienteAtivo implements ValidadorAgendamentoConsultas{
    
    @Autowired
    private PacienteRepository repository;

    @Override
    public void validar(DadosAgendamentoConsulta dados) {
        var isAtivo = repository.findAtivoById(dados.idPaciente());       
        if (!isAtivo) {
            throw new ValidacaoException("Consulta nao pode ser marcada com paciente inativo");
        }
    }
}

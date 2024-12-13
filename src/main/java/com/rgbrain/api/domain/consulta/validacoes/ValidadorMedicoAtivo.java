package com.rgbrain.api.domain.consulta.validacoes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.rgbrain.api.domain.consulta.DadosAgendamentoConsulta;
import com.rgbrain.api.domain.consulta.ValidacaoException;
import com.rgbrain.api.domain.medico.MedicoRepository;

@Component
public class ValidadorMedicoAtivo implements ValidadorAgendamentoConsultas{
    
    @Autowired
    private static MedicoRepository repository;

    @Override
    public void validar(DadosAgendamentoConsulta dados) {
        var idMedico = dados.idMedico();
        if (idMedico == null) {
            return;
        }
        
        var medico = repository.getReferenceById(idMedico);
        if (!medico.getAtivo()) {
            throw new ValidacaoException("Consulta nao pode ser marcada com medico inativo");
        }
    }
}

package com.rgbrain.api.domain.consulta.validacoes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.rgbrain.api.domain.consulta.DadosAgendamentoConsulta;
import com.rgbrain.api.domain.consulta.ValidacaoException;
import com.rgbrain.api.domain.medico.MedicoRepository;

@Component
public class ValidadorMedicoAtivo implements ValidadorAgendamentoConsultas{
    
    @Autowired
    private MedicoRepository repository;

    @Override
    public void validar(DadosAgendamentoConsulta dados) {
        if (dados.idMedico() == null) {
            return;
        }

        var medicoEstaAtivo = repository.findAtivoById(dados.idMedico());
        if (!medicoEstaAtivo) {
            throw new ValidacaoException("Consulta não pode ser agendada com médico excluído");
        }
    }
}

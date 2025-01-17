package com.rgbrain.api.domain.consulta.validacoes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.rgbrain.api.domain.consulta.ConsultaRepository;
import com.rgbrain.api.domain.consulta.DadosAgendamentoConsulta;
import com.rgbrain.api.domain.consulta.ValidacaoException;

@Component
public class ValidadorMedicoComOutraConsultaNoMesmoHorario implements ValidadorAgendamentoConsultas{
    
    @Autowired
    private ConsultaRepository repository;
    
    @Override
    public void validar(DadosAgendamentoConsulta dados) {
        var consultasNoMesmoHorario = repository.existsByMedicoIdAndData(dados.idMedico(), dados.data()); 

        if (consultasNoMesmoHorario) {
            throw new ValidacaoException("Medico com outra consulta no mesmo horário");
        }       
    }
}

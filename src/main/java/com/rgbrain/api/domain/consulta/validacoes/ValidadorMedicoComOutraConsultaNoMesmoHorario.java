package com.rgbrain.api.domain.consulta.validacoes;

import java.time.LocalDateTime;

import com.rgbrain.api.domain.consulta.ConsultaRepository;
import com.rgbrain.api.domain.consulta.ValidacaoException;

public class ValidadorMedicoComOutraConsultaNoMesmoHorario {
    
    private ConsultaRepository repository;
    
    public void validar(Long idMedico, LocalDateTime dataConsulta) {
        var consultasNoMesmoHorario = repository.existsByMedicoIdAndData(idMedico, dataConsulta); 

        if (consultasNoMesmoHorario) {
            throw new ValidacaoException("Medico com outra consulta no mesmo horário");
        }       
    }
}

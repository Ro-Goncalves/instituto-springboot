package com.rgbrain.api.domain.consulta.validacoes;

import java.time.Duration;
import java.time.LocalDateTime;

import org.springframework.stereotype.Component;

import com.rgbrain.api.domain.consulta.DadosAgendamentoConsulta;
import com.rgbrain.api.domain.consulta.ValidacaoException;

@Component
public class ValidadorHorarioAntecedencia implements ValidadorAgendamentoConsultas{
    
    @Override
    public void validar(DadosAgendamentoConsulta dados) {
        var agora = LocalDateTime.now();
        var diferencaMinutos = Duration.between(agora, dados.data());

        if (diferencaMinutos.toMinutes() < 30) {
            throw new ValidacaoException("Consulta deve ser agendada com antecedência de 30 minutos");
        }
    }
}

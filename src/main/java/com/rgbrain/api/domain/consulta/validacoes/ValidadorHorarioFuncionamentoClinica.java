package com.rgbrain.api.domain.consulta.validacoes;

import java.time.DayOfWeek;

import org.springframework.stereotype.Component;

import com.rgbrain.api.domain.consulta.DadosAgendamentoConsulta;
import com.rgbrain.api.domain.consulta.ValidacaoException;

@Component
public class ValidadorHorarioFuncionamentoClinica implements ValidadorAgendamentoConsultas{
    
    @Override
    public void validar(DadosAgendamentoConsulta dados) {
        var dataConsulta = dados.data();
        var isDomingo = dataConsulta.getDayOfWeek().equals(DayOfWeek.SATURDAY);
        var isAntesAbertura = dataConsulta.getHour() < 7;
        var isDepoisEncerramento = dataConsulta.getHour() > 18;

        if (isDomingo || isAntesAbertura || isDepoisEncerramento) {
            throw new ValidacaoException("Consulta fora do horário de funcionamento da clínica");
        }
        
    }
}

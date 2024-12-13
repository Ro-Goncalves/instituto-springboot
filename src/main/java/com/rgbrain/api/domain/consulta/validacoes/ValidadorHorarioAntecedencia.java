package com.rgbrain.api.domain.consulta.validacoes;

import java.time.Duration;
import java.time.LocalDateTime;

import com.rgbrain.api.domain.consulta.ValidacaoException;

public class ValidadorHorarioAntecedencia {
    
    public static void validar(LocalDateTime dataConsulta) {
        var agora = LocalDateTime.now();
        var diferencaMinutos = Duration.between(agora, dataConsulta);

        if (diferencaMinutos.toMinutes() < 30) {
            throw new ValidacaoException("Consulta deve ser agendada com antecedência de 30 minutos");
        }
    }
}

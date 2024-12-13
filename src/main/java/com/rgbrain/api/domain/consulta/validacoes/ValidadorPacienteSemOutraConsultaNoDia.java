package com.rgbrain.api.domain.consulta.validacoes;

import java.time.LocalDateTime;

import com.rgbrain.api.domain.consulta.ConsultaRepository;
import com.rgbrain.api.domain.consulta.ValidacaoException;

public class ValidadorPacienteSemOutraConsultaNoDia {
    
    private ConsultaRepository repository;

    public void validar(Long idPaciente, LocalDateTime dataConsulta) {
        var primeiroHorario = dataConsulta.withHour(7);
        var ultimoHorario = dataConsulta.withHour(18);

        var consultasNoDia = repository.existsByPacienteIdAndDataBetween(idPaciente, primeiroHorario, ultimoHorario);
        if (consultasNoDia) {
            throw new ValidacaoException("Paciente com outra consulta no mesmo dia");
        }
    }
}

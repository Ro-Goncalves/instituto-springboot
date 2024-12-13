package com.rgbrain.api.domain.consulta.validacoes;

import com.rgbrain.api.domain.consulta.ValidacaoException;
import com.rgbrain.api.domain.paciente.PacienteRepository;

public class ValidadorPacienteAtivo {
    
    private PacienteRepository repository;

    public void validar(Long idPaciente) {        
        var isAtivo = repository.findAtivoById(idPaciente);       
        if (!isAtivo) {
            throw new ValidacaoException("Consulta nao pode ser marcada com paciente inativo");
        }
    }
}

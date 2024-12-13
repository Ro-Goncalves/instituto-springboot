package com.rgbrain.api.domain.consulta.validacoes;

import org.springframework.beans.factory.annotation.Autowired;

import com.rgbrain.api.domain.consulta.ValidacaoException;
import com.rgbrain.api.domain.medico.MedicoRepository;

public class ValidadorMedicoAtivo {
    
    @Autowired
    private static MedicoRepository repository;

    public static void validar(Long idMedico) {
        if (idMedico == null) {
            return;
        }
        
        var medico = repository.getReferenceById(idMedico);
        if (!medico.getAtivo()) {
            throw new ValidacaoException("Consulta nao pode ser marcada com medico inativo");
        }
    }
}

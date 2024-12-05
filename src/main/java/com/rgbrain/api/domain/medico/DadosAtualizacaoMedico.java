package com.rgbrain.api.domain.medico;

import com.rgbrain.api.domain.endereco.DadosEndereco;

import jakarta.validation.constraints.NotNull;

public record DadosAtualizacaoMedico(
    
    @NotNull(message = "{id.obrigatorio}")
    Long id,

    String nome,
    String telefone,
    DadosEndereco endereco
) {}

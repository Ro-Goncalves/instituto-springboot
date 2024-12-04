package com.rgbrain.api.medico;

import com.rgbrain.api.endereco.DadosEndereco;

import jakarta.validation.constraints.NotNull;

public record DadosAtualizacaoMedico(
    
    @NotNull(message = "O id do medico é obrigatório")
    Long id,

    String nome,
    String telefone,
    DadosEndereco endereco
) {}

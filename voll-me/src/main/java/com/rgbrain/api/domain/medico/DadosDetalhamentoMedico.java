package com.rgbrain.api.domain.medico;

import com.rgbrain.api.domain.endereco.Endereco;

public record DadosDetalhamentoMedico(
    Long id,
    String nome,
    String email,
    String telefone,
    String crm,
    Especialidade especialidade,
    Endereco endereco
) {

    public DadosDetalhamentoMedico(Medico medico){
        this(
            medico.getId(),
            medico.getNome(),
            medico.getEmail(),
            medico.getTelefone(),
            medico.getCrm(),
            medico.getEspecialidade(),
            medico.getEndereco()
        );
    }

}

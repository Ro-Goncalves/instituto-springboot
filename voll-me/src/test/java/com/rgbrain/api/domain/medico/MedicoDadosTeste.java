package com.rgbrain.api.domain.medico;

import com.rgbrain.api.domain.endereco.DadosEndereco;
import com.rgbrain.api.domain.endereco.Endereco;

public class MedicoDadosTeste {    

    public static Medico criarMedico() {
        return Medico.builder()
            .id(1L)
            .nome("Médico Teste 01")
            .email("emailmedico01@rgbrain.io")
            .telefone("111111111")
            .crm("000001")
            .especialidade(Especialidade.CARDIOLOGIA)
            .ativo(Boolean.TRUE)
            .endereco(criarEndereco())
            .build();
    }

    public static DadosCadastroMedico criarDadosCadastroMedico() {
        return new DadosCadastroMedico(
            "Médico Teste 01", 
            "E-Mail Médico 01", 
            "1111111111", 
            "000001", 
            Especialidade.CARDIOLOGIA, 
            criarDadosEndereco()
        );
    }

    public static DadosDetalhamentoMedico criarDadosDetalhamentoMedico() {
        return new DadosDetalhamentoMedico(
            1L,
            "Médico Teste 01", 
            "E-Mail Médico 01", 
            "1111111111", 
            "000001", 
            Especialidade.CARDIOLOGIA, 
            criarEndereco()
        );
    }

    private static DadosEndereco criarDadosEndereco() {
        return new DadosEndereco(
            "Médico Logradouro 01", 
            "Médico Bairro 01", 
            "12345678", 
            "UF", 
            "Médico Cidade 01", 
            "Médico Complemento 01", 
            "001");
    }

    private static Endereco criarEndereco() {
        return Endereco.builder()
            .logradouro("Médico Logradouro 01")
            .bairro("Médico Bairro 01")
            .cep("12345678")
            .uf("UF")
            .cidade("Médico Cidade 01")
            .complemento("Médico Complemento 01")
            .numero("001")
            .build();
    }
}

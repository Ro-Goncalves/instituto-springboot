package com.rgbrain.api.domain.endereco;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Endereco {
  
    private String logradouro;
    private String bairro;
    private String cep;
    private String cidade;
    private String uf;
    private String complemento;
    private String numero;
    
    public void atualizarInformacoes(DadosEndereco endereco) {

        // Campos opcionais em DadosEndereco
        if (endereco.complemento() != null) {
            this.complemento = endereco.complemento();
        }

        if (endereco.numero() != null) {
            this.numero = endereco.numero();
        }

        // Campos obrigatórios em DadosEndereco
        this.logradouro = endereco.logradouro();
        this.bairro = endereco.bairro();
        this.cep = endereco.cep();
        this.cidade = endereco.cidade();
        this.uf = endereco.uf();
    }

}

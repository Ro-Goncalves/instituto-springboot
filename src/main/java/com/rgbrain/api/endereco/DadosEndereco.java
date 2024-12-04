package com.rgbrain.api.endereco;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record DadosEndereco(
    @NotBlank(message = "O logradouro do endereço e obrigatório")
    String logradouro,

    @NotBlank(message = "O bairro do endereço e obrigatório")
    String bairro,

    @NotBlank(message = "O CEP do endereço e obrigatório")
    @Pattern(regexp = "\\d{8}", message = "O CEP do endereço deve conter 8 dígitos")
    String cep,

    @NotBlank(message = "A cidade do endereço é obrigatória")
    String cidade,

    @NotBlank(message = "A UF do endereço é obrigatória")
    String uf,

    String complemento,
    String numero
) {}

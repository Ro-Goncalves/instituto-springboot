package com.rgbrain.api.medico;

import com.rgbrain.api.endereco.DadosEndereco;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record DadosCadastroMedico(
    @NotBlank(message = "O nome do médico é obrigatório")
    String nome,

    @NotBlank(message = "O email do médico é obrigatório")
    @Email(message = "O email do médico é inválido")
    String email,

    @NotBlank(message = "O telefone do médico é obrigatório")
    String telefone,

    @NotBlank(message = "O crm do médico é obrigatório")
    @Pattern(regexp = "\\d{4,6}", message = "O crm do médico deve conter 6 dígitos")
    String crm,

    @NotNull
    Especialidade especialidade,

    @NotNull
    @Valid
    DadosEndereco endereco
    
) {}

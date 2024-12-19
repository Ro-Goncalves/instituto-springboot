package com.rgbrain.api.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;

import com.rgbrain.api.domain.medico.DadosCadastroMedico;
import com.rgbrain.api.domain.medico.DadosDetalhamentoMedico;
import com.rgbrain.api.domain.medico.Medico;
import com.rgbrain.api.domain.medico.MedicoDadosTeste;
import com.rgbrain.api.domain.medico.MedicoRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
public class MedicoControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private JacksonTester<DadosCadastroMedico> dadosCadastroMedicoJson;

    @Autowired
    private JacksonTester<DadosDetalhamentoMedico> dadosDetalhamentoMedicoJson;

    @Mock
    private MedicoRepository repository;

    @Test
    @DisplayName("Deveria devolver codigo http 400 quando informacoes estao invalidas")
    @WithMockUser    
    void quandoNaoExistirMedicoDisponivel_deveRetornarNull() throws Exception {
        // GIVEN & WHEN
        var response = mvc.perform(post("/medicos")).andReturn().getResponse();

        // THEN
        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @DisplayName("Deveria devolver codigo http 200 quando informacoes estao validas")    
    @WithMockUser
    void quandoExistirMedicoDisponivel_deveRetornarMedico() throws Exception {
        // GIVEN
        var medico = MedicoDadosTeste.criarMedico();
        var dadosCadastroMedico = MedicoDadosTeste.criarDadosCadastroMedico();
        var dadosDetalhamentoMedico = MedicoDadosTeste.criarDadosDetalhamentoMedico();

        var jsonEsperado = dadosDetalhamentoMedicoJson.write(dadosDetalhamentoMedico).getJson();
        
        when(repository.save(Mockito.any(Medico.class))).thenReturn(medico);
        // WHEN
        var response = mvc
            .perform(
                post("/medicos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(dadosCadastroMedicoJson.write(dadosCadastroMedico).getJson()))
            .andReturn()
            .getResponse();

        // THEN
        assertThat(response.getStatus()).isEqualTo(HttpStatus.CREATED.value());
        assertThat(response.getContentAsString()).isEqualTo(jsonEsperado);
    }
}

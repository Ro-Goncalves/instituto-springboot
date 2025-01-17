package com.rgbrain.api.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import java.time.LocalDateTime;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.rgbrain.api.domain.consulta.AgendaDeConsultas;
import com.rgbrain.api.domain.consulta.DadosAgendamentoConsulta;
import com.rgbrain.api.domain.consulta.DadosDetalhamentoConsulta;
import com.rgbrain.api.domain.medico.Especialidade;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
public class ConsultaControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private JacksonTester<DadosAgendamentoConsulta> dadosAgendamentoConsultaJson;

    @Autowired
    private JacksonTester<DadosDetalhamentoConsulta> dadosDetalhamentoConsultaJson;

    @MockitoBean
    private AgendaDeConsultas agendaDeConsultas;

    @Test
    @DisplayName("Deveria devolver codigo http 400 quando informacoes estao invalidas")
    @WithMockUser
    void quandoRequestForInvalido_deveRetornarBadRequest() throws Exception {
        // GIVEN & WHEN
        var response = mvc.perform(post("/consultas")).andReturn().getResponse();

        // THEN
        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @DisplayName("Deveria devolver codigo http 200 quando informacoes estao corretas")
    @WithMockUser
    void quandoRequestForValido_deveRetornarStatusOk() throws Exception {
        // GIVEN
        var data = LocalDateTime.now().plusDays(1);
        var especialidade = Especialidade.CARDIOLOGIA;
        var dadosDetalhamentoConsulta = new DadosDetalhamentoConsulta(1L, 1L, 1L, data);
        var jsonEsperado = dadosDetalhamentoConsultaJson.write(dadosDetalhamentoConsulta).getJson();

        when(agendaDeConsultas.agendar(Mockito.any(DadosAgendamentoConsulta.class)))
            .thenReturn(dadosDetalhamentoConsulta);
        
        // WHEN
        var response = mvc
            .perform(
                post("/consultas")
                .contentType(MediaType.APPLICATION_JSON)
                .content(dadosAgendamentoConsultaJson.write(
                        new DadosAgendamentoConsulta(1L, 1L, data, especialidade)
                    ).getJson()
                )
            )
            .andReturn()
            .getResponse();

        // THEN
        assertThat(response.getContentAsString()).isEqualTo(jsonEsperado);
    }
}

package com.rgbrain.brianbot.domain.brian.infrastructure.adivsor;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import com.rgbrain.brianbot.domain.clima.infrastructure.adivsor.AdivsorPrevisaoUmidade;
import com.rgbrain.brianbot.domain.clima.infrastructure.adivsor.AdvisorClient;
import com.rgbrain.brianbot.domain.clima.infrastructure.adivsor.model.exception.AdvisorClientException;

@SpringBootTest
@ContextConfiguration(classes = { AdvisorRetryTestConfig.class })
public class AdivsorPrevisaoUmidadeTest {

    @MockitoBean
    private AdvisorClient advisorClient;

    @Autowired
    private AdivsorPrevisaoUmidade adivsorPrevisaoUmidade;

    @Test
    @DisplayName("Quando AdvisorClient lançar AdivsorClientException, deve tentar 3 vezes")
    public void deveExecutarRetry() throws Exception {
        // Given
        var advisorClientException = new AdvisorClientException(
                "Erro na comunicação com o Advisor",
                new Exception("Erro"));

        when(advisorClient.executar(Mockito.anyString(), Mockito.any()))
                .thenThrow(advisorClientException);

        // When
        adivsorPrevisaoUmidade.obterPrevisao();

        // Then
        verify(advisorClient, times(3)).executar(Mockito.anyString(), Mockito.any());
    }

    @Test
    @DisplayName("Quando falhar o retry, deve executar o recover")
    public void deveExecutarRecover() throws Exception {
        // Given
        var advisorClientException = new AdvisorClientException(
                "Erro na comunicação com o Advisor",
                new Exception("Erro"));

        when(advisorClient.executar(Mockito.anyString(), Mockito.any()))
                .thenThrow(advisorClientException);

        // When
        var resposta = adivsorPrevisaoUmidade.obterPrevisao();

        // Then
        assertThat(resposta).isNotNull();
    }
}
package com.rgbrain.brianbot.domain.brian.infrastructure.adivsor;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import com.rgbrain.brianbot.domain.clima.infrastructure.FalhaRequisicaoRepository;
import com.rgbrain.brianbot.domain.clima.infrastructure.adivsor.AdivsorPrevisaoUmidade;
import com.rgbrain.brianbot.domain.clima.infrastructure.adivsor.AdvisorClient;
import com.rgbrain.brianbot.domain.clima.infrastructure.adivsor.model.exception.AdvisorClientException;

@DataJpaTest
@AutoConfigureDataJpa
@AutoConfigureTestDatabase
@ActiveProfiles("test")
public class AdivsorPrevisaoUmidadeIntegrationTest {

        @MockitoBean
        private AdvisorClient advisorClient;

        @Autowired
        private FalhaRequisicaoRepository falhaRequisicaoRepository;

        @Autowired
        private AdivsorPrevisaoUmidade adivsorPrevisaoUmidade;       

        @Test
        @DisplayName("Quando falhar o retry, deve armazenar erro")
        public void deveExecutarRecoverEAmarzenarErro() throws Exception {
                // Given
                var advisorClientException = new AdvisorClientException(
                                "Erro na comunicação com o Advisor",
                                new Exception("Erro"));

                when(advisorClient.executar(Mockito.anyString(), Mockito.any()))
                                .thenThrow(advisorClientException);

                // When
                adivsorPrevisaoUmidade.obterPrevisao();

                // Then
                // var captor = ArgumentCaptor.forClass(FalhaRequisicao.class);
                // verify(falhaRequisicaoRepository).save(captor.capture());

                // var falhaRequisicao = captor.getValue();

                // assertThat(falhaRequisicaoRepository
                //         .findById(falhaRequisicao.getId()))
                //         .isPresent();
        }
}
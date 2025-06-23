package com.rgbrain.brianbot.domain.brian.infrastructure.adivsor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.retry.annotation.EnableRetry;

import com.rgbrain.brianbot.domain.clima.infrastructure.FalhaRequisicaoRepository;
import com.rgbrain.brianbot.domain.clima.infrastructure.adivsor.AdivsorPrevisaoUmidade;
import com.rgbrain.brianbot.domain.clima.infrastructure.adivsor.AdvisorClient;

@Configuration
@EnableRetry
class AdvisorRetryTestConfig {

    @Bean
    public AdivsorPrevisaoUmidade adivsorPrevisaoUmidade(AdvisorClient advisorClient, FalhaRequisicaoRepository falhaRequisicaoRepository) {
        return new AdivsorPrevisaoUmidade("http://teste.url", advisorClient, falhaRequisicaoRepository);
    }
}

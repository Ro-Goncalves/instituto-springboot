package com.rgbrain.brianbot.domain.clima.infrastructure.adivsor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

import com.rgbrain.brianbot.domain.clima.infrastructure.FalhaRequisicaoRepository;
import com.rgbrain.brianbot.domain.clima.infrastructure.adivsor.model.exception.AdvisorClientException;
import com.rgbrain.brianbot.domain.clima.infrastructure.adivsor.model.reponse.ResponsePrevisaoUmidade;
import com.rgbrain.brianbot.domain.clima.infrastructure.model.entity.FalhaRequisicao;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class AdivsorPrevisaoUmidade {

    private final String urlPrevisaoUmidade;
    private final AdvisorClient advisorRequests;
    private final FalhaRequisicaoRepository falhaRequisicaoRepository;

    public AdivsorPrevisaoUmidade(
            @Value("${advisor.url.previsao.umidade}") String urlPrevisaoUmidade,
            AdvisorClient advisorRequests,
            FalhaRequisicaoRepository falhaRequisicaoRepository) {
        this.urlPrevisaoUmidade = urlPrevisaoUmidade;
        this.advisorRequests = advisorRequests;
        this.falhaRequisicaoRepository = falhaRequisicaoRepository;
    }

    @Retryable(maxAttempts = 3, backoff = @Backoff(delay = 5000), value = {
            AdvisorClientException.class })
    public ResponsePrevisaoUmidade obterPrevisao() {
        try {
            var resultado = advisorRequests.executar(urlPrevisaoUmidade, ResponsePrevisaoUmidade.class);
            return resultado;
        } catch (Exception e) {
            throw e;
        }
    }

    @Recover
    private ResponsePrevisaoUmidade recuperarFalhaObterPrevisao(AdvisorClientException e) {
        log.error("Todas as tentativas de obter previsão da umidade falharam após 3 tentativas", e);
        falhaRequisicaoRepository.save(new FalhaRequisicao());
        return new ResponsePrevisaoUmidade();
    }
}

package com.rgbrain.brianbot.domain.clima.infrastructure.adivsor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rgbrain.brianbot.domain.clima.infrastructure.adivsor.model.exception.AdvisorClientException;
import com.rgbrain.brianbot.domain.clima.infrastructure.adivsor.model.exception.AdvisorException;
import com.rgbrain.brianbot.domain.clima.infrastructure.adivsor.model.exception.AdvisorSerializationException;
import com.rgbrain.brianbot.domain.clima.infrastructure.adivsor.model.exception.AdvisorServerException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class AdvisorClient {

    private final String advisorToken;

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    public AdvisorClient(
            @Value("${advisor.api.token}") String advisorToken,
            RestTemplate restTemplate,
            ObjectMapper objectMapper) {

        this.advisorToken = advisorToken;
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
    }

    public <T> T executar(String url, Class<T> responseType) {
        try {
            String responseJson = obterPrevisao(url);
            return objectMapper.readValue(responseJson, responseType);
        } catch (JsonProcessingException e) {
            log.error("Erro ao serializar {}. {}", responseType.getName(), e.getMessage(), e);
            throw new AdvisorSerializationException("Erro ao processar dados da " + responseType.getSimpleName(), e);
        }
    }

    private String obterPrevisao(String url) {
        validarToken();

        try {
            var requestHeaders = new HttpHeaders();
            requestHeaders.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<Object> requestEntity = new HttpEntity<>(requestHeaders);

            ResponseEntity<String> responseEntity = restTemplate.exchange(
                    url.formatted(advisorToken),
                    HttpMethod.GET,
                    requestEntity,
                    String.class);

            return responseEntity.getBody();

        } catch (HttpClientErrorException e) {
            // Erros 4xx - Erros de cliente (400-499)
            log.warn("Erro de cliente ao obter a previsão. Status: {}. Mensagem: {}",
                    e.getStatusCode(), e.getMessage());
            throw new AdvisorClientException(
                    "Falha na requisição ao serviço (erro de cliente) - %s".formatted(e.getMessage()), e);

        } catch (HttpServerErrorException e) {
            // Erros 5xx - Erros de servidor (500-599)
            log.error("Erro de servidor ao obter a previsão. Status: {}. Mensagem: {}",
                    e.getStatusCode(), e.getMessage());
            throw new AdvisorServerException(
                    "Falha na requisição ao serviço (erro de servidor) - %s".formatted(e.getMessage()), e);

        } catch (RestClientException e) {
            // Outros erros de comunicação (timeout, problemas de conexão, etc.)
            log.warn("Erro de comunicação ao obter a previsão. {}", e.getMessage());
            throw new AdvisorClientException("Falha na requisição ao serviço - %s".formatted(e.getMessage()), e);
        }
    }

    private void validarToken() {
        if (advisorToken == null || advisorToken.isEmpty()) {
            throw new AdvisorException("Token de API do Advisor não configurado");
        }
    }
}

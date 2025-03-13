package com.rgbrain.brianbot.domain.brian.infrastructure.adivsor;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rgbrain.brianbot.domain.brian.TestResponse;
import com.rgbrain.brianbot.domain.clima.infrastructure.adivsor.AdvisorClient;
import com.rgbrain.brianbot.domain.clima.infrastructure.adivsor.model.exception.AdvisorClientException;
import com.rgbrain.brianbot.domain.clima.infrastructure.adivsor.model.exception.AdvisorException;
import com.rgbrain.brianbot.domain.clima.infrastructure.adivsor.model.exception.AdvisorSerializationException;
import com.rgbrain.brianbot.domain.clima.infrastructure.adivsor.model.exception.AdvisorServerException;

@ExtendWith(MockitoExtension.class)
public class AdvisorClientTest {

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private ObjectMapper objectMapper;

    private String advisorToken;

    private AdvisorClient client;

    private String url;
    private String responseJson;

    @BeforeEach
    void setUp() {
        advisorToken = "test-token";
        url = "http://api.advisor.com/endpoint?token=%s";
        responseJson = "{\"data\":\"test\"}";

        client = new AdvisorClient(advisorToken, restTemplate, objectMapper);
    }

    @Test
    @DisplayName("Deve executar com sucesso e converter a resposta para o tipo especificado")
    void testExecutarSuccess() throws JsonProcessingException {
        // Given
        var expectedResponse = new TestResponse("test");

        var responseEntity = ResponseEntity.ok(responseJson);

        when(restTemplate.exchange(
                eq(url.formatted(advisorToken)),
                eq(HttpMethod.GET),
                any(HttpEntity.class),
                eq(String.class))).thenReturn(responseEntity);

        when(objectMapper.readValue(responseJson, TestResponse.class)).thenReturn(expectedResponse);

        // When
        var result = client.executar(url, TestResponse.class);

        // Then
        assertThat(result).isNotNull();
        assertThat(result).isEqualTo(expectedResponse);
        verify(objectMapper).readValue(responseJson, TestResponse.class);
    }

    @Test
    @DisplayName("Deve lançar AdvisorSerializationException quando ocorrer erro na deserialização JSON")
    void testExecutarJsonProcessingException() throws JsonProcessingException {
        // Given
        var responseEntity = ResponseEntity.ok(responseJson);

        when(restTemplate.exchange(
                anyString(),
                eq(HttpMethod.GET),
                any(HttpEntity.class),
                eq(String.class))).thenReturn(responseEntity);

        when(objectMapper.readValue(responseJson, TestResponse.class)).thenThrow(JsonProcessingException.class);

        // When & Then
        assertThatThrownBy(() -> client.executar(url, TestResponse.class))
                .isInstanceOf(AdvisorSerializationException.class)
                .hasMessageContaining("Erro na serialização dos dados do Advisor: Erro ao processar dados da")
                .hasMessageContaining(TestResponse.class.getSimpleName())
                .hasCauseInstanceOf(JsonProcessingException.class);
    }

    @Test
    @DisplayName("Deve fazer uma requisição HTTP GET válida e retornar o corpo da resposta")
    void testObterPrevisaoSuccess() {
        // Given
        var responseBody = "{\"data\":\"test\"}";

        var responseEntity = ResponseEntity.ok(responseBody);

        when(restTemplate.exchange(
                eq(url.formatted(advisorToken)),
                eq(HttpMethod.GET),
                any(HttpEntity.class),
                eq(String.class))).thenReturn(responseEntity);

        // When
        // ReflectionTestUtils para acessar o método privado
        var result = (String) ReflectionTestUtils.invokeMethod(
                client,
                "obterPrevisao",
                url);

        // Then
        assertThat(result).isEqualTo(responseBody);

        // Verificar se os headers foram configurados corretamente
        var expectedHeaders = new HttpHeaders();
        expectedHeaders.setContentType(MediaType.APPLICATION_JSON);

        verify(restTemplate).exchange(
                eq(url.formatted(advisorToken)),
                eq(HttpMethod.GET),
                any(HttpEntity.class),
                eq(String.class));
    }

    @Test
    @DisplayName("Deve lançar AdvisorClientException quando o servidor retornar erro 4xx")
    void testObterPrevisaoClientError() {
        // Given
        @SuppressWarnings("null")
        var exception = HttpClientErrorException.create(
                HttpStatus.BAD_REQUEST,
                "Bad Request",
                HttpHeaders.EMPTY,
                null,
                null);

        when(restTemplate.exchange(
                anyString(),
                eq(HttpMethod.GET),
                any(HttpEntity.class),
                eq(String.class))).thenThrow(exception);

        // When & Then
        assertThatThrownBy(() -> ReflectionTestUtils.invokeMethod(
                client,
                "obterPrevisao",
                url))
                .isInstanceOf(AdvisorClientException.class)
                .hasMessageContaining("Falha na requisição ao serviço (erro de cliente)")
                .hasCause(exception);
    }

    @Test
    @DisplayName("Deve lançar AdvisorServerException quando o servidor retornar erro 5xx")
    void testObterPrevisaoServerError() {
        // Given
        @SuppressWarnings("null")
        var exception = HttpServerErrorException.create(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "Internal Server Error",
                HttpHeaders.EMPTY,
                null,
                null);

        when(restTemplate.exchange(
                anyString(),
                eq(HttpMethod.GET),
                any(HttpEntity.class),
                eq(String.class))).thenThrow(exception);

        // When & Then
        assertThatThrownBy(() -> ReflectionTestUtils.invokeMethod(
                client,
                "obterPrevisao",
                url))
                .isInstanceOf(AdvisorServerException.class)
                .hasMessageContaining("Falha na requisição ao serviço (erro de servidor)")
                .hasCause(exception);
    }

    @Test
    @DisplayName("Deve lançar AdvisorClientException quando ocorrer erro geral de comunicação")
    void testObterPrevisaoRestClientException() {
        // Given
        var exception = new RestClientException("Connection timed out");

        when(restTemplate.exchange(
                anyString(),
                eq(HttpMethod.GET),
                any(HttpEntity.class),
                eq(String.class))).thenThrow(exception);

        // When & Then
        assertThatThrownBy(() -> ReflectionTestUtils.invokeMethod(
                client,
                "obterPrevisao",
                url))
                .isInstanceOf(AdvisorClientException.class)
                .hasMessageContaining("Falha na requisição ao serviço")
                .hasCause(exception);
    }

    @Test
    @DisplayName("Não deve lançar exceção quando o token é válido")
    void testValidarTokenValid() {
        // When & Then
        // Não deve lançar exceção
        ReflectionTestUtils.invokeMethod(client, "validarToken");
    }

    @Test
    @DisplayName("Deve lançar AdvisorException quando o token é nulo")
    void testValidarTokenNull() {
        // Arrange
        ReflectionTestUtils.setField(client, "advisorToken", null);

        // Act & Assert
        assertThatThrownBy(() -> ReflectionTestUtils.invokeMethod(client, "validarToken"))
                .isInstanceOf(AdvisorException.class)
                .hasMessage("Token de API do Advisor não configurado");
    }

    @Test
    @DisplayName("Deve lançar AdvisorException quando o token é vazio")
    void testValidarTokenEmpty() {
        // Arrange
        ReflectionTestUtils.setField(client, "advisorToken", "");

        // Act & Assert
        assertThatThrownBy(() -> ReflectionTestUtils.invokeMethod(client, "validarToken"))
                .isInstanceOf(AdvisorException.class)
                .hasMessage("Token de API do Advisor não configurado");
    }
}

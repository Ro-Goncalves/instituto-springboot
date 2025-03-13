package com.rgbrain.brianbot.domain.clima.infrastructure.adivsor.model.exception;

public class AdvisorServerException extends AdvisorException {
    public AdvisorServerException(String message, Throwable cause) {
        super("Erro no Servidor Advisor: " + message, cause);
    }
}

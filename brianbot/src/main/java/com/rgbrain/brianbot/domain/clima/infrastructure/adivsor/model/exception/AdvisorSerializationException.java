package com.rgbrain.brianbot.domain.clima.infrastructure.adivsor.model.exception;

public class AdvisorSerializationException extends AdvisorException {
    public AdvisorSerializationException(String message, Throwable cause) {
        super("Erro na serialização dos dados do Advisor: " + message, cause);
    }
}

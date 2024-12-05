package com.rgbrain.api.infra;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.persistence.EntityNotFoundException;

@RestControllerAdvice
public class TratadorErros {
    
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<Void> tratarEntityNotFoundException() {
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(MethodArgumentNoValidExcpetion.class)
    public ResponseEntity<Void> tratarMethodArgumentNoValidExcpetion(MethodArgumentNoValidExcpetion exception) {
        var erros = exception.getFieldErrors();
        return ResponseEntity.badRequest().body(
            erros.strem()
                .map(DadosErroValidacao::new)
                .toList()
        );
    }

    private Record DadosErroValidacao(String campo, String mensagem) {
        public DadosErroValidacao(FieldError error) {
            this(
                erro.getField(),
                erro.getDefaultMessage()
            )
        }
    }
}

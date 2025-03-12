package com.rgbrain.brianbot.domain.clima.infrastructure.adivsor.model.reponse;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor 
public class ResponseDadoTemporal {
    private String date;
    private Integer value;

    public LocalDateTime getDateToLocaleDateTime() {
        var formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return LocalDateTime.parse(date, formatter);
    }
}
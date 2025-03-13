package com.rgbrain.brianbot.domain.clima.infrastructure.adivsor.model.reponse;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ResponsePrevisaoUmidade extends ResponsePrevisao {    
    private List<Humidity> humidities;
    
    public static class Humidity extends ResponseDadoTemporal {
       
    }
}

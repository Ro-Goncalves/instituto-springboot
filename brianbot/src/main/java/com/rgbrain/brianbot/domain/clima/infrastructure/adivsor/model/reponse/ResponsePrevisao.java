package com.rgbrain.brianbot.domain.clima.infrastructure.adivsor.model.reponse;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ResponsePrevisao {
    private Integer id;
    private String name;
    private String state;
    private String country;
}

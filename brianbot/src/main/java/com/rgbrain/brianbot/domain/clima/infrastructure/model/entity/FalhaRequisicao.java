package com.rgbrain.brianbot.domain.clima.infrastructure.model.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity(name = "FalhaRequisicao")
@Table(name = "falhas_requisicao")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FalhaRequisicao {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_Falha_Requisicao")
    private Long id;
    
    @Column(name = "TP_Requisicao")
    private String tipoRequisicao;

    @Column(name = "URL_Requisicao")
    private String url;

    @Column(name = "MSN_Error")
    private String mensagemErro;
    
    @Column(name = "Stack_Trace")
    private String stackTrace;
    
    @Column(name = "DT_Falha")
    private LocalDateTime dataHora;
}

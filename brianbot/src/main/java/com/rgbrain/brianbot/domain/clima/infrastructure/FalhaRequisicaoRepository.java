package com.rgbrain.brianbot.domain.clima.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rgbrain.brianbot.domain.clima.infrastructure.model.entity.FalhaRequisicao;

@Repository
public interface FalhaRequisicaoRepository extends JpaRepository<FalhaRequisicao, Long> {
    
}

package com.rgbrain.api.domain.medico;

import java.time.LocalDateTime;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface MedicoRepository extends JpaRepository<Medico, Long> {
    Page<Medico> findAllByAtivoTrue(Pageable paginacao);

    @Query(value = """
        SELECT m 
        FROM Medico m 
        WHERE 
            m.ativo = TRUE
            AND m.especialidade = :especialidade
            AND NOT EXISTS (
                SELECT 1
                FROM Consulta c
                WHERE 
                    c.medico.id = m.id
                    AND c.data = :data
            )
        ORDER BY m.id
        LIMIT 1
    """)
    Medico escolherMedicoAleatorio(Especialidade especialidade, LocalDateTime data);

    @Query("""
        SELECT m.ativo 
        FROM Medico m 
        WHERE m.id = :idMedico            
    """)
    Boolean findAtivoById(Long idMedico);

}



package com.rgbrain.api.domain.consulta;

import java.time.LocalDateTime;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ConsultaRepository extends JpaRepository<Consulta, Long>{
    boolean existsByMedicoIdAndData(Long idMedico, LocalDateTime data);

    Boolean existsByPacienteIdAndDataBetween(Long idPaciente, LocalDateTime primeiroHorario,
            LocalDateTime ultimoHorario);
}

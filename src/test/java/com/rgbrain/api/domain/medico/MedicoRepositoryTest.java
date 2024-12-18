package com.rgbrain.api.domain.medico;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.AFTER_TEST_METHOD;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

@SpringBootTest
@ActiveProfiles("test")
public class MedicoRepositoryTest {

    @Autowired
    private MedicoRepository medicoRepository;

    @Test
    @DisplayName("Deve retornar NULL quando nenhum medico estiver disponivel")
    @Sql({"/medico-nao-disponivel.sql"})
    @Sql(scripts = "/limpar-database.sql", executionPhase = AFTER_TEST_METHOD)
    void quandoNenhumMedicoEstiverDisponivel_deveRetornarNull() {        
        // GIVEN
        var dataConsulta = dataConsulta();

        // WHEN
        var medicoLivre = medicoRepository.escolherMedicoAleatorio(Especialidade.CARDIOLOGIA, dataConsulta);

        // THEN
        assertThat(medicoLivre).isNull();
    }

    private LocalDateTime dataConsulta() {
        var dataHoraString = "2024-12-23T10:00";
        var formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
        var dataConsulta = LocalDateTime.parse(dataHoraString, formatter);
        
        return dataConsulta;
    }
}

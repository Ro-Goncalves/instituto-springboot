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
    @DisplayName("Deveria Devolver Null quando unico médico cadastrado não está disponível na data")
    @Sql({"/medico-nao-disponivel.sql"})
    @Sql(scripts = "/limpar-database.sql", executionPhase = AFTER_TEST_METHOD)
    void fake() {
        String dataHoraString = "2024-12-16T10:00:00+03:00";
        DateTimeFormatter formatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME; 
        var dataConsulta = LocalDateTime.parse(dataHoraString, formatter);

        var medicoLivre = medicoRepository.escolherMedicoAleatorio(Especialidade.CARDIOLOGIA, dataConsulta);

        assertThat(medicoLivre).isNull();
    }
}

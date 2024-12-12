CREATE TABLE consultas(
    id          BIGINT PRIMARY KEY AUTOINCREMENT,
    medico_id   BIGINT,
    paciente_id BIGINT,
    data        TEXT,

    FOREIGN KEY(fk_consultas_medico_id) REFERENCES medicos(id),
    FOREIGN KEY(fk_consultas_paciente_id) REFERENCES pacientes(id)
);
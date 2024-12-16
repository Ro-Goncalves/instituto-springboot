CREATE TABLE consultas(
    id          INTEGER PRIMARY KEY AUTOINCREMENT,
    medico_id   INTEGER,
    paciente_id INTEGER,
    data        TEXT,

    FOREIGN KEY(medico_id) REFERENCES medicos(id),
    FOREIGN KEY(paciente_id) REFERENCES pacientes(id)
);
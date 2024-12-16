INSERT INTO medicos(id, nome, email, telefone, crm, especialidade, logradouro, bairro, cep, complemento, numero, uf, cidade, ativo)
VALUES
    (1, "Médico Teste 01", "E-Mail Médico 01", "1111111111","000001", "CARDIOLOGIA", "Médico Logradouro 01", "Médico Bairro 01", "000000001", "Médico Complemento 01", "001", "UF", "Médico Cidade 01", 1);

INSERT INTO pacientes(id, nome, email, cpf, logradouro, bairro, cep, complemento, numero, uf, cidade, telefone, ativo)
VALUES
    (1, "Paciente Teste 01", "E-Mail Paciente 01", "11111111111", "Paciente Logradouro 01", "Paciente Bairro 01", "000000001", "Paciente Complemento 01", "001", "UF", "Paciente Cidade 01", "1111111111", 1);

INSERT INTO consultas(id, medico_id, paciente_id, data)
VALUES
    (1, 1, 1, '2024-12-16T10:00:00+03:00');
CREATE TABLE pacientes(
    id          BIGINT       PRIMARY KEY AUTOINCREMENT,
    nome        VARCHAR(100) NOT NULL,
    email       VARCHAR(100) NOT NULL UNIQUE,
    cpf         VARCHAR(14)  NOT NULL UNIQUE,
    logradouro  VARCHAR(100) NOT NULL,
    bairro      VARCHAR(100) NOT NULL,
    cep         VARCHAR(9)   NOT NULL,
    complemento VARCHAR(100) DEFAULT NULL,
    numero      VARCHAR(20)  DEFAULT NULL,
    uf          CHAR(2)      NOT NULL,
    cidade      VARCHAR(100) NOT NULL,
    telefone    VARCHAR(20)  NOT NULL,
    ativo       TINYINT      NOT NULL
);
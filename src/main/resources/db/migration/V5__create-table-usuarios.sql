CREATE TABLE usuarios(
    id    INTEGER      PRIMARY KEY AUTOINCREMENT,
    login VARCHAR(100) NOT NULL UNIQUE,
    senha VARCHAR(255) NOT NULL
);

INSERT INTO usuarios(login, senha) VALUES('rodrigo.jesus@voll.med', '$2a$10$/mbdRFp53evVENH32rxXEewML5J1aUTVvrBuaJ6/oFRfX/3bLHZWW')

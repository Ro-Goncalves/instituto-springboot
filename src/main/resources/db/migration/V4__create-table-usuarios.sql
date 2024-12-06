CREATE TABLE usuarios(
    id            INTEGER       PRIMARY KEY AUTOINCREMENT,
    login         VARCHAR(100)  NOT NULL UNIQUE,
    senha         VARCHAR(255)  NOT NULL
);

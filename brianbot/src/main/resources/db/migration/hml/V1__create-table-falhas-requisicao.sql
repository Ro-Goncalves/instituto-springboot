CREATE TABLE falhas_requisicao (
    ID_Falha_Requisicao INTEGER PRIMARY KEY AUTOINCREMENT,
    TP_Requisicao               VARCHAR(30) NOT NULL,
    URL_Requisicao              VARCHAR(255) NOT NULL,
    MSN_Error                   VARCHAR(255) NOT NULL,
    Stack_Trace TEXT            NOT NULL,
    DT_Falha DATETIME           NOT NULL
);
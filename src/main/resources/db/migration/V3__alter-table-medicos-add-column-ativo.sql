ALTER TABLE medicos ADD COLUMN ativo TINYINT DEFAULT 0;
UPDATE medicos SET ativo = 1;
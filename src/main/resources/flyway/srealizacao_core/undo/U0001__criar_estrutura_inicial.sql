-- Remove todas as tabelas em ordem reversa das dependências (restrição, prioridade, gratidao, aprendizagem, folha)
DROP TABLE IF EXISTS srealizacao_core.restricao CASCADE;
DROP TABLE IF EXISTS srealizacao_core.prioridade CASCADE;
DROP TABLE IF EXISTS srealizacao_core.gratidao CASCADE;
DROP TABLE IF EXISTS srealizacao_core.aprendizagem CASCADE;
DROP TABLE IF EXISTS srealizacao_core.folha CASCADE;

-- Remove as sequências (caso não tenham sido dropadas pelo CASCADE)
DROP SEQUENCE IF EXISTS srealizacao_core.restricao_idrestricao_seq;
DROP SEQUENCE IF EXISTS srealizacao_core.prioridade_idprioridade_seq;
DROP SEQUENCE IF EXISTS srealizacao_core.gratidao_idgratidao_seq;
DROP SEQUENCE IF EXISTS srealizacao_core.aprendizagem_idaprendizagem_seq;
DROP SEQUENCE IF EXISTS srealizacao_core.folha_idfolha_seq;

-- Remove o schema (se vazio)
DROP SCHEMA IF EXISTS srealizacao_core CASCADE;

-- Criação do schema
CREATE SCHEMA IF NOT EXISTS srealizacao_core;

COMMENT ON SCHEMA srealizacao_core IS 'Schema principal do sistema Srealizacao';

-- Sequências
CREATE SEQUENCE IF NOT EXISTS srealizacao_core.aprendizagem_idaprendizagem_seq START WITH 1 INCREMENT BY 1 NO MINVALUE NO MAXVALUE CACHE 1;
CREATE SEQUENCE IF NOT EXISTS srealizacao_core.folha_idfolha_seq START WITH 1 INCREMENT BY 1 NO MINVALUE NO MAXVALUE CACHE 1;
CREATE SEQUENCE IF NOT EXISTS srealizacao_core.gratidao_idgratidao_seq START WITH 1 INCREMENT BY 1 NO MINVALUE NO MAXVALUE CACHE 1;
CREATE SEQUENCE IF NOT EXISTS srealizacao_core.prioridade_idprioridade_seq START WITH 1 INCREMENT BY 1 NO MINVALUE NO MAXVALUE CACHE 1;
CREATE SEQUENCE IF NOT EXISTS srealizacao_core.restricao_idrestricao_seq START WITH 1 INCREMENT BY 1 NO MINVALUE NO MAXVALUE CACHE 1;

-- Tabela folha
CREATE TABLE IF NOT EXISTS srealizacao_core.folha (
    idfolha BIGINT NOT NULL DEFAULT nextval('srealizacao_core.folha_idfolha_seq'),
    foco TEXT NOT NULL, -- Comentário: Foco principal da folha
    notadia INTEGER, -- Comentário: Nota do dia (avaliação)
    dtarealizacao DATE NOT NULL, -- Comentário: Data de realização da folha
    observacao TEXT, -- Comentário: Observações complementares
    status BOOLEAN NOT NULL, -- Comentário: Status da folha (ativa/finalizada)
    CONSTRAINT idx_24593_pk_folha PRIMARY KEY (idfolha)
);

COMMENT ON TABLE srealizacao_core.folha IS 'Tabela que armazena informações das folhas diárias.';
COMMENT ON COLUMN srealizacao_core.folha.idfolha IS 'Identificador único da folha.';
COMMENT ON COLUMN srealizacao_core.folha.foco IS 'Foco principal da folha.';
COMMENT ON COLUMN srealizacao_core.folha.notadia IS 'Nota do dia (avaliação).';
COMMENT ON COLUMN srealizacao_core.folha.dtarealizacao IS 'Data de realização da folha.';
COMMENT ON COLUMN srealizacao_core.folha.observacao IS 'Observações complementares.';
COMMENT ON COLUMN srealizacao_core.folha.status IS 'Status da folha (ativa/finalizada).';

-- Tabela aprendizagem
CREATE TABLE IF NOT EXISTS srealizacao_core.aprendizagem (
    idaprendizagem BIGINT NOT NULL DEFAULT nextval('srealizacao_core.aprendizagem_idaprendizagem_seq'),
    descricao TEXT NOT NULL, -- Comentário: Descrição da aprendizagem
    isconcluido BOOLEAN NOT NULL, -- Comentário: Indica se a aprendizagem foi concluída
    ordem INTEGER NOT NULL, -- Comentário: Ordem de exibição
    idfolha INTEGER NOT NULL, -- Comentário: Chave estrangeira para folha
    CONSTRAINT idx_24580_pk_aprendizagem PRIMARY KEY (idaprendizagem),
    CONSTRAINT fk_aprendizagem_folha FOREIGN KEY (idfolha) REFERENCES srealizacao_core.folha(idfolha)
);

COMMENT ON TABLE srealizacao_core.aprendizagem IS 'Tabela que armazena os aprendizados do dia.';
COMMENT ON COLUMN srealizacao_core.aprendizagem.idaprendizagem IS 'Identificador único da aprendizagem.';
COMMENT ON COLUMN srealizacao_core.aprendizagem.descricao IS 'Descrição da aprendizagem.';
COMMENT ON COLUMN srealizacao_core.aprendizagem.isconcluido IS 'Indica se a aprendizagem foi concluída.';
COMMENT ON COLUMN srealizacao_core.aprendizagem.ordem IS 'Ordem de exibição.';
COMMENT ON COLUMN srealizacao_core.aprendizagem.idfolha IS 'Chave estrangeira para folha.';

-- Tabela gratidao
CREATE TABLE IF NOT EXISTS srealizacao_core.gratidao (
    idgratidao BIGINT NOT NULL DEFAULT nextval('srealizacao_core.gratidao_idgratidao_seq'),
    descricao TEXT NOT NULL, -- Comentário: Descrição do motivo de gratidão
    isconcluido BOOLEAN NOT NULL, -- Comentário: Indica se o motivo foi concluído
    ordem INTEGER NOT NULL, -- Comentário: Ordem de exibição
    idfolha INTEGER NOT NULL, -- Comentário: Chave estrangeira para folha
    CONSTRAINT idx_24600_pk_gratidao PRIMARY KEY (idgratidao),
    CONSTRAINT fk_gratidao_folha FOREIGN KEY (idfolha) REFERENCES srealizacao_core.folha(idfolha)
);

COMMENT ON TABLE srealizacao_core.gratidao IS 'Tabela que armazena motivos de gratidão.';
COMMENT ON COLUMN srealizacao_core.gratidao.idgratidao IS 'Identificador único do motivo de gratidão.';
COMMENT ON COLUMN srealizacao_core.gratidao.descricao IS 'Descrição do motivo de gratidão.';
COMMENT ON COLUMN srealizacao_core.gratidao.isconcluido IS 'Indica se o motivo foi concluído.';
COMMENT ON COLUMN srealizacao_core.gratidao.ordem IS 'Ordem de exibição.';
COMMENT ON COLUMN srealizacao_core.gratidao.idfolha IS 'Chave estrangeira para folha.';

-- Tabela prioridade
CREATE TABLE IF NOT EXISTS srealizacao_core.prioridade (
    idprioridade BIGINT NOT NULL DEFAULT nextval('srealizacao_core.prioridade_idprioridade_seq'),
    descricao TEXT NOT NULL, -- Comentário: Descrição da prioridade
    isconcluido BOOLEAN NOT NULL, -- Comentário: Indica se a prioridade foi concluída
    ordem INTEGER NOT NULL, -- Comentário: Ordem de exibição
    idfolha INTEGER NOT NULL, -- Comentário: Chave estrangeira para folha
    CONSTRAINT idx_24624_pk_prioridades PRIMARY KEY (idprioridade),
    CONSTRAINT fk_prioridade_foco FOREIGN KEY (idfolha) REFERENCES srealizacao_core.folha(idfolha)
);

COMMENT ON TABLE srealizacao_core.prioridade IS 'Tabela que armazena prioridades do dia.';
COMMENT ON COLUMN srealizacao_core.prioridade.idprioridade IS 'Identificador único da prioridade.';
COMMENT ON COLUMN srealizacao_core.prioridade.descricao IS 'Descrição da prioridade.';
COMMENT ON COLUMN srealizacao_core.prioridade.isconcluido IS 'Indica se a prioridade foi concluída.';
COMMENT ON COLUMN srealizacao_core.prioridade.ordem IS 'Ordem de exibição.';
COMMENT ON COLUMN srealizacao_core.prioridade.idfolha IS 'Chave estrangeira para folha.';

-- Tabela restricao
CREATE TABLE IF NOT EXISTS srealizacao_core.restricao (
    idrestricao BIGINT NOT NULL DEFAULT nextval('srealizacao_core.restricao_idrestricao_seq'),
    descricao TEXT NOT NULL, -- Comentário: Descrição da restrição
    isconcluido BOOLEAN NOT NULL, -- Comentário: Indica se a restrição foi concluída
    ordem INTEGER, -- Comentário: Ordem de exibição (pode ser nula)
    idfolha INTEGER, -- Comentário: Chave estrangeira para folha (pode ser nula)
    CONSTRAINT idx_24631_pk_restricao PRIMARY KEY (idrestricao),
    CONSTRAINT fk_restricao_folha FOREIGN KEY (idfolha) REFERENCES srealizacao_core.folha(idfolha)
);

COMMENT ON TABLE srealizacao_core.restricao IS 'Tabela que armazena restrições do dia.';
COMMENT ON COLUMN srealizacao_core.restricao.idrestricao IS 'Identificador único da restrição.';
COMMENT ON COLUMN srealizacao_core.restricao.descricao IS 'Descrição da restrição.';
COMMENT ON COLUMN srealizacao_core.restricao.isconcluido IS 'Indica se a restrição foi concluída.';
COMMENT ON COLUMN srealizacao_core.restricao.ordem IS 'Ordem de exibição (pode ser nula).';
COMMENT ON COLUMN srealizacao_core.restricao.idfolha IS 'Chave estrangeira para folha (pode ser nula).';

-- Ownership, setval, e outros comandos administrativos não são incluídos (não são suportados ou necessários para o Flyway).

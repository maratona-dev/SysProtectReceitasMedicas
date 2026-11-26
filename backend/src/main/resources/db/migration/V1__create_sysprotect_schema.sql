CREATE TABLE pacientes (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    nome VARCHAR(150) NOT NULL,
    cpf VARCHAR(11) NOT NULL,
    data_nascimento DATE NOT NULL,
    ativo BOOLEAN NOT NULL DEFAULT TRUE,
    CONSTRAINT uk_pacientes_cpf UNIQUE (cpf)
);

CREATE TABLE medicos (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    nome VARCHAR(150) NOT NULL,
    crm VARCHAR(20) NOT NULL,
    uf_crm VARCHAR(2) NOT NULL,
    especialidade VARCHAR(100),
    crm_ativo BOOLEAN NOT NULL DEFAULT TRUE,
    CONSTRAINT uk_medicos_crm UNIQUE (crm)
);

CREATE TABLE medicamentos (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    nome VARCHAR(150) NOT NULL,
    codigo_anvisa VARCHAR(30),
    principio_ativo VARCHAR(150) NOT NULL,
    dosagem_maxima_diaria NUMERIC(10, 2),
    unidade_medida VARCHAR(20),
    controlado BOOLEAN NOT NULL DEFAULT FALSE,
    ativo BOOLEAN NOT NULL DEFAULT TRUE,
    CONSTRAINT uk_medicamentos_codigo_anvisa UNIQUE (codigo_anvisa)
);

CREATE TABLE receitas (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    codigo_receita VARCHAR(64) NOT NULL,
    hash_conteudo VARCHAR(128) NOT NULL,
    data_emissao TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    data_validade DATE NOT NULL,
    assinatura_digital TEXT,
    assinatura_valida BOOLEAN NOT NULL DEFAULT FALSE,
    validade_ok BOOLEAN NOT NULL DEFAULT TRUE,
    situacao_analise INTEGER NOT NULL DEFAULT 1,
    paciente_id UUID NOT NULL,
    medico_id UUID NOT NULL,
    CONSTRAINT uk_receitas_codigo_receita UNIQUE (codigo_receita),
    CONSTRAINT uk_receitas_hash_conteudo UNIQUE (hash_conteudo),
    CONSTRAINT ck_receitas_situacao_analise CHECK (situacao_analise IN (1, 2, 3, 4, 5)),
    CONSTRAINT fk_receitas_paciente FOREIGN KEY (paciente_id) REFERENCES pacientes (id),
    CONSTRAINT fk_receitas_medico FOREIGN KEY (medico_id) REFERENCES medicos (id)
);

CREATE INDEX idx_receitas_situacao_analise ON receitas (situacao_analise);
CREATE INDEX idx_receitas_paciente_id ON receitas (paciente_id);
CREATE INDEX idx_receitas_medico_id ON receitas (medico_id);

CREATE TABLE receita_itens (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    receita_id UUID NOT NULL,
    medicamento_id UUID NOT NULL,
    dosagem_prescrita NUMERIC(10, 2) NOT NULL,
    quantidade INTEGER NOT NULL,
    posologia VARCHAR(255),
    CONSTRAINT fk_receita_itens_receita FOREIGN KEY (receita_id) REFERENCES receitas (id) ON DELETE CASCADE,
    CONSTRAINT fk_receita_itens_medicamento FOREIGN KEY (medicamento_id) REFERENCES medicamentos (id)
);

CREATE INDEX idx_receita_itens_receita_id ON receita_itens (receita_id);
CREATE INDEX idx_receita_itens_medicamento_id ON receita_itens (medicamento_id);

CREATE TABLE tipos_fraude (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    codigo VARCHAR(50) NOT NULL,
    descricao VARCHAR(255) NOT NULL,
    severidade INTEGER NOT NULL DEFAULT 1,
    CONSTRAINT uk_tipos_fraude_codigo UNIQUE (codigo)
);

CREATE TABLE fraudes (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    receita_id UUID NOT NULL,
    tipo_fraude_id UUID NOT NULL,
    data_deteccao TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    detalhes_evidencia TEXT,
    resolvida BOOLEAN NOT NULL DEFAULT FALSE,
    CONSTRAINT fk_fraudes_receita FOREIGN KEY (receita_id) REFERENCES receitas (id) ON DELETE CASCADE,
    CONSTRAINT fk_fraudes_tipo_fraude FOREIGN KEY (tipo_fraude_id) REFERENCES tipos_fraude (id)
);

CREATE INDEX idx_fraudes_receita_id ON fraudes (receita_id);
CREATE INDEX idx_fraudes_tipo_fraude_id ON fraudes (tipo_fraude_id);

CREATE TABLE condicoes_teste (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    tipo_fraude_id UUID NOT NULL,
    campo_alvo VARCHAR(100) NOT NULL,
    operador VARCHAR(20) NOT NULL,
    valor_esperado VARCHAR(100) NOT NULL,
    ativa BOOLEAN NOT NULL DEFAULT TRUE,
    CONSTRAINT fk_condicoes_teste_tipo_fraude FOREIGN KEY (tipo_fraude_id) REFERENCES tipos_fraude (id) ON DELETE CASCADE
);

CREATE INDEX idx_condicoes_teste_tipo_fraude_id ON condicoes_teste (tipo_fraude_id);

CREATE TABLE cenarios_teste (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    condicao_teste_id UUID NOT NULL,
    nome VARCHAR(150) NOT NULL,
    entrada_simulada VARCHAR(255) NOT NULL,
    fraude_esperada BOOLEAN NOT NULL,
    CONSTRAINT fk_cenarios_teste_condicao_teste FOREIGN KEY (condicao_teste_id) REFERENCES condicoes_teste (id) ON DELETE CASCADE
);

CREATE INDEX idx_cenarios_teste_condicao_teste_id ON cenarios_teste (condicao_teste_id);

CREATE TABLE role (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    role_name VARCHAR(255) NOT NULL,
    enabled BOOLEAN NOT NULL DEFAULT TRUE,
    CONSTRAINT uk_role_role_name UNIQUE (role_name)
);

CREATE TABLE app_user (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    username VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    full_name VARCHAR(255) NOT NULL,
    enabled BOOLEAN NOT NULL DEFAULT TRUE,
    CONSTRAINT uk_app_user_username UNIQUE (username)
);

CREATE TABLE user_role (
    user_id UUID NOT NULL,
    role_id UUID NOT NULL,
    CONSTRAINT pk_app_user_role PRIMARY KEY (user_id, role_id),
    CONSTRAINT fk_app_user_role_user FOREIGN KEY (user_id) REFERENCES app_user (id) ON DELETE CASCADE,
    CONSTRAINT fk_app_user_role_role FOREIGN KEY (role_id) REFERENCES role (id) ON DELETE CASCADE
);

CREATE INDEX idx_user_role_role_id ON user_role (role_id);
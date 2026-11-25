INSERT INTO role (role_name) VALUES
('ROLE_USER'),
('ROLE_ADMIN');

INSERT INTO app_user (username, password, full_name, enabled) VALUES
('admin', '$2a$10$r.onnlwQHsdFcNZSOu7ZYOvkc5i9o8MVlpiaU9U6MxCWyon0eXXzK', 'Administrator', TRUE),
('user', '$2a$10$r.onnlwQHsdFcNZSOu7ZYOvkc5i9o8MVlpiaU9U6MxCWyon0eXXzK', 'Student One', TRUE);

INSERT INTO user_role (user_id, role_id)
SELECT u.id, r.id
FROM app_user u
CROSS JOIN role r
WHERE u.username = 'admin'
  AND r.role_name IN ('ROLE_USER', 'ROLE_ADMIN');

INSERT INTO user_role (user_id, role_id)
SELECT u.id, r.id
FROM app_user u
CROSS JOIN role r
WHERE u.username = 'user'
  AND r.role_name = 'ROLE_USER';

INSERT INTO tipos_fraude (codigo, descricao, severidade) VALUES
('MEDICO_INVALIDO', 'Registro do médico inválido ou inativo', 5),
('CRM_INEXISTENTE', 'CRM não encontrado na base do conselho', 5),
('RECEITA_ADULTERADA', 'Conteúdo da receita foi modificado após a emissão', 5),
('RECEITA_DUPLICADA', 'Mesma receita submetida mais de uma vez', 4),
('PACIENTE_INEXISTENTE', 'Paciente não cadastrado ou inválido', 4),
('MEDICAMENTO_INVALIDO', 'Medicamento não registrado ou inativo', 4),
('DOSAGEM_INVALIDA', 'Dosagem prescrita fora dos limites permitidos', 3),
('RECEITA_VENCIDA', 'Prazo de validade da receita expirado', 3),
('ASSINATURA_INVALIDA', 'Assinatura digital ou do emitente inválida', 5),
('RECEITA_FALSA', 'Autenticidade da notificação de receita não verificada', 5);

INSERT INTO condicoes_teste (tipo_fraude_id, campo_alvo, operador, valor_esperado, ativa)
SELECT id, 'crm_ativo', 'EQUALS', 'false', TRUE
FROM tipos_fraude
WHERE codigo = 'MEDICO_INVALIDO';

INSERT INTO condicoes_teste (tipo_fraude_id, campo_alvo, operador, valor_esperado, ativa)
SELECT id, 'hash_conteudo', 'EXISTS', 'true', TRUE
FROM tipos_fraude
WHERE codigo = 'RECEITA_DUPLICADA';

INSERT INTO condicoes_teste (tipo_fraude_id, campo_alvo, operador, valor_esperado, ativa)
SELECT id, 'validade_ok', 'EQUALS', 'false', TRUE
FROM tipos_fraude
WHERE codigo = 'RECEITA_VENCIDA';

INSERT INTO condicoes_teste (tipo_fraude_id, campo_alvo, operador, valor_esperado, ativa)
SELECT id, 'assinatura_valida', 'EQUALS', 'false', TRUE
FROM tipos_fraude
WHERE codigo = 'ASSINATURA_INVALIDA';

INSERT INTO condicoes_teste (tipo_fraude_id, campo_alvo, operador, valor_esperado, ativa)
SELECT id, 'dosagem_prescrita', 'GREATER_THAN', 'dosagem_maxima_diaria', TRUE
FROM tipos_fraude
WHERE codigo = 'DOSAGEM_INVALIDA';

INSERT INTO cenarios_teste (condicao_teste_id, nome, entrada_simulada, fraude_esperada)
SELECT c.id, 'CRM active', 'crm_ativo = true', FALSE
FROM condicoes_teste c
JOIN tipos_fraude t ON c.tipo_fraude_id = t.id
WHERE t.codigo = 'MEDICO_INVALIDO';

INSERT INTO cenarios_teste (condicao_teste_id, nome, entrada_simulada, fraude_esperada)
SELECT c.id, 'CRM inactive', 'crm_ativo = false', TRUE
FROM condicoes_teste c
JOIN tipos_fraude t ON c.tipo_fraude_id = t.id
WHERE t.codigo = 'MEDICO_INVALIDO';

INSERT INTO cenarios_teste (condicao_teste_id, nome, entrada_simulada, fraude_esperada)
SELECT c.id, 'Duplicate prescription', 'Existing hash', TRUE
FROM condicoes_teste c
JOIN tipos_fraude t ON c.tipo_fraude_id = t.id
WHERE t.codigo = 'RECEITA_DUPLICADA';

INSERT INTO cenarios_teste (condicao_teste_id, nome, entrada_simulada, fraude_esperada)
SELECT c.id, 'Expired prescription', 'validade_ok = false', TRUE
FROM condicoes_teste c
JOIN tipos_fraude t ON c.tipo_fraude_id = t.id
WHERE t.codigo = 'RECEITA_VENCIDA';

INSERT INTO cenarios_teste (condicao_teste_id, nome, entrada_simulada, fraude_esperada)
SELECT c.id, 'Invalid signature', 'assinatura_valida = false', TRUE
FROM condicoes_teste c
JOIN tipos_fraude t ON c.tipo_fraude_id = t.id
WHERE t.codigo = 'ASSINATURA_INVALIDA';

INSERT INTO pacientes (nome, cpf, data_nascimento, ativo) VALUES
('João Carlos Pereira', '12345678901', '1988-04-12', TRUE),
('José Roberto', '98765432100', '1975-09-20', TRUE);

INSERT INTO medicos (nome, crm, uf_crm, especialidade, crm_ativo) VALUES
('Dr. Sávio Luís Oliveira Costa', '5649', 'CE', 'Psiquiatria', TRUE),
('Dr. Abraão Nicolau Salum', '18455', 'SP', 'Clínica Médica', TRUE);

INSERT INTO medicamentos (nome, codigo_anvisa, principio_ativo, dosagem_maxima_diaria, unidade_medida, controlado, ativo) VALUES
('Ritalina 10 mg', '1006800540011', 'Cloridrato de Metilfenidato', 60.00, 'mg', TRUE, TRUE),
('Diazepam 10 mg', '1023504820024', 'Diazepam', 40.00, 'mg', TRUE, TRUE);

INSERT INTO receitas (
    codigo_receita,
    hash_conteudo,
    data_emissao,
    data_validade,
    assinatura_digital,
    assinatura_valida,
    validade_ok,
    situacao_analise,
    paciente_id,
    medico_id
)
SELECT
    'CE-03185629-A',
    'a3f8c9e2b14d7098f5c6a12e4b89d03c7f1a2e5b6c8d9e0f1a2b3c4d5e6f7a8b',
    '2025-11-25 10:30:00',
    '2025-12-25',
    'CARIMBO-CRM-CE-5649-CONTROLE-1449147',
    FALSE,
    FALSE,
    3,
    p.id,
    m.id
FROM pacientes p
CROSS JOIN medicos m
WHERE p.cpf = '12345678901'
  AND m.crm = '5649';

INSERT INTO receita_itens (receita_id, medicamento_id, dosagem_prescrita, quantidade, posologia)
SELECT
    r.id,
    med.id,
    10.00,
    1,
    '1 cp pela manhã (1 caixa)'
FROM receitas r
CROSS JOIN medicamentos med
WHERE r.codigo_receita = 'CE-03185629-A'
  AND med.codigo_anvisa = '1006800540011';

INSERT INTO fraudes (receita_id, tipo_fraude_id, data_deteccao, detalhes_evidencia, resolvida)
SELECT
    r.id,
    tf.id,
    '2025-11-26 14:15:00',
    'Notificação de Receita A emitida no CE (Sobral) apresentada para dispensação em SC (CRF-SC) com assinatura/autenticidade sob alerta.',
    FALSE
FROM receitas r
CROSS JOIN tipos_fraude tf
WHERE r.codigo_receita = 'CE-03185629-A'
  AND tf.codigo = 'RECEITA_FALSA';

INSERT INTO receitas (
    codigo_receita,
    hash_conteudo,
    data_emissao,
    data_validade,
    assinatura_digital,
    assinatura_valida,
    validade_ok,
    situacao_analise,
    paciente_id,
    medico_id
)
SELECT
    'SP-951453-B',
    'f9e8d7c6b5a4938271605f4e3d2c1b0a9f8e7d6c5b4a39281706f5e4d3c2b1a0',
    '2025-03-21 09:00:00',
    '2025-04-20',
    'ASSINATURA-DIGITAL-ESF-SP-18455',
    TRUE,
    TRUE,
    1,
    p.id,
    m.id
FROM pacientes p
CROSS JOIN medicos m
WHERE p.cpf = '98765432100'
  AND m.crm = '18455';

INSERT INTO receita_itens (receita_id, medicamento_id, dosagem_prescrita, quantidade, posologia)
SELECT
    r.id,
    med.id,
    10.00,
    60,
    '60 comprimidos conforme prescrição médica'
FROM receitas r
CROSS JOIN medicamentos med
WHERE r.codigo_receita = 'SP-951453-B'
  AND med.codigo_anvisa = '1023504820024';
# CONTRIBUTING.md

# Guia de Contribuição – SysProtect

Este documento estabelece as diretrizes de contribuição para o projeto **SysProtect**, padronizando nomenclaturas de branches, mensagens de commit e fluxo de desenvolvimento.

O objetivo é manter a organização do repositório, facilitar a revisão de código e garantir uma colaboração eficiente entre os integrantes da equipe.

---

# 1. Padrão de Commits

O projeto utiliza o padrão **Conventional Commits 1.0.0** para padronizar as mensagens de commit.

## 1.1. Estrutura do commit

Toda mensagem de commit deve seguir a estrutura:

```text
<tipo>(<escopo>): <descrição curta>

<descrição detalhada>
```

Exemplo:

```text
feat(receitas): implementar cadastro de receitas médicas

Implementado o cadastro de receitas médicas,
incluindo validação dos campos obrigatórios,
tratamento de exceções e integração com a API REST.
```

**Componentes:**

| Componente | Descrição |
|---|---|
| Tipo | Identifica a natureza da alteração |
| Escopo | Identifica o módulo ou componente afetado (opcional) |
| Descrição curta | Resume a alteração realizada |
| Extended description | Explica detalhadamente as modificações |

## 1.2. Tipos de commits

| Tipo | Finalidade | Exemplo |
|---|---|---|
| `feat` | Nova funcionalidade | Implementar cadastro de receitas |
| `fix` | Correção de erros | Corrigir validação de receitas |
| `docs` | Documentação | Adicionar documentação README |
| `style` | Formatação do código | Padronizar formatação Java |
| `refactor` | Refatoração | Reorganizar estrutura de serviços |
| `perf` | Melhoria de desempenho | Otimizar processamento de receitas |
| `test` | Testes automatizados | Adicionar testes unitários |
| `build` | Build e dependências | Atualizar dependências Maven |
| `ci` | Integração contínua | Configurar GitHub Actions |
| `chore` | Manutenção geral | Atualizar arquivos de configuração |
| `revert` | Reverter alterações | Reverter implementação anterior |

## 1.3. Exemplos de commits por categoria

### 1.3.1. feat – Nova funcionalidade

Utilizado quando uma nova funcionalidade é adicionada ao sistema.

```text
feat: implementar cadastro de receitas médicas
feat: implementar autenticação de usuários
feat: adicionar gerenciamento de usuários
feat: implementar consulta de receitas
feat: adicionar upload de receitas digitais
feat: implementar leitura de receitas físicas
feat: adicionar validação de receitas controladas
feat: implementar detecção de fraudes com IA
feat: adicionar análise de documentos médicos
feat: implementar classificação de receitas
feat: adicionar histórico de verificações
feat: implementar painel administrativo
feat: adicionar notificações de irregularidades
feat: implementar controle de permissões
feat: adicionar exportação de relatórios
```

**Exemplo com escopo:**

```text
feat(receitas): implementar cadastro de receitas médicas

Implementado o cadastro de receitas médicas,
permitindo registrar informações do paciente,
profissional responsável e dados da prescrição.
```

### 1.3.2. fix – Correção de erros

Utilizado para corrigir comportamentos incorretos ou falhas existentes.

```text
fix: corrigir validação de receitas médicas
fix: corrigir erro no cadastro de usuários
fix: ajustar tratamento de exceções
fix: corrigir autenticação de usuários
fix: corrigir autorização de acesso
fix: ajustar validação de campos obrigatórios
fix: corrigir processamento de arquivos PDF
fix: corrigir leitura de imagens
fix: ajustar resposta dos endpoints REST
fix: corrigir conversão de datas
fix: corrigir consulta de receitas
fix: ajustar validação de CPF
fix: corrigir tratamento de arquivos inválidos
fix: corrigir problema de paginação
fix: ajustar configuração de CORS
```

**Exemplo com escopo:**

```text
fix(security): corrigir validação de permissões

Corrigida a verificação das permissões de acesso,
impedindo que usuários sem autorização executem
operações restritas do sistema.
```

### 1.3.3. docs – Documentação

Utilizado exclusivamente para alterações na documentação.

```text
docs: adicionar documentação README
docs: adicionar guia de contribuição
docs: documentar arquitetura do projeto
docs: documentar tecnologias utilizadas
docs: documentar funcionalidades do sistema
docs: adicionar documentação da API REST
docs: documentar endpoints de receitas
docs: adicionar instruções de instalação
docs: documentar configuração do ambiente
docs: adicionar exemplos de requisições HTTP
docs: documentar autenticação JWT
docs: documentar medidas de segurança
docs: adicionar documentação do banco de dados
docs: atualizar instruções de execução
docs: adicionar diagrama de arquitetura
docs: documentar integração com inteligência artificial
docs: adicionar documentação de testes
docs: atualizar informações do projeto
```

**Exemplo com descrição detalhada:**

```text
docs: adicionar documentação README para o projeto SysProtect

Adicionada documentação completa para o projeto SysProtect,
detalhando sua arquitetura, tecnologias utilizadas,
funcionalidades e medidas de segurança implementadas.
```

### 1.3.4. style – Formatação e estilo

Utilizado para alterações que não modificam a lógica da aplicação.

```text
style: padronizar formatação das classes Java
style: ajustar identação dos arquivos
style: organizar imports das classes
style: padronizar nomenclatura de variáveis
style: ajustar espaçamento do código
style: padronizar formatação dos componentes Angular
style: organizar estrutura dos arquivos CSS
style: ajustar formatação dos arquivos HTML
style: padronizar nomes das classes CSS
style: remover espaços desnecessários
```

### 1.3.5. refactor – Refatoração

Utilizado para melhorar a estrutura interna do código sem alterar seu comportamento externo.

```text
refactor: reorganizar estrutura de pacotes
refactor: simplificar lógica de validação
refactor: melhorar organização dos serviços
refactor: separar responsabilidades das classes
refactor: reorganizar camada de persistência
refactor: melhorar estrutura dos controllers
refactor: centralizar tratamento de exceções
refactor: simplificar métodos de conversão
refactor: reorganizar componentes Angular
refactor: melhorar organização dos módulos
refactor: extrair lógica de validação para serviços
refactor: padronizar utilização de DTOs
```

### 1.3.6. perf – Performance

Utilizado para otimizações de desempenho.

```text
perf: otimizar processamento de receitas
perf: melhorar desempenho das consultas
perf: otimizar carregamento de imagens
perf: reduzir tempo de processamento de documentos
perf: otimizar consultas ao banco de dados
perf: melhorar processamento de arquivos PDF
perf: otimizar paginação de resultados
perf: reduzir consumo de memória
perf: melhorar desempenho da análise de documentos
perf: otimizar carregamento do painel administrativo
```

### 1.3.7. test – Testes automatizados

Utilizado para adicionar, corrigir ou melhorar testes.

```text
test: adicionar testes unitários para serviços
test: adicionar testes para cadastro de receitas
test: adicionar testes para validação de documentos
test: adicionar testes para autenticação
test: adicionar testes para controllers
test: adicionar testes para repositories
test: adicionar testes para tratamento de exceções
test: adicionar testes de integração
test: adicionar testes para validação de CPF
test: melhorar cobertura de testes
test: corrigir testes de autenticação
test: adicionar testes para detecção de fraudes
test: adicionar testes para upload de arquivos
test: adicionar testes para processamento de PDF
test: adicionar testes para permissões de usuários
```

### 1.3.8. build – Build e dependências

Utilizado para alterações no processo de compilação, empacotamento e gerenciamento de dependências.

```text
build: atualizar dependências do Spring Boot
build: adicionar dependência do MapStruct
build: adicionar dependência do Lombok
build: configurar compilação com Java 25
build: atualizar plugins do Maven
build: configurar processamento de anotações
build: adicionar dependência de validação
build: configurar dependências de testes
build: atualizar versão do Maven Compiler Plugin
build: configurar empacotamento da aplicação
```

### 1.3.9. ci – Integração contínua

Utilizado para configurações de automação e pipelines.

```text
ci: configurar GitHub Actions
ci: adicionar pipeline de compilação Maven
ci: configurar execução automatizada de testes
ci: adicionar verificação de qualidade do código
ci: configurar análise estática
ci: adicionar workflow de integração contínua
ci: configurar validação de Pull Requests
ci: automatizar execução dos testes unitários
ci: configurar pipeline de build
ci: adicionar verificação de dependências
```

### 1.3.10. chore – Manutenção

Utilizado para tarefas administrativas e manutenção que não representam funcionalidades diretamente.

```text
chore: adicionar arquivo gitignore
chore: atualizar configuração do projeto
chore: organizar estrutura do repositório
chore: atualizar versão da aplicação
chore: remover arquivos desnecessários
chore: configurar ambiente de desenvolvimento
chore: atualizar configurações do Eclipse
chore: adicionar arquivos de configuração
chore: organizar diretórios do projeto
chore: atualizar informações do projeto
chore: configurar variáveis de ambiente
```

### 1.3.11. revert – Reversão

Utilizado para desfazer alterações introduzidas por commits anteriores.

```text
revert: reverter implementação do cadastro de receitas
revert: desfazer alteração na autenticação JWT
revert: reverter atualização das dependências
revert: desfazer alteração na validação de documentos
```

---

# 2. Extended Description – Descrição detalhada

A descrição detalhada deve explicar o que foi implementado, corrigido ou modificado.

Não é obrigatório utilizar uma descrição detalhada em todos os commits. Ela é recomendada quando a alteração exige contexto adicional.

## 2.1. Exemplos de descrições detalhadas

**Cadastro de receitas:**

```text
feat(receitas): implementar cadastro de receitas médicas

Implementado o cadastro de receitas médicas,
permitindo registrar os dados necessários para
o gerenciamento e acompanhamento das prescrições.
```

**Autenticação:**

```text
feat(auth): implementar autenticação de usuários

Implementado o processo de autenticação utilizando
Spring Security e JWT, permitindo validar a identidade
dos usuários e controlar o acesso aos recursos protegidos.
```

**Validação:**

```text
feat(validation): adicionar validação de receitas controladas

Implementadas regras de validação para receitas
médicas controladas, considerando os campos obrigatórios
e possíveis inconsistências nas informações apresentadas.
```

**Inteligência artificial:**

```text
feat(ai): implementar análise de documentos com IA

Adicionada integração com o mecanismo de inteligência
artificial para auxiliar na identificação de possíveis
irregularidades e indícios de falsificação em receitas médicas.
```

**Testes:**

```text
test(receitas): adicionar testes unitários para validação

Adicionados testes unitários para verificar as regras
de validação das receitas médicas, incluindo cenários
válidos, dados inválidos e situações excepcionais.
```

**Documentação:**

```text
docs: adicionar documentação completa do projeto

Documentadas a arquitetura, as tecnologias utilizadas,
as funcionalidades disponíveis, as instruções de execução
e as medidas de segurança adotadas pelo SysProtect.
```

---

# 3. Padrão de nomenclatura de Branches

As branches devem utilizar prefixos que identifiquem o objetivo do desenvolvimento.

## 3.1. Estrutura da branch

```text
<tipo>/<descricao>
```

Exemplos:

```text
feature/cadastro-receitas
bugfix/validacao-receitas
hotfix/correcao-autenticacao
docs/atualizar-readme
refactor/estrutura-servicos
test/receitas-service
chore/atualizar-dependencias
ci/configurar-github-actions
```

## 3.2. Tipos de branches

| Prefixo | Finalidade |
|---|---|
| `feature/` | Nova funcionalidade |
| `bugfix/` | Correção de erros |
| `hotfix/` | Correção urgente |
| `docs/` | Documentação |
| `refactor/` | Refatoração |
| `test/` | Testes automatizados |
| `chore/` | Manutenção |
| `ci/` | Integração contínua |
| `build/` | Configuração de build |
| `perf/` | Otimização de desempenho |

## 3.3. Exemplos de branches para o SysProtect

### Backend – Java e Spring Boot

```text
feature/cadastro-receitas
feature/consulta-receitas
feature/atualizacao-receitas
feature/exclusao-receitas
feature/cadastro-usuarios
feature/gerenciamento-usuarios
feature/autenticacao-jwt
feature/autorizacao-usuarios
feature/validacao-receitas
feature/validacao-cpf
feature/upload-documentos
feature/processamento-pdf
feature/armazenamento-arquivos
feature/historico-verificacoes
feature/geracao-relatorios
feature/paginacao-receitas
feature/controle-permissoes
```

### Frontend – Angular

```text
feature/tela-login
feature/tela-cadastro-receitas
feature/tela-consulta-receitas
feature/tela-dashboard
feature/tela-administrativa
feature/componente-upload-arquivos
feature/componente-validacao-receitas
feature/servico-autenticacao
feature/servico-receitas
feature/guard-autenticacao
feature/interceptor-jwt
feature/gerenciamento-rotas
feature/layout-responsivo
feature/notificacoes-usuario
```

### Inteligência artificial e detecção de fraudes

```text
feature/deteccao-fraudes
feature/analise-documentos-ia
feature/classificacao-receitas
feature/identificacao-inconsistencias
feature/analise-imagens
feature/reconhecimento-ocr
feature/deteccao-falsificacoes
feature/validacao-assinaturas
feature/analise-dados-receitas
feature/integracao-modelo-ia
```

### Segurança da aplicação

```text
feature/seguranca-autenticacao
feature/controle-acesso
feature/criptografia-documentos
feature/auditoria-operacoes
feature/validacao-entradas
feature/protecao-endpoints
feature/gerenciamento-permissoes
feature/protecao-dados-sensiveis
```

### Banco de dados

```text
feature/modelagem-banco-dados
feature/migracao-flyway
feature/tabela-receitas
feature/tabela-usuarios
feature/tabela-historico
feature/relacionamentos-entidades
feature/indices-consultas
```

### Correção de erros

```text
bugfix/validacao-receita-azul
bugfix/validacao-receita-amarela
bugfix/validacao-receita-branca
bugfix/erro-cadastro-usuario
bugfix/erro-autenticacao-jwt
bugfix/processamento-pdf
bugfix/upload-documentos
bugfix/consulta-receitas
bugfix/validacao-cpf
bugfix/controle-permissoes
```

### Correções urgentes

```text
hotfix/vulnerabilidade-autenticacao
hotfix/falha-controle-acesso
hotfix/exposicao-dados-sensiveis
hotfix/erro-processamento-documentos
hotfix/falha-validacao-receitas
```

### Documentação

```text
docs/readme
docs/contributing
docs/arquitetura
docs/api-rest
docs/endpoints
docs/banco-dados
docs/seguranca
docs/autenticacao-jwt
docs/instalacao
docs/configuracao
docs/tecnologias
docs/funcionalidades
docs/integracao-ia
docs/testes
```

### Refatoração

```text
refactor/estrutura-pacotes
refactor/camada-services
refactor/camada-controllers
refactor/camada-repositories
refactor/estrutura-dtos
refactor/tratamento-excecoes
refactor/validacao-receitas
refactor/componentes-angular
```

### Testes

```text
test/receitas-service
test/receitas-controller
test/usuarios-service
test/autenticacao-service
test/receitas-repository
test/validacao-documentos
test/deteccao-fraudes
test/integracao-api
```

### Manutenção e infraestrutura

```text
chore/atualizar-dependencias
chore/configurar-ambiente
chore/organizar-repositorio
build/configurar-maven
build/atualizar-java
ci/configurar-pipeline
ci/executar-testes-automaticos
ci/verificar-pull-requests
```

---

# 4. Regras para nomenclatura de branches

Todas as branches devem seguir estas regras:

1. Utilizar letras minúsculas.
2. Separar palavras com hífen (`-`).
3. Não utilizar espaços.
4. Não utilizar acentos ou caracteres especiais.
5. Utilizar prefixos padronizados.
6. Criar nomes objetivos e descritivos.
7. Evitar nomes genéricos como `teste`, `ajustes`, `branch1` ou `minha-branch`.
8. Criar uma branch específica para cada funcionalidade ou correção.

**Exemplos incorretos:**

```text
Feature/Cadastro Receitas
nova-funcionalidade
Teste
branch1
AJUSTES-API
```

**Exemplos corretos:**

```text
feature/cadastro-receitas
feature/validacao-documentos
test/receitas-service
docs/atualizar-readme
fix/correcao-autenticacao
```

---

# 5. Branches principais

O projeto pode utilizar o seguinte modelo de organização:

| Branch | Finalidade |
|---|---|
| `main` | Versão estável e principal do projeto |
| `develop` | Integração das funcionalidades em desenvolvimento |

As branches de trabalho devem ser criadas a partir da branch de integração adotada pela equipe.

**Fluxo recomendado:**

```text
main
  |
  develop
  |
  +-- feature/cadastro-receitas
  |
  +-- feature/deteccao-fraudes
  |
  +-- bugfix/validacao-receitas
  |
  +-- docs/atualizar-readme
  |
  +-- test/receitas-service
```

Se a equipe não utilizar `develop`, as branches de trabalho devem partir da `main` e retornar por Pull Request para ela.

---

# 6. Fluxo de desenvolvimento Git

## 6.1. Atualizar a branch principal

```bash
git switch develop
git pull origin develop
```

## 6.2. Criar uma branch

Exemplo de nova funcionalidade:

```bash
git switch -c feature/cadastro-receitas
```

Exemplo de documentação:

```bash
git switch -c docs/contributing
```

Exemplo de correção:

```bash
git switch -c bugfix/validacao-receitas
```

## 6.3. Verificar alterações

```bash
git status
```

## 6.4. Adicionar arquivos

```bash
git add .
```

Também é possível adicionar arquivos específicos:

```bash
git add README.md
git add CONTRIBUTING.md
```

## 6.5. Criar commit

```bash
git commit -m "feat(receitas): implementar cadastro de receitas"
```

## 6.6. Enviar branch para o GitHub

```bash
git push -u origin feature/cadastro-receitas
```

## 6.7. Criar Pull Request

Após enviar a branch:

1. Acessar o repositório no GitHub.
2. Criar um Pull Request.
3. Selecionar a branch de destino.
4. Descrever as alterações realizadas.
5. Solicitar revisão de outro integrante.
6. Verificar os testes e as aprovações necessárias.
7. Realizar o merge conforme as regras do repositório.

---

# 7. Padrão de Pull Requests

Todos os Pull Requests devem apresentar uma descrição clara das alterações realizadas.

## 7.1. Modelo de título

```text
feat(receitas): implementar cadastro de receitas médicas
```

## 7.2. Modelo de descrição

```markdown
## Descrição

Implementação do cadastro de receitas médicas no SysProtect.

## Alterações realizadas

- Criado endpoint para cadastro de receitas.
- Implementadas validações dos dados.
- Adicionado tratamento de exceções.
- Integrada a camada de persistência.

## Testes realizados

- [x] Testes unitários.
- [x] Testes de integração.
- [x] Compilação do projeto.
- [ ] Revisão de código.

## Tipo de alteração

- [x] Nova funcionalidade.
- [ ] Correção de erro.
- [ ] Documentação.
- [ ] Refatoração.
- [ ] Testes.

## Observações

Informações adicionais sobre a implementação.
```

---

# 8. Boas práticas para trabalho em equipe

- Não realizar commits diretamente na `main`.
- Criar branches específicas para cada atividade.
- Manter as branches atualizadas.
- Utilizar mensagens de commit padronizadas.
- Evitar commits muito grandes.
- Não misturar funcionalidades diferentes em um mesmo commit.
- Executar os testes antes de enviar alterações.
- Revisar o código antes de abrir Pull Requests.
- Resolver conflitos antes de finalizar o merge.
- Não enviar senhas, tokens ou credenciais ao GitHub.
- Respeitar as regras de proteção das branches.
- Solicitar revisão de código de outro integrante da equipe.

---

# 9. Exemplo completo de contribuição

Exemplo de implementação da funcionalidade de detecção de fraudes em receitas médicas.

**1. Atualizar a branch de integração:**

```bash
git switch develop
git pull origin develop
```

**2. Criar a branch:**

```bash
git switch -c feature/deteccao-fraudes
```

**3. Implementar a funcionalidade e executar os testes.**

**4. Criar o commit:**

```bash
git add .
git commit -m "feat(fraudes): implementar detecção de fraudes em receitas"
```

**5. Enviar a branch:**

```bash
git push -u origin feature/deteccao-fraudes
```

**6. Abrir um Pull Request:**

```text
feature/deteccao-fraudes -> develop
```

**7. Aguardar a revisão e aprovação dos integrantes.**

**8. Realizar o merge conforme as regras do repositório.**

---

# 10. Referências oficiais

- [Conventional Commits 1.0.0](https://www.conventionalcommits.org/pt-br/v1.0.0/)
- [Git Documentation](https://git-scm.com/doc)
- [GitHub Flow](https://docs.github.com/en/get-started/using-github/github-flow)
- [GitHub Pull Requests](https://docs.github.com/en/pull-requests)
- [GitHub Branches](https://docs.github.com/en/pull-requests/collaborating-with-pull-requests/proposing-changes-to-your-work-with-pull-requests/about-branches)

---

**SysProtect – Prevenção e Detecção de Fraudes em Receitas Médicas**

*Contribua com organização, qualidade, colaboração e segurança.*

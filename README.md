[![Docker Publish](https://github.com/maratona-dev/SysProtectReceitasMedicas/actions/workflows/docker-publish.yml/badge.svg)](https://github.com/maratona-dev/SysProtectReceitasMedicas/actions/workflows/docker-publish.yml)

# SysProtectReceitasMedicas – Prevenção e Detecção de Fraudes

O **SysProtectReceitasMedicas** é um projeto voltado à prevenção e detecção de fraudes em receitas médicas controladas, abrangendo documentos físicos e digitais, com foco especial nas receitas físicas dos tipos **branca, amarela e azul**.

A solução tem como objetivo identificar possíveis irregularidades, inconsistências e indícios de falsificação, aumentando a segurança, a confiabilidade e a integridade do processo de validação desses documentos.

---

## Informações do Projeto

| Informação | Valor |
|---|---|
| Nome do projeto | `SysProtectReceitasMedicas` |
| Package principal | `com.sysprotect.receitasmedicas` |
| Backend | Java 25 + Spring Boot |
| Frontend | Angular 22 |
| Banco de dados | PostgreSQL |
| API | REST |

---

## Arquitetura e Tecnologias

O sistema será desenvolvido utilizando uma arquitetura **Full Stack**, com tecnologias modernas para desenvolvimento, persistência de dados, inteligência artificial, segurança e infraestrutura.

### Backend e API

| Tecnologia | Utilização |
|---|---|
| Java 25 | Linguagem de programação |
| Spring Boot | Framework principal do backend |
| Spring Web | Desenvolvimento da API REST |
| Spring Data JPA | Persistência de dados |
| Hibernate | ORM para comunicação com o banco de dados |
| Spring Validation | Validação dos dados utilizando Bean Validation |
| Spring AI | Integração com recursos de Inteligência Artificial |
| Google Gemini API | Análise e processamento de informações utilizando IA |
| Apache Maven | Gerenciamento de dependências e build do projeto |

### Banco de Dados e Infraestrutura

| Tecnologia | Utilização |
|---|---|
| PostgreSQL | Banco de dados relacional |
| Flyway | Versionamento e controle das migrations |
| Docker | Containerização das aplicações |
| Docker Compose | Orquestração dos containers |

### Frontend

| Tecnologia | Utilização |
|---|---|
| Angular 22 | Framework para desenvolvimento da aplicação frontend |
| TypeScript | Linguagem utilizada no frontend |
| HTTP Client | Comunicação entre Angular e API REST |
| API REST | Integração entre frontend e backend |

### Segurança e Autenticação

| Tecnologia / Recurso | Utilização |
|---|---|
| Spring Security | Autenticação e autorização |
| JWT | Autenticação baseada em tokens |
| RBAC | Controle de acesso baseado em papéis |
| ADMIN | Perfil administrativo |
| USER | Perfil de usuário |
| 2FA | Autenticação de dois fatores |
| TOTP | Geração de códigos temporários de autenticação |
| Google Authenticator | Aplicativo autenticador |
| Microsoft Authenticator | Aplicativo autenticador |

---

## Arquitetura da Aplicação

A arquitetura do **SysProtectReceitasMedicas** será organizada em camadas:

| Camada | Responsabilidade | Tecnologia |
|---|---|---|
| Frontend | Interface de usuário | Angular 22 |
| API | Comunicação entre frontend e backend | Spring Web / REST |
| Controller | Recebimento das requisições HTTP | Spring Boot |
| Service | Regras de negócio | Java / Spring |
| Repository | Acesso aos dados | Spring Data JPA |
| Database | Persistência das informações | PostgreSQL |
| Security | Autenticação e autorização | Spring Security / JWT |
| AI | Análise inteligente dos documentos | Spring AI / Gemini |
| Infrastructure | Execução e infraestrutura | Docker / Docker Compose |

---

## Inteligência Artificial e Prevenção de Fraudes

O **SysProtectReceitasMedicas** contará com integração de inteligência artificial por meio do **Spring AI** e **Google Gemini**, permitindo analisar documentos e identificar possíveis características suspeitas.

### Funcionalidades previstas

| Funcionalidade | Descrição |
|---|---|
| Análise de imagens | Análise de imagens de receitas médicas físicas |
| OCR | Extração de informações presentes nos documentos |
| Visão computacional | Identificação de características visuais da receita |
| Análise de inconsistências | Identificação de informações divergentes ou suspeitas |
| Detecção de alterações | Apoio na identificação de possíveis alterações no documento |
| Detecção de falsificação | Identificação de possíveis indícios de falsificação |
| Regras de negócio | Cruzamento das informações extraídas com regras previamente definidas |
| Indicadores | Geração de indicadores relacionados ao risco de fraude |
| Relatórios | Geração de informações para auxiliar a análise dos casos |

A inteligência artificial atuará como **mecanismo de apoio à análise**, enquanto as regras de negócio e a validação humana serão utilizadas para a avaliação dos casos suspeitos.

---

## Tipos de Receitas Analisadas

O sistema terá como foco receitas médicas controladas, incluindo:

| Tipo de Receita | Característica |
|---|---|
| Receita Branca | Receitas de controle especial |
| Receita Amarela | Receitas relacionadas a medicamentos sujeitos a controle especial |
| Receita Azul | Receitas relacionadas a medicamentos sujeitos a controle especial |

> **Observação:** as regras específicas de cada tipo de receita deverão ser implementadas conforme a legislação e as normas regulatórias aplicáveis.

---

## Segurança e Controle de Acesso

O sistema contará com diferentes níveis de permissão.

### Perfis de Usuário

| Perfil | Permissões |
|---|---|
| `ADMIN` | Gerenciamento de usuários |
| `ADMIN` | Configuração do sistema |
| `ADMIN` | Gerenciamento das regras de validação |
| `ADMIN` | Monitoramento das operações |
| `USER` | Análise de receitas |
| `USER` | Consulta de receitas |
| `USER` | Validação de receitas |
| `USER` | Acesso às funcionalidades conforme suas permissões |

### Autenticação

| Mecanismo | Descrição |
|---|---|
| JWT | Token utilizado para autenticação das requisições |
| Spring Security | Gerenciamento de autenticação e autorização |
| RBAC | Controle de acesso baseado em papéis |
| 2FA | Segunda camada de autenticação |
| TOTP | Código temporário utilizado na autenticação de dois fatores |
| Google Authenticator | Aplicativo compatível com TOTP |
| Microsoft Authenticator | Aplicativo compatível com TOTP |

---

## Fluxo de Análise de uma Receita

O fluxo principal da aplicação poderá ser organizado da seguinte maneira:

| Etapa | Processo |
|---:|---|
| 1 | Usuário realiza login |
| 2 | Sistema valida as credenciais |
| 3 | Sistema solicita o segundo fator de autenticação, quando habilitado |
| 4 | Usuário envia a receita para análise |
| 5 | API recebe o documento |
| 6 | Sistema realiza validações iniciais |
| 7 | OCR realiza a extração das informações |
| 8 | Inteligência artificial analisa o documento |
| 9 | Sistema aplica as regras de negócio |
| 10 | Informações são cruzadas e avaliadas |
| 11 | Sistema calcula indicadores de possível fraude |
| 12 | Resultado da análise é apresentado ao usuário |
| 13 | Caso suspeito pode ser encaminhado para validação humana |
| 14 | Resultado da análise é armazenado no PostgreSQL |

---

## Infraestrutura

A infraestrutura será organizada utilizando **Docker** e **Docker Compose**, facilitando a configuração, execução e manutenção dos serviços.

### Containers

| Container | Responsabilidade |
|---|---|
| `sysprotect-receitas-medicas-api` | API REST desenvolvida com Spring Boot |
| `sysprotect-receitas-medicas-frontend` | Aplicação Angular |
| `sysprotect-receitas-medicas-db` | Banco de dados PostgreSQL |

### Serviços

| Serviço | Tecnologia | Porta |
|---|---|---:|
| Frontend | Angular | `4200` |
| Backend | Spring Boot | `8080` |
| PostgreSQL | PostgreSQL | `5432` |

> As portas são uma sugestão para o ambiente de desenvolvimento e podem ser alteradas conforme a configuração do projeto.

---

## Banco de Dados

O **PostgreSQL** será utilizado como banco de dados relacional.

O **Flyway** será responsável pelo controle de versão das migrations, garantindo a evolução estruturada e consistente do esquema do banco de dados.

### Tecnologias

| Tecnologia | Responsabilidade |
|---|---|
| PostgreSQL | Armazenamento dos dados |
| Spring Data JPA | Abstração para acesso aos dados |
| Hibernate | Mapeamento objeto-relacional |
| Flyway | Controle de versão do banco |
| SQL | Criação e alteração das estruturas |

---

## Principais Dados do Sistema

Uma possível organização das informações do sistema:

| Entidade | Responsabilidade |
|---|---|
| Usuário | Armazenamento dos usuários do sistema |
| Perfil | Controle dos papéis e permissões |
| Receita | Dados principais da receita médica |
| Paciente | Informações relacionadas ao paciente |
| Médico | Informações relacionadas ao profissional |
| Medicamento | Informações dos medicamentos prescritos |
| Análise | Resultado da análise realizada pelo sistema |
| Fraude | Registro de possíveis indícios de fraude |
| Regra | Regras utilizadas na validação |
| Auditoria | Registro das operações realizadas no sistema |

---

## Indicadores de Fraude

O sistema poderá utilizar diferentes indicadores para auxiliar na identificação de situações suspeitas.

| Indicador | Descrição |
|---|---|
| Dados inconsistentes | Informações divergentes entre os campos da receita |
| Informações ausentes | Campos obrigatórios não preenchidos |
| Alteração visual | Possíveis alterações identificadas na imagem |
| Dados suspeitos | Informações que não correspondem às regras esperadas |
| Divergência de informações | Dados conflitantes entre diferentes fontes |
| Padrão suspeito | Identificação de padrões associados a possíveis fraudes |
| Resultado da IA | Classificação ou indicação gerada pelo modelo de IA |
| Regra violada | Regra de negócio identificada como inconsistente |

---

## Resultado da Análise

O resultado da análise poderá ser apresentado utilizando diferentes níveis de classificação:

| Classificação | Descrição |
|---|---|
| `APROVADA` | Receita sem inconsistências identificadas |
| `ATENÇÃO` | Receita apresenta alguma inconsistência que necessita de avaliação |
| `SUSPEITA` | Receita apresenta indicadores de possível fraude |
| `FRAUDE_CONFIRMADA` | Caso confirmado após análise e validação humana |

> A classificação final de fraude deve considerar as regras de negócio, evidências disponíveis e validação humana quando necessária.

---

## Objetivo do Projeto

Desenvolver uma plataforma **segura, escalável e confiável**, capaz de auxiliar na identificação e prevenção de fraudes em receitas médicas controladas.

O projeto combinará:

| Recurso | Objetivo |
|---|---|
| Validações automatizadas | Identificar inconsistências automaticamente |
| Inteligência Artificial | Auxiliar na análise dos documentos |
| OCR | Extrair informações das receitas |
| Regras de negócio | Validar informações conforme critérios definidos |
| Spring Security | Proteger a aplicação |
| JWT | Implementar autenticação baseada em tokens |
| 2FA / TOTP | Adicionar uma camada adicional de segurança |
| RBAC | Controlar permissões dos usuários |
| PostgreSQL | Armazenar os dados |
| Flyway | Controlar a evolução do banco |
| Docker | Padronizar o ambiente |
| Angular | Disponibilizar a interface web |
| Spring Boot | Desenvolver a API REST |

---

## Stack Tecnológica

| Categoria | Tecnologia |
|---|---|
| Linguagem Backend | Java 25 |
| Backend | Spring Boot |
| API | Spring Web / REST |
| Persistência | Spring Data JPA |
| ORM | Hibernate |
| Validação | Spring Validation |
| Inteligência Artificial | Spring AI |
| IA Generativa | Google Gemini |
| Frontend | Angular 22 |
| Linguagem Frontend | TypeScript |
| Banco de Dados | PostgreSQL |
| Database Migration | Flyway |
| Segurança | Spring Security |
| Autenticação | JWT |
| Autorização | RBAC |
| Segundo Fator | TOTP / 2FA |
| Build | Apache Maven |
| Containerização | Docker |
| Orquestração | Docker Compose |

---

## Objetivo Final

O **SysProtectReceitasMedicas** tem como objetivo fornecer uma plataforma tecnológica para **prevenção, detecção e análise de possíveis fraudes em receitas médicas controladas**, utilizando uma combinação de:

**Java + Spring Boot + Angular + PostgreSQL + Flyway + Spring Security + JWT + 2FA + Docker + Spring AI + Google Gemini.**

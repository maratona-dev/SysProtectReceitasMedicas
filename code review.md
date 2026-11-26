# CODE REVIEW – Guia de Padrões, Qualidade e Desenvolvimento de Software

**Projeto:** Java + Spring Boot + Angular 22  
**Equipe:** 4 desenvolvedores (nível estágio)  
**Repositório:** GitHub  
**CI/CD:** GitHub Actions  
**Backend:** Java + Spring Boot + Maven  
**Frontend:** Angular 22 + TypeScript  
**Banco de dados:** PostgreSQL  
**Testes Backend:** JUnit + Mockito + AssertJ  
**Cobertura mínima:** 80% – JaCoCo  
**Idioma do código:** Inglês (recomendado)  
**Idioma da documentação:** Português  

---

# 1. Objetivo do documento

Este documento estabelece as regras obrigatórias de desenvolvimento, organização, testes, revisão e integração de código para todos os integrantes da equipe.

Como a equipe é composta por desenvolvedores em nível de estágio, é fundamental estabelecer padrões que incentivem boas práticas desde o início do desenvolvimento profissional.

O objetivo é desenvolver software com qualidade, legibilidade, segurança, organização e facilidade de manutenção.

Todos os integrantes deverão aprender a:

- Escrever código limpo e organizado.
- Aplicar os princípios SOLID.
- Utilizar corretamente Java e Spring Boot.
- Desenvolver aplicações Angular.
- Criar testes automatizados.
- Trabalhar com Git e GitHub.
- Realizar Code Review.
- Identificar problemas de arquitetura.
- Documentar decisões técnicas.
- Trabalhar em equipe utilizando metodologias profissionais.

**Regra principal: nenhum código será integrado à branch principal sem passar pelo processo completo de revisão e aprovação.**

# 2. Regras obrigatórias da equipe

Todos os integrantes deverão seguir as mesmas regras.

1. Nenhum desenvolvedor poderá enviar código diretamente para `main`.
2. Toda funcionalidade deverá ser desenvolvida em uma branch própria.
3. Todo desenvolvimento deverá gerar um Pull Request.
4. Todo PR deverá ser revisado pelos outros três integrantes.
5. O autor deverá realizar seu próprio checklist de revisão.
6. Os três revisores deverão registrar suas aprovações no GitHub.
7. Todos os comentários deverão ser resolvidos.
8. Todos os testes obrigatórios deverão passar.
9. O pipeline GitHub Actions deverá finalizar com sucesso.
10. Nenhuma aprovação poderá ser concedida sem uma análise real do código.

## 2.1. Participação dos quatro integrantes

<escape>O</escape> processo deverá envolver os quatro integrantes:

| Integrante | Responsabilidade |
|---|---|
| Desenvolvedor A | Implementação e autorrevisão |
| Desenvolvedor B | Revisão técnica |
| Desenvolvedor C | Revisão técnica |
| Desenvolvedor D | Revisão técnica |

**Critério obrigatório:** 100% da equipe deverá participar do processo de revisão.

No GitHub, configurar três aprovações obrigatórias, pois o autor não pode aprovar o próprio Pull Request.

A autorrevisão deverá ser registrada no checklist do PR.

# 3. Fundamentos de qualidade de código

Antes de revisar uma funcionalidade, todos deverão compreender os seguintes conceitos.

## 3.1. Clean Code – Código limpo

O código deverá ser fácil de ler, entender, testar e modificar.

Um desenvolvedor que não participou da implementação deverá conseguir compreender o funcionamento do código sem precisar perguntar ao autor sobre cada método.

Regras:

- Evitar código desnecessariamente complexo.
- Evitar métodos muito extensos.
- Evitar classes com muitas responsabilidades.
- Evitar duplicação de código.
- Evitar nomes genéricos.
- Evitar comentários que apenas repetem o código.
- Evitar números e textos mágicos.
- Evitar condicionais excessivamente complexas.
- Remover códigos não utilizados.
- Manter uma formatação consistente.

## 3.2. Nomes de variáveis

Os nomes devem explicar o que a variável representa.

**Não permitido:**

```java
String x;
String a;
int n;
double v;
List<Object> lista;
```

**Padrão esperado:**

```java
String patientName;
String prescriptionNumber;
int medicationQuantity;
BigDecimal medicationPrice;
List<Medication> medications;
```

Regras:

- Utilizar camelCase.
- Utilizar nomes descritivos.
- Evitar abreviações desnecessárias.
- Não utilizar nomes que dependam de comentários para serem compreendidos.
- Booleanos devem expressar condições.

Exemplo:

```java
boolean isActive;
boolean hasPermission;
boolean isPrescriptionValid;
```

## 3.3. Nomes de métodos

Os métodos deverão indicar claramente a ação realizada.

**Não permitido:**

```java
public void process() { }

public void execute() { }

public void verify() { }
```

**Padrão esperado:**

```java
public void registerPatient() { }

public void validatePrescription() { }

public void calculateMedicationPrice() { }
```

Regras:

- Utilizar verbos.
- Utilizar camelCase.
- Evitar nomes genéricos.
- Não criar métodos que executem diversas funcionalidades independentes.
- Não utilizar nomes enganosos.
- O nome deve corresponder ao comportamento real.

## 3.4. Nomes de classes

Utilizar PascalCase.

Exemplos:

```text
Patient
PatientController
PatientService
PatientRepository
PatientRequest
PatientResponse
PrescriptionValidator
```

Não criar classes com nomes genéricos:

```text
Manager
Helper
Utils
Processor
GeneralService
```

Esses nomes não são proibidos em qualquer situação, mas precisam apresentar uma justificativa técnica clara.

# 4. Princípios SOLID

Todos os desenvolvedores deverão aplicar os cinco princípios SOLID.

## S – Single Responsibility Principle

Uma classe deve possuir uma única responsabilidade principal.

Uma função deve executar uma tarefa bem definida.

**Exemplo incorreto:**

Uma classe `PatientService` que:

- Cadastra pacientes.
- Envia e-mails.
- Gera relatórios.
- Valida receitas.
- Processa pagamentos.

**Exemplo correto:**

```text
PatientService
EmailService
ReportService
PrescriptionService
PaymentService
```

Pergunta obrigatória durante o Code Review:

"Esta classe possui mais de uma responsabilidade que poderia ser separada?"

## O – Open/Closed Principle

As classes devem permitir extensão sem exigir modificações constantes em funcionalidades já existentes.

Evitar estruturas condicionais gigantescas para adicionar novos comportamentos.

## L – Liskov Substitution Principle

Uma implementação deve respeitar o comportamento esperado de sua classe ou interface base.

Não criar implementações que quebrem o funcionamento esperado da abstração.

## I – Interface Segregation Principle

Evitar interfaces com métodos desnecessários para suas implementações.

Criar contratos menores e específicos quando necessário.

## D – Dependency Inversion Principle

As classes devem depender de abstrações, reduzindo o acoplamento.

No Spring Boot, utilizar injeção de dependências, preferencialmente por construtor.

# 5. Backend – Java e Spring Boot

## 5.1. Organização da arquitetura

O projeto deverá respeitar a separação de responsabilidades.

Estrutura de referência:

```text
src/main/java/br/com/projeto/
│
├── controller/
│   └── PatientController.java
│
├── service/
│   └── PatientService.java
│
├── repository/
│   └── PatientRepository.java
│
├── entity/
│   └── Patient.java
│
├── dto/
│   ├── request/
│   │   └── PatientRequest.java
│   └── response/
│       └── PatientResponse.java
│
├── mapper/
│   └── PatientMapper.java
│
├── exception/
│   ├── GlobalExceptionHandler.java
│   └── PatientNotFoundException.java
│
└── config/
```

A estrutura poderá ser adaptada conforme a arquitetura do projeto, desde que as responsabilidades permaneçam separadas.

## 5.2. Controllers

Responsabilidade: receber requisições HTTP e retornar respostas HTTP.

Checklist:

- [ ] Utiliza `@RestController`.
- [ ] Possui mapeamento adequado de endpoints.
- [ ] Não contém regras de negócio.
- [ ] Não acessa o banco diretamente.
- [ ] Utiliza DTOs para entrada e saída.
- [ ] Utiliza corretamente `@RequestBody`.
- [ ] Utiliza corretamente `@PathVariable`.
- [ ] Utiliza corretamente `@RequestParam`.
- [ ] Retorna códigos HTTP apropriados.
- [ ] Possui documentação dos endpoints quando aplicável.

## 5.3. Services

Responsabilidade: implementar as regras de negócio.

Checklist:

- [ ] Contém regras de negócio.
- [ ] Não possui código de comunicação HTTP.
- [ ] Não executa responsabilidades de Controller.
- [ ] Não mistura diferentes domínios.
- [ ] Utiliza dependências injetadas por construtor.
- [ ] Possui tratamento adequado de exceções.
- [ ] Utiliza transações quando necessário.
- [ ] Possui testes unitários.

## 5.4. Repositories

Responsabilidade: acesso e persistência dos dados.

Checklist:

- [ ] Utiliza Spring Data JPA adequadamente.
- [ ] Não possui regras de negócio.
- [ ] Evita consultas desnecessárias.
- [ ] Evita problemas de N+1 queries.
- [ ] Utiliza paginação quando necessário.
- [ ] Possui consultas com nomes claros.
- [ ] Não retorna dados desnecessários.

## 5.5. Entidades e DTOs

Checklist:

- [ ] Entidades representam corretamente o domínio.
- [ ] Relacionamentos JPA estão configurados corretamente.
- [ ] Não existem relacionamentos desnecessários.
- [ ] DTOs não expõem informações sensíveis.
- [ ] Validações de entrada são aplicadas.
- [ ] Não existe exposição desnecessária de entidades JPA diretamente na API.

## 5.6. Tratamento de exceções

Não utilizar exceções genéricas para esconder problemas.

**Incorreto:**

```java
try {
    patientService.register(patient);
} catch (Exception exception) {
    System.out.println("Error");
}
```

**Padrão esperado:**

- Criar exceções específicas quando necessário.
- Utilizar `@RestControllerAdvice`.
- Retornar mensagens claras.
- Utilizar códigos HTTP adequados.
- Registrar erros técnicos sem expor informações sensíveis.
- Não ignorar exceções.

# 6. Testes automatizados – Backend

Os testes são obrigatórios e fazem parte da implementação.

Não considerar uma funcionalidade concluída sem seus testes correspondentes.

Tecnologias:

- JUnit 6.
- Mockito.
- AssertJ.
- JaCoCo.
- Maven.

## 6.1. Nomenclatura dos testes

O nome deve explicar o cenário e o comportamento esperado.

**Incorreto:**

```java
@Test
void test1() {}

@Test
void testPatient() {}

@Test
void execute() {}
```

**Correto:**

```java
@Test
void shouldRegisterPatientSuccessfully() {}

@Test
void shouldThrowExceptionWhenPatientDoesNotExist() {}

@Test
void shouldRejectPrescriptionWithInvalidMedication() {}
```

O padrão deverá ser consistente em todo o projeto.

## 6.2. Estrutura dos testes

Utilizar o padrão Arrange, Act, Assert.

```java
@Test
void shouldRegisterPatientSuccessfully() {

    // Arrange
    Patient patient = new Patient();

    // Act
    Patient result = patientService.register(patient);

    // Assert
    Assertions.assertThat(result).isNotNull();
}
```

Regras:

- [ ] Preparar os dados do teste.
- [ ] Executar apenas a ação que está sendo testada.
- [ ] Verificar o resultado.
- [ ] Não depender de outros testes.
- [ ] Não utilizar dados reais de produção.
- [ ] Não testar somente se o objeto não é nulo.
- [ ] Validar os comportamentos esperados.

## 6.3. Cenários obrigatórios

Para cada funcionalidade, avaliar:

- Cenário de sucesso.
- Dados inválidos.
- Dados obrigatórios ausentes.
- Registro inexistente.
- Registro duplicado.
- Erros de negócio.
- Falhas de dependências externas, quando aplicável.
- Limites e condições especiais.

Nem todos os cenários se aplicam a todas as funções. O desenvolvedor deverá justificar os cenários relevantes.

## 6.4. JaCoCo – Cobertura mínima de 80%

<escape>O</escape> projeto deverá possuir cobertura mínima de 80% de linhas de código.

Comando:

```bash
mvn clean verify
```

Critérios:

- [ ] Cobertura mínima de 80%.
- [ ] JaCoCo configurado no Maven.
- [ ] Build reprovado quando a cobertura ficar abaixo do mínimo.
- [ ] Relatório gerado automaticamente.
- [ ] Código novo coberto por testes adequados.
- [ ] Exclusões justificadas tecnicamente.

Relatório:

```text
target/site/jacoco/index.html
```

**Atenção:** não criar testes artificiais somente para aumentar o percentual de cobertura.

Cobertura não significa qualidade automática. Os testes precisam validar comportamentos reais.

# 7. Frontend – Angular 22

## 7.1. Organização

O frontend deverá possuir separação clara entre componentes, serviços, modelos e funcionalidades.

Estrutura de referência:

```text
src/app/
│
├── core/
│   ├── interceptors/
│   ├── guards/
│   └── services/
│
├── shared/
│   ├── components/
│   ├── pipes/
│   └── directives/
│
├── features/
│   ├── patients/
│   │   ├── components/
│   │   ├── services/
│   │   └── models/
│   │
│   └── prescriptions/
│       ├── components/
│       ├── services/
│       └── models/
│
└── app.config.ts
```

## 7.2. Componentes

Checklist:

- [ ] Cada componente possui responsabilidade definida.
- [ ] Não concentra todas as funcionalidades do sistema.
- [ ] Não possui regras complexas de negócio.
- [ ] Utiliza componentes reutilizáveis quando necessário.
- [ ] Possui nomes claros.
- [ ] Não possui código duplicado.
- [ ] Trata estados de carregamento.
- [ ] Trata mensagens de erro.
- [ ] Utiliza corretamente o ciclo de vida do Angular.

## 7.3. TypeScript

**Não permitido:**

```typescript
const x: any = {};
const a = '';
```

**Padrão esperado:**

```typescript
interface Patient {
    id: number;
    name: string;
    email: string;
}

const patientName: string = '';
```

Regras:

- [ ] Evitar `any`.
- [ ] Utilizar interfaces e tipos.
- [ ] Utilizar nomes descritivos.
- [ ] Evitar funções extensas.
- [ ] Evitar lógica duplicada.
- [ ] Evitar valores mágicos.
- [ ] Utilizar tipagem adequada para respostas HTTP.

## 7.4. Services e integração com Spring Boot

Os services serão responsáveis pela comunicação com a API.

Checklist:

- [ ] Utilizar `HttpClient`.
- [ ] Centralizar a comunicação HTTP.
- [ ] Não realizar chamadas HTTP diretamente em vários componentes.
- [ ] Utilizar interfaces para os dados.
- [ ] Tratar erros HTTP.
- [ ] Configurar URLs por ambiente.
- [ ] Utilizar interceptors quando necessário.
- [ ] Não expor tokens ou credenciais.
- [ ] Não duplicar endpoints.
- [ ] Manter compatibilidade com os contratos da API.

## 7.5. Testes Angular

Checklist:

- [ ] Testes dos componentes.
- [ ] Testes dos services.
- [ ] Testes dos formulários.
- [ ] Testes de validação.
- [ ] Testes de cenários de erro.
- [ ] Build de produção aprovado.
- [ ] Lint aprovado.

Comandos de referência:

```bash
npm ci
npm run lint
npm test -- --watch=false
npm run build
```

Os comandos deverão corresponder aos scripts definidos no `package.json` do projeto.

# 8. Integração Angular + Spring Boot

A comunicação deverá ocorrer por API REST.

Regras:

- Utilizar JSON como formato de comunicação.
- Utilizar HTTP corretamente.
- Respeitar os contratos dos DTOs.
- Utilizar códigos HTTP adequados.
- Configurar CORS no backend.
- Documentar endpoints.
- Tratar erros de comunicação.
- Não compartilhar regras internas do backend com o frontend.

Exemplo de integração:

```text
Angular 22
    |
    | HTTP / JSON
    |
Spring Boot REST API
    |
    | JPA
    |
PostgreSQL
```

Exemplo de endpoint:

```http
POST /api/v1/patients
```

Request:

```json
{
  "name": "John Smith",
  "email": "john@example.com"
}
```

Response:

```json
{
  "id": 1,
  "name": "John Smith",
  "email": "john@example.com"
}
```

O frontend deverá respeitar o contrato definido pelo backend.

# 9. Banco de dados e persistência

Checklist:

- [ ] Tabelas possuem nomes claros.
- [ ] Colunas possuem nomes significativos.
- [ ] Chaves primárias corretamente definidas.
- [ ] Chaves estrangeiras corretamente definidas.
- [ ] Restrições de integridade aplicadas.
- [ ] Tipos de dados adequados.
- [ ] Índices criados quando necessários.
- [ ] Não existem dados duplicados indevidamente.
- [ ] Scripts de migração versionados.
- [ ] Migrações Flyway testadas.
- [ ] Não alterar migrações já aplicadas sem um procedimento controlado.

# 10. Segurança

Todo desenvolvedor deverá verificar:

- [ ] Nenhuma senha está no código.
- [ ] Nenhuma chave de API está no repositório.
- [ ] Nenhum token está exposto.
- [ ] Dados recebidos são validados.
- [ ] Endpoints possuem autorização adequada.
- [ ] SQL Injection é prevenido.
- [ ] Informações sensíveis não aparecem nos logs.
- [ ] CORS está configurado corretamente.
- [ ] Dependências possuem versões controladas.
- [ ] Arquivos `.env` e configurações locais não são enviados ao Git.

Problemas de segurança deverão ser tratados como bloqueadores de merge.

# 11. Git e GitHub

## 11.1. Branches

Padrão:

```text
main
develop
feature/patient-registration
feature/prescription-validation
bugfix/patient-validation
hotfix/security-fix
```

Regras:

- Não trabalhar diretamente na `main`.
- Uma funcionalidade por branch.
- Atualizar a branch antes de abrir o PR.
- Não enviar arquivos desnecessários.
- Não incluir arquivos de configuração pessoal.

## 11.2. Commits

Utilizar Conventional Commits.

Exemplos:

```text
feat: add patient registration
fix: correct prescription validation
test: add patient service tests
refactor: separate patient service responsibilities
docs: update API documentation
chore: configure GitHub Actions
```

## 11.3. Pull Request

Todo PR deverá conter:

- Título claro.
- Descrição do que foi desenvolvido.
- Motivo da alteração.
- Funcionalidades afetadas.
- Testes realizados.
- Evidências quando necessário.
- Riscos conhecidos.
- Informações importantes para os revisores.

O autor deverá realizar uma autorrevisão antes de solicitar aprovação.

# 12. GitHub Actions – CI/CD

O pipeline deverá ser executado automaticamente em Pull Requests e nas branches definidas pela equipe.

## 12.1. Backend

Etapas:

1. Checkout do repositório.
2. Configuração do JDK.
3. Configuração do Maven.
4. Compilação.
5. Execução de testes.
6. Execução do JaCoCo.
7. Validação de cobertura mínima.
8. Geração do relatório.

Comando:

```bash
mvn clean verify
```

## 12.2. Frontend

Etapas:

1. Checkout do repositório.
2. Configuração do Node.js.
3. Instalação das dependências.
4. Execução do lint.
5. Execução dos testes.
6. Build de produção.

## 12.3. Regras obrigatórias

- [ ] Pipeline executado automaticamente.
- [ ] Nenhum teste reprovado.
- [ ] JaCoCo acima ou igual a 80%.
- [ ] Build do backend aprovado.
- [ ] Build do frontend aprovado.
- [ ] Lint aprovado.
- [ ] Nenhuma etapa obrigatória ignorada.
- [ ] Relatórios disponíveis no GitHub Actions.

Um PR com pipeline reprovado não poderá ser integrado.

# 13. Configuração de proteção da branch main

Configurar as regras de proteção no GitHub.

| Configuração | Valor |
|---|---|
| Require a pull request | Ativado |
| Required approvals | 3 |
| Dismiss stale approvals | Ativado |
| Require status checks | Ativado |
| Require branch up to date | Ativado |
| Block force pushes | Ativado |
| Restrict direct pushes | Ativado |
| Require conversation resolution | Ativado |

A configuração de três aprovações corresponde aos três revisores diferentes do autor.

A quarta participação será a autorrevisão obrigatória, registrada no PR.

# 14. Como realizar um Code Review profissional

O Code Review deverá ser feito com atenção e responsabilidade.

Não aprovar um PR simplesmente porque o colega pediu.

Cada integrante deverá analisar o código, compreender a implementação e registrar os problemas encontrados.

## 14.1. O que verificar primeiro?

1. Entender o objetivo do Pull Request.
2. Verificar se a implementação resolve o problema.
3. Analisar a organização das classes.
4. Conferir os nomes de variáveis e métodos.
5. Avaliar os princípios SOLID.
6. Verificar possíveis duplicações.
7. Conferir o tratamento de erros.
8. Avaliar a segurança.
9. Analisar os testes.
10. Verificar a integração com outras funcionalidades.

## 14.2. Como comentar corretamente

Os comentários devem ser educados, objetivos e técnicos.

**Comentário inadequado:**

"Esse código está errado. Refaz."

**Comentário profissional:**

"Este método está executando duas responsabilidades: validação dos dados e persistência do paciente. Podemos separar essas responsabilidades para melhorar a organização e facilitar os testes?"

Outro exemplo:

"Esta variável possui um nome genérico. Sugiro utilizar `patientEmail`, pois representa melhor o conteúdo armazenado."

Outro exemplo:

"Este cenário ainda não possui cobertura de testes. Podemos adicionar um teste para validar o comportamento quando o paciente não existir?"

## 14.3. Tipos de comentários

| Tipo | Utilização |
|---|---|
| Bug | Comportamento incorreto |
| Security | Problema de segurança |
| Architecture | Problema arquitetural |
| SOLID | Violação de responsabilidade |
| Test | Teste ausente ou inadequado |
| Naming | Nome inadequado |
| Suggestion | Sugestão de melhoria |
| Question | Dúvida sobre a implementação |

# 15. Classificação dos problemas

| Prioridade | Descrição | Ação |
|---|---|---|
| P0 – Critical | Vulnerabilidade grave ou perda de dados | Bloquear PR |
| P1 – High | Regra de negócio incorreta ou falha funcional | Bloquear PR |
| P2 – Medium | Problema de arquitetura ou qualidade | Corrigir antes da aprovação |
| P3 – Low | Legibilidade, nomenclatura e organização | Corrigir antes da aprovação |

Nenhum problema poderá ser ignorado sem justificativa e concordância dos revisores.

# 16. Checklist obrigatório do autor

Antes de abrir um Pull Request, o desenvolvedor deverá verificar:

## Código

- [ ] Entendo completamente o código que desenvolvi.
- [ ] Os nomes das variáveis são claros.
- [ ] Os nomes dos métodos são claros.
- [ ] Os nomes das classes representam suas responsabilidades.
- [ ] Cada classe possui uma responsabilidade principal.
- [ ] Cada método possui uma funcionalidade bem definida.
- [ ] Não existe duplicação desnecessária.
- [ ] Não existem códigos não utilizados.
- [ ] Os princípios SOLID foram considerados.
- [ ] Não existem credenciais expostas.

## Testes

- [ ] Testes unitários implementados.
- [ ] Cenários positivos testados.
- [ ] Cenários negativos testados.
- [ ] Testes executados localmente.
- [ ] `mvn clean verify` aprovado.
- [ ] JaCoCo com pelo menos 80%.
- [ ] Testes Angular aprovados.
- [ ] Build Angular aprovado.

## GitHub

- [ ] Branch correta.
- [ ] Commits organizados.
- [ ] Pull Request documentado.
- [ ] Autorrevisão realizada.
- [ ] CI/CD aprovado.

# 17. Checklist obrigatório dos três revisores

Cada revisor deverá realizar sua própria análise.

- [ ] Entendi o objetivo do PR.
- [ ] Analisei as alterações.
- [ ] Verifiquei os nomes das variáveis.
- [ ] Verifiquei os nomes dos métodos.
- [ ] Verifiquei os nomes das classes.
- [ ] Analisei a separação de responsabilidades.
- [ ] Verifiquei os princípios SOLID.
- [ ] Analisei a qualidade dos testes.
- [ ] Verifiquei possíveis problemas de segurança.
- [ ] Verifiquei a integração backend/frontend.
- [ ] Verifiquei os resultados do CI/CD.
- [ ] Resolvi minhas dúvidas com o autor.
- [ ] Registrei minha aprovação somente após a revisão.

# 18. Processo obrigatório de aprovação

O fluxo deverá seguir esta ordem:

**Etapa 1 – Desenvolvimento**

O autor cria uma branch e implementa a funcionalidade.

**Etapa 2 – Autorrevisão**

O autor executa os testes e preenche o checklist.

**Etapa 3 – Pull Request**

O autor abre o PR e solicita revisão dos outros três integrantes.

**Etapa 4 – Revisão técnica**

Os três integrantes analisam o código individualmente.

**Etapa 5 – Correções**

O autor corrige os problemas identificados e responde aos comentários.

**Etapa 6 – Nova validação**

Os revisores verificam as correções e aprovam novamente quando necessário.

**Etapa 7 – CI/CD**

GitHub Actions confirma que todos os requisitos automatizados foram atendidos.

**Etapa 8 – Merge**

O PR poderá ser integrado somente quando todos os critérios forem atendidos.

# 19. Critérios para considerar uma funcionalidade concluída

Uma funcionalidade somente será considerada concluída quando:

- Código implementado.
- Código revisado.
- Responsabilidades bem definidas.
- Testes automatizados implementados.
- Testes aprovados.
- Cobertura mínima respeitada.
- Documentação atualizada quando necessária.
- Integração funcionando.
- CI/CD aprovado.
- Três aprovações registradas.
- Autorrevisão realizada.

# 20. Cultura de desenvolvimento da equipe

Todos os integrantes deverão compreender que Code Review não é uma competição e não deve ser utilizado para constranger colegas.

O objetivo é desenvolver profissionais melhores e entregar software de qualidade.

Regras de comportamento:

1. Não aprovar código sem entender o que foi implementado.
2. Não realizar críticas pessoais.
3. Explicar tecnicamente os problemas encontrados.
4. Aceitar sugestões de melhoria.
5. Perguntar quando não compreender uma implementação.
6. Compartilhar conhecimento.
7. Corrigir os próprios erros.
8. Não esconder problemas.
9. Não utilizar código de terceiros sem compreender seu funcionamento e suas licenças.
10. Priorizar a qualidade do projeto em vez da velocidade de entrega.

**Lembre-se: todos estão aprendendo, mas todos são responsáveis pela qualidade do código que entregam.**

---

# 21. Regra final

**Nenhum PR será integrado à branch principal sem a participação dos quatro integrantes, três aprovações formais no GitHub, autorrevisão, testes aprovados e CI/CD concluído com sucesso.**

O padrão de qualidade deverá ser aplicado por todos, independentemente de quem escreveu o código.

O objetivo da equipe é desenvolver software seguindo práticas utilizadas em ambientes profissionais, preparando todos os integrantes para trabalhar com projetos reais de desenvolvimento de software.

**Qualidade não é responsabilidade de uma única pessoa. É responsabilidade de toda a equipe.**

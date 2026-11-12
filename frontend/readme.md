# Angular 22 — Comandos de Desenvolvimento

## 1. Instalar as dependências

Após clonar o projeto, acesse a pasta do frontend e execute:

```bash
npm install
```

Esse comando instala todas as dependências definidas no `package.json`.

## 2. Executar em ambiente de desenvolvimento

Para iniciar a aplicação Angular:

```bash
ng serve
```

Ou utilizando o npm:

```bash
npm start
```

A aplicação ficará disponível em:

```text
http://localhost:4200
```

Para iniciar e abrir automaticamente o navegador:

```bash
ng serve --open
```

## 3. Executar em uma porta específica

Caso queira utilizar outra porta:

```bash
ng serve --port 4300
```

## 4. Executar com acesso externo

Para permitir acesso à aplicação por outros dispositivos na rede local:

```bash
ng serve --host 0.0.0.0
```

## 5. Compilar a aplicação

Para gerar os arquivos de compilação:

```bash
ng build
```

Os arquivos serão gerados no diretório `dist/`.

## 6. Compilar em modo de desenvolvimento

```bash
ng build --configuration development
```

## 7. Compilar automaticamente ao detectar alterações

Para manter a compilação atualizada durante o desenvolvimento:

```bash
ng build --watch --configuration development
```

## 8. Executar os testes

Para executar os testes unitários:

```bash
ng test
```

## 9. Verificar problemas de código

Para executar o lint, caso o projeto tenha ESLint configurado:

```bash
ng lint
```

## 10. Gerar componentes

Para criar um novo componente:

```bash
ng generate component nome-componente
```

Forma abreviada:

```bash
ng g c nome-componente
```

## 11. Gerar serviços

```bash
ng generate service nome-servico
```

Forma abreviada:

```bash
ng g s nome-servico
```

## 12. Gerar interfaces

```bash
ng generate interface models/nome-interface
```

## 13. Encerrar a aplicação

No terminal onde o Angular está executando, pressione:

```text
CTRL + C
```

---

## Resumo dos comandos mais utilizados

| Comando | Finalidade |
|---|---|
| `npm install` | Instala as dependências |
| `ng serve` | Inicia o servidor de desenvolvimento |
| `npm start` | Executa o script start |
| `ng serve --open` | Inicia e abre o navegador |
| `ng build` | Compila a aplicação |
| `ng test` | Executa os testes |
| `ng lint` | Analisa o código com ESLint |
| `ng g c nome` | Gera um componente |
| `ng g s nome` | Gera um serviço |
| `ng build --watch` | Compila automaticamente ao detectar alterações |

## Fluxo recomendado para desenvolvimento

Para o desenvolvimento diário, normalmente basta executar:

```bash
npm install
npm start
```

Acesse `http://localhost:4200` no navegador.

O Angular CLI utiliza o servidor de desenvolvimento com atualização automática durante as alterações nos arquivos.

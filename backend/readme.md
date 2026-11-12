## Configuração das variáveis de ambiente

Antes de iniciar a aplicação, é necessário criar o arquivo `.env` a partir do arquivo de exemplo `.env.example`.

**1. Copie o arquivo `.env.example` para `.env`:**

```bash
cp .env.example .env
```

No Windows (PowerShell), utilize:

```powershell
Copy-Item .env.example .env
```

**2. Configure as variáveis de ambiente**

Abra o arquivo `.env` e preencha as variáveis necessárias de acordo com o ambiente de execução.

> **Importante:** Não compartilhe o arquivo `.env` e não o envie para o GitHub, pois ele pode conter senhas, tokens e outras informações sensíveis.

**3. Execute a aplicação com Docker Compose**

Após configurar o arquivo `.env`, execute:

```bash
docker compose up -d
```

O Docker Compose utilizará as variáveis definidas no `.env` durante a inicialização dos serviços.

---


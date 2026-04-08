# Dr Marut

Dr Marut é um bot para Discord focado em consulta de regras do Pathfinder 2e. Este repositório contém a base da aplicação: Spring Boot, integração com JDA, PostgreSQL + Flyway, health checks via Actuator, CI e a estrutura inicial de documentação (ADR/specs).

## Requisitos

- JDK 21
- Maven 3.9+
- Docker Desktop ou outro runtime Docker local

## Clone e download

Clone o repositório com o submódulo PF2E já inicializado:

```bash
git clone --recurse-submodules https://github.com/Je-Carlos/discord-dr-marut-bot.git
cd discord-dr-marut-bot
```

Se você já clonou o repositório sem os submódulos, execute:

```bash
git submodule update --init --recursive
```

## Submódulo de dados PF2E

O diretório `pf2e-data/` é um submódulo Git que aponta para o repositório oficial do PF2E:

```bash
git submodule status
```

Para baixar o conteúdo mais recente do submódulo configurado neste repositório:

```bash
git submodule update --init --recursive
```

Para buscar mudanças mais novas do upstream dentro do submódulo:

```bash
git submodule update --remote --merge
```

Use `--remote --merge` apenas quando quiser avançar intencionalmente a revisão fixada do PF2E e depois comitar o novo ponteiro do submódulo neste repositório.

## Bootstrap local

1. Inicie o PostgreSQL:

```bash
docker compose up -d
```

2. Exporte as variáveis de ambiente se quiser habilitar o Discord:

```bash
set DRMARUT_DISCORD_ENABLED=true
set DRMARUT_DISCORD_TOKEN=seu-token
set DRMARUT_DISCORD_GUILD_ID=id-do-seu-servidor
```

O Discord vem desabilitado por padrão no perfil `local`.

3. Execute a aplicação:

```bash
mvn spring-boot:run
```

## Configuração local padrão

- Perfil: `local`
- JDBC URL: `jdbc:postgresql://localhost:5432/drmarut`
- Usuário: `drmarut`
- Senha: `drmarut`
- Health check: `http://localhost:8080/actuator/health`
- Info: `http://localhost:8080/actuator/info`

## Testes

O projeto utiliza:

- JUnit 5
- Spring Boot Test
- Testcontainers para testes de integração com PostgreSQL

Execute:

```bash
mvn test
```

Se o Docker não estiver disponível localmente, o teste de integração com Testcontainers é ignorado, enquanto os testes unitários e de configuração continuam rodando.

## Estrutura do projeto

- `src/main/java/com/drmarut`: bootstrap da aplicação e pacotes de features
- `src/main/resources`: perfis Spring e migrações Flyway
- `pf2e-data`: repositório de dados PF2E upstream, rastreado como submódulo
- `docs/adr`: decisões de arquitetura
- `docs/specs`: especificações de features
- `docs/product`: documentos de produto
- `docs/runbooks`: runbooks operacionais

## Secrets do GitHub

Para o GitHub Actions, o workflow está preparado para ler estes secrets do repositório ou organização:

- `DRMARUT_DISCORD_ENABLED`
- `DRMARUT_DISCORD_TOKEN`
- `DRMARUT_DISCORD_GUILD_ID`
- `DRMARUT_DB_URL`
- `DRMARUT_DB_USERNAME`
- `DRMARUT_DB_PASSWORD`

Use GitHub Secrets para CI e automação de deploy. Para execução local, continue usando variáveis de ambiente na sua máquina.

## Nota de segurança

Secrets não devem ser commitados. O arquivo legado `src/main/resources/config.properties` não faz parte do caminho de execução atual e não deve ser usado como fonte de credenciais.

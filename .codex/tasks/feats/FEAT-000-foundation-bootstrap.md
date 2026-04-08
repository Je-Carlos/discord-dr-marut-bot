# FEAT-000 Foundation Bootstrap

## Objetivo

Concentrar a base tecnica do bot: bootstrap da aplicacao, build, empacotamento, configuracao inicial e estrutura de projeto.

## Subfeatures associadas

- FEAT-000 Projeto base Spring Boot + Maven
- FEAT-001 Configuracao JDA
- FEAT-002 Persistencia PostgreSQL + Flyway
- FEAT-003 Observabilidade basica
- FEAT-004 Pipeline CI
- FEAT-005 Estrutura docs/specs/adr

## Estado atual

`parcial`

## O que existe hoje

- Projeto Maven valido em `pom.xml`.
- Java 21 configurado em `pom.xml`.
- Dependencias de JDA, SLF4J e Logback em `pom.xml`.
- Empacotamento de `jar-with-dependencies` via `maven-assembly-plugin`.
- Classe principal declarada como `discord.bot.discordDrMarutBot`.
- Estrutura minima de `src/main/java`, `src/main/resources`, `src/test/java`.
- Arquivo `.gitignore` com regras para `target/`, IDEs e `src/main/resources/config.properties`.

## Evidencias

- `pom.xml`
- `src/main/java/discord/bot/discordDrMarutBot.java`
- `src/test/java/discord/bot/discordDrMarutBotTest.java`
- `.gitignore`

## Leitura do estado real

- O projeto ainda nao usa Spring Boot, apesar de o `AGENTS.md` definir essa stack como base obrigatoria.
- O ponto de entrada `discordDrMarutBot.java` e apenas um placeholder que imprime `Hello World!`.
- O teste atual e um esqueleto JUnit 3 gerado por template e nao cobre comportamento real.
- Ainda nao existe estrutura `package by feature`.
- Ainda nao existe `docs/`, `docs/specs/` ou `docs/adr/` no repositorio.

## Lacunas para fechar esta macrofeature

- Migrar o bootstrap para Spring Boot.
- Inicializar JDA de forma real, nao apenas como dependencia.
- Introduzir configuracao tipada para runtime.
- Adicionar PostgreSQL e Flyway.
- Adicionar observabilidade basica.
- Adicionar pipeline de CI.
- Criar a estrutura de documentacao descrita no `AGENTS.md`.

## Riscos e observacoes

- O roadmap do `AGENTS.md` pressupoe uma base muito mais madura do que a que existe hoje.
- O pacote `discord.bot` e o nome da classe `discordDrMarutBot` ainda refletem scaffold inicial, nao design de produto.

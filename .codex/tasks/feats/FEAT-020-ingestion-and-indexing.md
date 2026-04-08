# FEAT-020 Ingestion and Indexing

## Objetivo

Descrever a macrofeature responsavel por transformar os JSONs do PF2E em dados normalizados, rastreaveis e consultaveis pelo bot.

## Subfeatures associadas

- FEAT-011 Parser de RuleEntry
- FEAT-012 Sanitizacao de HTML/markup
- FEAT-013 Persistencia de RuleEntry
- FEAT-014 Versionamento de SyncRun
- FEAT-015 Reindexacao manual

## Estado atual

`nao iniciada`

## O que nao foi encontrado

- Nenhuma entidade de dominio como `RuleEntry`, `RuleAlias` ou `SyncRun`.
- Nenhum parser Jackson para packs do Foundry.
- Nenhuma camada de repositorio.
- Nenhuma configuracao de banco.
- Nenhuma migracao Flyway.
- Nenhum job de importacao ou reindexacao.
- Nenhum mecanismo de versionamento de sync.

## Evidencias

- `src/main/java/discord/bot/discordDrMarutBot.java` e o unico arquivo fonte Java atual.
- `pom.xml` nao inclui Spring Boot, PostgreSQL, Flyway, Jackson especifico, Testcontainers ou Actuator.
- Nao ha pastas de dominio, aplicacao ou infra no codigo Java.

## Leitura do estado real

- A fonte bruta existe, mas a pipeline de ingestao ainda nao comecou.
- O produto alvo do `AGENTS.md` pressupoe persistencia e rastreabilidade, mas o repositorio ainda esta antes dessa fase.
- Esta macrofeature depende de uma decisao arquitetural forte sobre recorte inicial de conteudo, modelo relacional e estrategia de importacao idempotente.

## Saidas esperadas desta macrofeature no roadmap

- Modelo interno de `RuleEntry`.
- Texto sanitizado e rastreavel.
- Persistencia em PostgreSQL.
- Registro de versao e historico de importacao.
- Processo repetivel de reprocessamento.

## Dependencias principais

- Foundation bootstrap mais madura.
- Definicao de persistencia e migracoes.
- Delimitacao de quais packs entram no MVP.

## Riscos e observacoes

- Sem esta macrofeature, as features de busca e explicacao ficam bloqueadas.
- O `AGENTS.md` exige rastreabilidade por sync; isso deve entrar desde o primeiro desenho de persistencia, nao depois.

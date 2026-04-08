# Features do Projeto Dr Marut

Este diretorio organiza o projeto em macrofeatures com base em duas fontes:

1. O estado real do repositorio lido em `src/`, `pom.xml`, `pf2e-data/` e `.gitignore`.
2. O roadmap e a arquitetura-alvo definidos em `.codex/AGENTS.md`.

O objetivo aqui nao e fingir que o roadmap ja esta implementado. A documentacao separa o que existe hoje do que esta previsto no produto.

## Convencao de status

- `presente`: existe no repositorio de forma identificavel.
- `parcial`: existe apenas como scaffold, dependencia, config ou intencao inicial.
- `nao iniciada`: nao ha implementacao observavel no codigo atual.
- `risco`: ponto relevante que exige atencao, mesmo sem fazer parte de uma feature concluida.

## Visao geral

| Macrofeature | Objetivo | Subfeatures do AGENTS.md | Estado atual | Evidencias principais |
| --- | --- | --- | --- | --- |
| `FEAT-000` Foundation Bootstrap | Base tecnica do bot | FEAT-000 a FEAT-005 | `parcial` | `pom.xml`, `src/main/java/discord/bot/discordDrMarutBot.java`, `src/test/java/discord/bot/discordDrMarutBotTest.java` |
| `FEAT-010` PF2E Local Data Source | Fonte local de dados PF2E | FEAT-010 | `parcial` | `pf2e-data/README.md`, `pf2e-data/package.json`, `pf2e-data/packs/pf2e` |
| `FEAT-020` Ingestion and Indexing | Ingerir, normalizar e persistir dados | FEAT-011 a FEAT-015 | `nao iniciada` | Ausencia de parser, banco, migracoes e entidades de dominio |
| `FEAT-030` Search, Ranking and Explanation | Consultar e explicar regras com confianca | FEAT-020 a FEAT-034 | `nao iniciada` | Ausencia de engine de busca, score, explicacao e comparacao |
| `FEAT-040` Discord Delivery | Entregar valor via Discord | FEAT-040 a FEAT-045 | `parcial` | Dependencia JDA no `pom.xml`, token em `src/main/resources/config.properties`, `main()` placeholder |
| `FEAT-050` Quality, Security and Operations | Operacao, testes, observabilidade e seguranca | FEAT-050 a FEAT-055 + criterios globais de qualidade | `parcial` | Teste placeholder, sem observabilidade real, sem banco, sem CI, segredo local em arquivo |

## Leitura curta do repositorio

- O codigo Java executavel atual e minimo: 1 classe principal com 17 linhas e 1 teste placeholder com 34 linhas.
- O workspace contem uma base local extensa do sistema PF2E em `pf2e-data`, com 28 mil+ arquivos dentro de `packs`.
- O `AGENTS.md` define uma visao de produto muito mais avancada do que a implementacao atual.
- O projeto ainda nao segue `package by feature`; hoje ele esta essencialmente no estado de bootstrap.

## Arquivos desta pasta

- `FEAT-000-foundation-bootstrap.md`
- `FEAT-010-pf2e-local-data-source.md`
- `FEAT-020-ingestion-and-indexing.md`
- `FEAT-030-search-ranking-and-explanation.md`
- `FEAT-040-discord-delivery.md`
- `FEAT-050-quality-security-and-operations.md`
- `LOG-2026-04-08.md`

## Observacoes importantes

- A divisao foi feita por macrofeatures, conforme decisao tomada durante o planejamento.
- Cada arquivo lista as subfeatures do roadmap que pertencem ao bloco.
- O token encontrado em `src/main/resources/config.properties` nao foi reproduzido nesta documentacao; ele aparece apenas como risco.

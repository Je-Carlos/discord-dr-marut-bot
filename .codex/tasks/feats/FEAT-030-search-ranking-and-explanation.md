# FEAT-030 Search, Ranking and Explanation

## Objetivo

Agrupar o motor de consulta do produto: normalizacao de query, alias, busca, score, politica de confianca, desambiguacao, explicacao e comparacao.

## Subfeatures associadas

- FEAT-020 Normalizacao de queries
- FEAT-021 Alias dictionary
- FEAT-022 Busca lexical
- FEAT-023 Ranking e score
- FEAT-024 Politica de confianca
- FEAT-025 Desambiguacao
- FEAT-030 Summary builder
- FEAT-031 Response formatter
- FEAT-032 Comparison engine
- FEAT-033 Source citation formatter
- FEAT-034 Compact/expanded response modes

## Estado atual

`nao iniciada`

## O que nao foi encontrado

- Nenhum objeto de consulta normalizada.
- Nenhum dicionario de aliases.
- Nenhuma busca por nome, texto ou similaridade.
- Nenhum algoritmo de ranking.
- Nenhuma politica de thresholds de confianca.
- Nenhuma camada de explicacao.
- Nenhum comparador de regras.

## Evidencias

- `src/main/java/discord/bot/discordDrMarutBot.java` nao contem nenhuma logica de busca.
- `src/test/java/discord/bot/discordDrMarutBotTest.java` nao contem cenarios funcionais.
- Nao ha classes, pacotes ou contratos relacionados a busca em `src/main/java`.

## Leitura do estado real

- O produto descrito no `AGENTS.md` e, na pratica, uma engine de conhecimento de regras com adaptador Discord.
- Hoje essa engine ainda nao existe.
- A macrofeature esta completamente dependente da ingestao e do modelo interno dos dados.

## Comportamentos exigidos pelo roadmap

- Match exato com prioridade alta.
- Alias e normalizacao antes de heuristica mais ampla.
- Priorizacao de Remaster.
- Desambiguacao quando houver candidatos fortes proximos.
- Resposta curta, rastreavel e honesta.
- Comparacao destacando diferencas relevantes.

## Dependencias principais

- Dados PF2E ingeridos e persistidos.
- Modelo de `RuleEntry` e metadados de fonte.
- Sanitizacao concluida.

## Riscos e observacoes

- Se a explicacao nascer antes da politica de confianca, o bot vira um "chat que improvisa", o que contradiz o `AGENTS.md`.
- Esta e a macrofeature central do produto, mas hoje ela ainda e apenas visao arquitetural.

# FEAT-010 PF2E Local Data Source

## Objetivo

Registrar a fonte local de verdade do dominio PF2E ja presente no repositorio e delimitar seu papel no produto.

## Subfeatures associadas

- FEAT-010 Importador do repositorio PF2e

## Estado atual

`parcial`

## O que existe hoje

- A pasta `pf2e-data/` contem um projeto Node/TypeScript independente do app Java.
- O dataset local esta presente em `pf2e-data/packs/pf2e`.
- A arvore PF2E contem 94 categorias de packs no primeiro nivel de `pf2e-data/packs/pf2e`.
- Ha 28 mil+ arquivos dentro de `pf2e-data/packs`.
- O subprojeto `pf2e-data` tem scripts de build, lint, test e extracao de packs em `pf2e-data/package.json`.
- A documentacao do dataset esta em `pf2e-data/README.md`, `pf2e-data/CONTRIBUTING.md` e changelogs proprios.

## Evidencias

- `pf2e-data/README.md`
- `pf2e-data/CONTRIBUTING.md`
- `pf2e-data/package.json`
- `pf2e-data/packs/pf2e`

## Leitura do estado real

- A base PF2E esta presente localmente no workspace, o que esta alinhado com o principio de `source of truth local`.
- Pelo `rtk git status --short` observado nesta sessao, `pf2e-data/` aparece como nao rastreado na worktree atual.
- Ainda nao existe integracao entre o app Java e `pf2e-data`.
- O app Java nao le JSONs, nao extrai campos de regra, nao gera modelo interno e nao persiste nada.
- Hoje `pf2e-data` funciona como ativo bruto do dominio, nao como subsistema integrado do bot.

## Categorias relevantes observadas

- `actions`
- `conditions`
- `feats`
- `equipment`
- `classes`
- `ancestries`
- `heritages`
- `deities`
- `hazards`
- `npc-gallery`
- `pathfinder-monster-core`

## Lacunas para fechar esta macrofeature

- Definir quais packs entram no MVP.
- Implementar leitura deterministicas dos JSONs de interesse.
- Extrair `RuleEntry` e metadados de origem.
- Sanitizar markup e HTML do Foundry.
- Persistir o conteudo processado.
- Registrar `SyncRun` com commit, tag e timestamps.
- Permitir reimportacao e reindexacao.

## Riscos e observacoes

- O repositorio carrega uma base de dados grande, mas ainda sem fronteira clara entre "dataset vendorizado" e "motor do bot".
- Sem uma politica de recorte de packs, o MVP corre risco de tentar ingerir tudo cedo demais.

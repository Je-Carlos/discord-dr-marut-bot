# AGENTS.md

# Dr Marut — Spec Driven Development Guide for Codex

> Documento mestre para orientar o Codex no desenvolvimento do projeto **Dr Marut**.
> Este arquivo formaliza a proposta da aplicação, a arquitetura, as regras de negócio,
> as convenções de engenharia de software, a organização por features e as etapas de execução.
>
> **Autoridade deste documento:** alta.
> Sempre que houver conflito entre uma implementação ad hoc e este documento, o Codex deve
> seguir este documento e, se necessário, propor uma atualização de spec antes de codificar.

---

## 1. Visão do Produto

**Dr Marut** é um bot para Discord, construído em **Java + Spring Boot + Maven**, cuja função é atuar como um **consultor de regras** para jogos usando a base de dados do sistema **PF2e** disponibilizada pelo repositório do **FoundryVTT**.

O bot deve permitir que usuários consultem regras, ações, itens, condições, feats, magias, monstros e outros elementos indexados a partir dos packs do PF2e, com foco em:

- busca confiável;
- explicação clara e curta;
- desambiguação quando houver múltiplos resultados;
- separação entre **regra geral** e **exceções**;
- rastreabilidade da origem da resposta;
- suporte primário a **slash commands**;
- suporte secundário a **menções ao bot**.

O projeto deve ser tratado como uma **engine de conhecimento de regras com adaptador Discord**, e não como um chatbot genérico.

---

## 2. Objetivos

## 2.1 Objetivos principais

1. Construir uma base sólida em **Java + Spring Boot**.
2. Importar e indexar os dados do PF2e a partir dos JSONs do FoundryVTT.
3. Permitir consultas via Discord com boa latência e alto grau de confiança.
4. Priorizar respostas fundamentadas em dados indexados, evitando invenções.
5. Manter arquitetura preparada para evolução futura (API, painel, novos transportes).

## 2.2 Objetivos secundários

1. Facilitar atualização dos dados por versionamento de commit/tag.
2. Permitir comparação entre regras relacionadas.
3. Exibir respostas organizadas, curtas e úteis.
4. Disponibilizar observabilidade básica e operação confiável.

## 2.3 Não objetivos do MVP

1. Não implementar chatbot livre irrestrito.
2. Não usar LLM como fonte primária de verdade.
3. Não tentar resolver todas as edge cases do sistema de regras na primeira versão.
4. Não depender de leitura online do GitHub a cada consulta.
5. Não acoplar a regra de negócio ao Discord.
6. Não responder sem evidência quando a confiança da busca for baixa.

---

## 3. Princípios Arquiteturais

1. **Source of Truth local:** o bot responde a partir de dados importados e indexados localmente.
2. **Discord é adaptador, não domínio.**
3. **Spec before code:** nenhuma feature relevante deve ser implementada sem spec.
4. **Package by feature:** organização principal por feature, não por camada global.
5. **Domínio explícito:** nomes, entidades e fluxos devem refletir o problema real.
6. **Confiança antes de conveniência:** se a confiança for baixa, desambiguar ou recusar responder.
7. **Respostas curtas, rastreáveis e honestas.**
8. **Remaster-first:** quando houver conflito entre conteúdos, priorizar a linha Remaster, salvo pedido explícito do usuário.
9. **Evolução incremental:** entregar em fases pequenas, testáveis e reversíveis.
10. **Preferir comandos estruturados:** slash commands são o fluxo principal; menções são suporte complementar.

---

## 4. Stack Tecnológica

## 4.1 Base obrigatória

- **Java 21 LTS**
- **Spring Boot 3.x**
- **Maven**
- **JDA** para integração com Discord
- **PostgreSQL** como persistência principal
- **Flyway** para migrações
- **JUnit 5**
- **Testcontainers** para testes de integração
- **Jackson** para parse dos JSONs
- **Spring Validation**
- **SLF4J + Logback**
- **Spring Actuator**

## 4.2 Dependências recomendadas

- **Caffeine** para cache local
- **Micrometer** para métricas
- **MapStruct** apenas se simplificar DTOs sem poluir o domínio
- **Jsoup** para sanitização de HTML
- **Apache Commons Text** se necessário para normalização
- **Spring Retry** se houver jobs/retries externos

## 4.3 Dependências opcionais futuras

- **OpenSearch/Elasticsearch** para busca avançada
- **pg_trgm** e full-text search do PostgreSQL
- **Spring Scheduling / Quartz** para sincronização recorrente
- **Spring Web** para expor endpoints administrativos
- **Spring AI / integração LLM** apenas como etapa posterior e nunca como motor de verdade primário

---

## 5. Estratégia de Interação no Discord

## 5.1 Fluxos suportados

### Primário
- Slash commands

### Secundário
- Menção direta ao bot em canal
- DM para o bot

## 5.2 Comandos previstos

- `/rule <query>`
- `/compare <term-a> <term-b>`
- `/search <query>`
- `/source <rule>`
- `/help`
- `/about`

## 5.3 Regras de UX

1. Se houver um resultado altamente confiável, responder diretamente.
2. Se houver múltiplos candidatos fortes, responder com desambiguação.
3. Se a confiança for baixa, informar que não foi possível determinar a regra correta.
4. Sempre citar ao menos:
   - nome do registro;
   - tipo do registro;
   - publicação/fonte quando disponível.
5. Não despejar texto bruto integral do compêndio.
6. Preferir resumo estruturado em vez de cópia literal longa.
7. Para comparações, exibir diferenças essenciais lado a lado.
8. Para consultas muito amplas, sugerir refino.

---

## 6. Fonte de Dados

A fonte de dados oficial do domínio será o repositório do **FoundryVTT PF2e** no caminho de packs relevante ao projeto.

### Diretriz
O sistema deve importar os JSONs e armazenar:
- conteúdo bruto;
- conteúdo normalizado;
- metadados de origem;
- commit/tag/versionamento da importação.

### Regras
1. Nunca consultar o GitHub em tempo real por requisição do usuário.
2. Toda consulta do usuário deve ocorrer sobre dados já importados.
3. Toda importação deve registrar:
   - commit SHA;
   - tag/release, se houver;
   - data/hora da sincronização;
   - status da execução.
4. O sistema deve permitir reindexação.

---

## 7. Modelo de Produto

## 7.1 Problema que resolvemos

Usuários precisam consultar regras do PF2e dentro do Discord de forma rápida, clara e confiável, sem depender de procurar manualmente em múltiplos compêndios ou textos longos.

## 7.2 Proposta de valor

O Dr Marut atua como um **advogado de regras**:
- encontra a regra relevante;
- resume o conteúdo;
- informa exceções quando aplicável;
- evita respostas inventadas;
- aponta a fonte.

## 7.3 Posicionamento

O Dr Marut não é um “oráculo que sempre sabe tudo”.
Ele é um **sistema de busca, ranking e explicação com confiança controlada**.

---

## 8. Domínio e Regra de Negócio

## 8.1 Entidades de domínio principais

### RuleEntry
Representa um registro indexável do PF2e.

Campos sugeridos:
- `id`
- `slug`
- `name`
- `type`
- `pack`
- `sourceBook`
- `sourcePage`
- `publicationData`
- `traits`
- `descriptionHtml`
- `descriptionText`
- `rawJson`
- `remasterStatus`
- `versionRef`
- `path`
- `searchText`
- `aliases`
- `createdAt`
- `updatedAt`

### RuleAlias
Alias, sinônimo ou normalização para RuleEntry.

### SyncRun
Representa uma execução de ingestão/importação.

### SearchQuery
Objeto de consulta normalizada.

### SearchCandidate
Resultado intermediário com score.

### SearchResult
Resultado final com confiança e justificativa.

### Explanation
Resumo estruturado do conteúdo a ser enviado ao usuário.

---

## 8.2 Tipos de conteúdo relevantes

O sistema deve tratar pelo menos:
- Actions
- Conditions
- Feats
- Spells
- Equipment
- Traits
- Ancestries
- Classes
- Archetypes
- Monsters / NPCs
- Effects
- Rules elements relacionados, quando útil

A lista inicial de tipos pode ser expandida ao longo do projeto.

---

## 8.3 Regras de negócio obrigatórias

1. **Nunca inventar regra ausente.**
2. **Se não houver evidência suficiente, não responder como se fosse certeza.**
3. **Quando houver ambiguidade, pedir desambiguação.**
4. **Priorizar match por nome e alias antes de match puramente semântico.**
5. **Priorizar conteúdo Remaster por padrão.**
6. **Quando houver conflito entre regra geral e exceção específica, deixar isso explícito.**
7. **Respostas devem ser curtas e orientadas ao uso.**
8. **Fonte deve ser mostrada sempre que disponível.**
9. **Texto bruto longo deve ser resumido, não despejado.**
10. **O bot deve distinguir busca direta de explicação contextual.**
11. **Comparações devem destacar diferenças, não repetir textos inteiros.**
12. **Se uma consulta corresponder a múltiplos tipos, o ranking deve ponderar tipo + contexto.**
13. **Campos HTML/markup do Foundry devem ser sanitizados antes do uso.**
14. **Referências especiais como UUID/macros internas devem ser resolvidas ou removidas da resposta.**
15. **Toda resposta deve carregar um indicador interno de confiança.**

---

## 8.4 Hierarquia de confiança da resposta

Ordem recomendada:

1. Match exato por nome
2. Match exato por alias
3. Match forte por nome normalizado
4. Match forte por nome + tipo esperado
5. Match por descrição com score alto
6. Busca ampliada com score médio
7. Sem resposta assertiva; desambiguar ou admitir baixa confiança

---

## 8.5 Política de decisão

### Responder diretamente
Quando:
- top 1 score muito acima dos demais;
- tipo compatível com a consulta;
- conteúdo coerente com o termo;
- confiança acima do threshold configurado.

### Desambiguar
Quando:
- múltiplos resultados com score próximo;
- termo genérico;
- conflito entre tipos plausíveis.

### Recusar com honestidade
Quando:
- score baixo;
- sem evidência suficiente;
- resultado potencialmente enganoso.

---

## 9. Busca, Ranking e Explicação

## 9.1 Estratégia de busca do MVP

A busca do MVP deve ser híbrida, porém simples e rastreável.

### Fase inicial
- busca lexical;
- normalização de string;
- aliases;
- trigram / similaridade textual;
- full-text search no PostgreSQL.

### Fase posterior
- índice externo para ranking avançado;
- embeddings semânticos, se realmente necessário.

## 9.2 Normalização obrigatória

- lowercase
- remoção de pontuação supérflua
- remoção da menção ao bot
- trim
- tratamento de plural/singular simples
- aliases manuais
- sinônimos de termos recorrentes
- tratamento de variações remaster/legacy, quando aplicável

## 9.3 Sinais de ranking

- match no nome
- match em alias
- tipo do conteúdo
- pack
- frequência do termo na descrição
- score de similaridade
- prioridade remaster
- prioridade de termos exatos
- penalidade para conteúdo tangencial

## 9.4 Engine de explicação

A explicação deve ser baseada em templates e extração estruturada.

Formato recomendado:
- Nome
- Tipo
- Resumo objetivo
- Exceções ou observações relevantes
- Fonte

### Regra
O bot não deve depender de geração livre para formular a verdade da regra.
A camada de explicação apenas reorganiza e resume dados confiáveis já encontrados.

---

## 10. Arquitetura de Software

## 10.1 Macroarquitetura

```text
Discord (JDA)
  -> Interaction / Mention Handlers
  -> Application Use Cases
  -> Domain Services
  -> Search / Ranking / Explanation
  -> Repositories
  -> PostgreSQL / Cache
  -> Importer / Indexer / Sync Jobs
```

## 10.2 Estilo arquitetural

- modular monolith
- package by feature
- boundary clara entre domínio, aplicação e infraestrutura
- adapters externos isolados

## 10.3 Regras de arquitetura

1. Não colocar lógica de negócio em listener do Discord.
2. Não colocar regra de ranking em controller/handler.
3. Não colocar parse de JSON dentro do domínio.
4. Repositórios devem expor contratos claros.
5. Domínio não deve depender de JDA.
6. Feature flags e configs devem estar em propriedades centralizadas.
7. Toda integração externa deve ser encapsulada.

---

## 11. Estrutura de Projeto

Estrutura recomendada:

```text
dr-marut/
  pom.xml
  AGENTS.md
  README.md
  docs/
    adr/
    product/
    specs/
    runbooks/
  src/
    main/
      java/
        com/drmarut/
          DrMarutApplication.java
          common/
          config/
          feature/
            ruleingestion/
              application/
              domain/
              infra/
              web/
            rulesearch/
              application/
              domain/
              infra/
              discord/
            ruleexplanation/
              application/
              domain/
              infra/
            discordbot/
              application/
              domain/
              infra/
            comparison/
              application/
              domain/
              infra/
            admin/
              application/
              domain/
              infra/
    test/
      java/
        com/drmarut/
          ...
```

## 11.1 Convenção de pacotes

Usar **package by feature**.  
Dentro de cada feature, separar internamente por subdomínio técnico apenas quando fizer sentido:
- `application`
- `domain`
- `infra`
- `discord`
- `web`

Evitar:
- gigantesco `service/`, `controller/`, `repository/` global
- classes utilitárias genéricas sem responsabilidade clara

---

## 12. Workflow de Spec Driven Development

## 12.1 Regra principal

**Nenhuma feature média ou grande deve ser implementada sem spec.**

## 12.2 Estrutura obrigatória por feature

Para cada feature, criar:

```text
docs/specs/FEAT-XXX-short-name/
  spec.md
  plan.md
  tasks.md
  acceptance.md
  notes.md
```

## 12.3 Conteúdo mínimo de cada arquivo

### `spec.md`
- contexto
- problema
- objetivo
- escopo
- fora de escopo
- requisitos funcionais
- requisitos não funcionais
- regras de negócio
- casos de uso
- contratos
- riscos
- critérios de aceite

### `plan.md`
- abordagem de implementação
- impactos em arquitetura
- migrações
- componentes afetados
- estratégia de teste
- rollback

### `tasks.md`
- checklist atômico e incremental
- ordem de execução
- dependências

### `acceptance.md`
- cenários Given/When/Then
- exemplos de input/output
- edge cases

### `notes.md`
- aprendizados
- desvios aprovados
- observações técnicas

---

## 12.4 Ordem de trabalho obrigatória do Codex

1. Ler este `AGENTS.md`
2. Identificar a feature
3. Verificar se já existe `docs/specs/FEAT-XXX-*`
4. Se não existir, criar a spec antes de codar
5. Propor ou registrar ADR se a decisão for arquitetural
6. Implementar em pequenos passos
7. Escrever testes
8. Validar critérios de aceite
9. Atualizar docs
10. Só então considerar a feature concluída

---

## 13. ADRs (Architecture Decision Records)

Toda decisão estrutural relevante deve gerar ADR em:

```text
docs/adr/ADR-XXXX-title.md
```

## 13.1 Deve haver ADR para

- escolha de estratégia de busca
- escolha de persistência
- forma de ingestão dos packs
- política de versionamento dos dados
- uso ou não de índice externo
- uso ou não de LLM
- estratégia de interação com Discord
- decisão de package by feature
- critérios de ranking importantes

---

## 14. Features e Roadmap por Etapas

## Fase 0 — Foundation

Objetivo: preparar a base técnica.

### Features
- FEAT-000 Projeto base Spring Boot + Maven
- FEAT-001 Configuração JDA
- FEAT-002 Persistência PostgreSQL + Flyway
- FEAT-003 Observabilidade básica
- FEAT-004 Pipeline CI
- FEAT-005 Estrutura docs/specs/adr

### Aceite
- projeto sobe localmente
- healthcheck funcional
- bot conecta no Discord
- banco sobe com migrações
- build e testes executam

---

## Fase 1 — Ingestão de Dados

Objetivo: importar e persistir os dados do PF2e.

### Features
- FEAT-010 Importador do repositório PF2e
- FEAT-011 Parser de RuleEntry
- FEAT-012 Sanitização de HTML/markup
- FEAT-013 Persistência de RuleEntry
- FEAT-014 Versionamento de SyncRun
- FEAT-015 Reindexação manual

### Aceite
- importar amostra real de packs
- persistir dados relevantes
- registrar commit/tag
- reprocessar sem duplicidade indevida

---

## Fase 2 — Busca e Ranking

Objetivo: permitir consulta confiável.

### Features
- FEAT-020 Normalização de queries
- FEAT-021 Alias dictionary
- FEAT-022 Busca lexical
- FEAT-023 Ranking e score
- FEAT-024 Política de confiança
- FEAT-025 Desambiguação

### Aceite
- consulta exata encontra registro certo
- consulta ambígua pede refinamento
- score é rastreável
- remaster é priorizado por padrão

---

## Fase 3 — Explicação e Resposta

Objetivo: transformar resultado em resposta útil.

### Features
- FEAT-030 Summary builder
- FEAT-031 Response formatter
- FEAT-032 Comparison engine
- FEAT-033 Source citation formatter
- FEAT-034 Compact/expanded response modes

### Aceite
- resposta curta e clara
- sem HTML cru
- com fonte
- com observações relevantes

---

## Fase 4 — Integração Discord

Objetivo: entregar o produto utilizável no Discord.

### Features
- FEAT-040 Slash command `/rule`
- FEAT-041 Slash command `/compare`
- FEAT-042 Slash command `/search`
- FEAT-043 Mention handler
- FEAT-044 Embeds e botões
- FEAT-045 Help / onboarding

### Aceite
- slash commands registrados
- menção interpretada corretamente
- resposta formatada no Discord
- desambiguação navegável

---

## Fase 5 — Operação e Administração

Objetivo: manter o sistema estável.

### Features
- FEAT-050 Job de sync
- FEAT-051 Endpoint administrativo
- FEAT-052 Métricas de uso
- FEAT-053 Logs estruturados
- FEAT-054 Cache e tuning
- FEAT-055 Rate limiting / resilience

### Aceite
- sincronização repetível
- métricas mínimas disponíveis
- logs úteis para incidentes
- comportamento previsível sob carga

---

## Fase 6 — Inteligência Contextual Controlada

Objetivo: melhorar utilidade sem perder confiança.

### Features
- FEAT-060 Relacionamento entre regras
- FEAT-061 Exceções relevantes
- FEAT-062 Sugestão de termos relacionados
- FEAT-063 Explain-by-context
- FEAT-064 LLM assistida opcional e controlada

### Aceite
- ainda há rastreabilidade
- motor principal continua sendo busca + regras
- nenhuma resposta inventada por geração livre

---

## 15. Critérios de Qualidade

## 15.1 Definição de pronto (Definition of Done)

Uma feature só está pronta quando:

1. Possui spec válida
2. Possui testes relevantes
3. Passa build local
4. Atende critérios de aceite
5. Atualiza docs, se necessário
6. Não viola arquitetura
7. Não adiciona acoplamento desnecessário
8. Não deixa TODO crítico sem registro
9. Está observável o suficiente para suporte
10. Está revisável por outro desenvolvedor

---

## 15.2 Regras de código

1. Código deve ser claro antes de ser “esperto”.
2. Nomes devem refletir intenção.
3. Métodos pequenos e com responsabilidade única.
4. Evitar classes gigantes.
5. Evitar utilitários genéricos sem contexto.
6. Evitar lógica de negócio em DTOs.
7. Evitar `if/else` dispersos para regras centrais; encapsular políticas.
8. Preferir objetos explícitos a mapas soltos.
9. Preferir imutabilidade onde razoável.
10. Validar entradas na borda.

---

## 15.3 Regras para o Codex ao editar código

1. Não refatorar áreas não relacionadas sem necessidade.
2. Não introduzir framework novo sem ADR.
3. Não mudar contrato público sem atualizar spec.
4. Não usar comentários para esconder design ruim.
5. Não duplicar lógica de ranking.
6. Não acoplar testes a detalhes frágeis.
7. Não implementar parcialmente sem marcar claramente o status.
8. Não remover rastreabilidade da origem dos dados.

---

## 16. Estratégia de Testes

## 16.1 Pirâmide de testes

### Unitários
Para:
- normalização;
- ranking;
- confiança;
- parsing;
- explicação;
- comparação.

### Integração
Para:
- repositórios;
- migrações;
- ingestão;
- queries reais;
- fluxo com banco.

### Contrato
Para:
- adaptadores Discord;
- payloads internos;
- comandos.

### E2E controlado
Para:
- fluxo principal `/rule`
- fluxo `/compare`
- fluxo de desambiguação

## 16.2 Casos obrigatórios

- termo exato
- termo com alias
- termo ambíguo
- termo inexistente
- item remaster vs legado
- HTML malformado
- marcação UUID
- importação repetida
- consulta com ruído textual
- score baixo

---

## 17. Persistência e Dados

## 17.1 Tabelas mínimas

### `rule_entry`
- id
- slug
- name
- type
- pack
- source_book
- source_page
- remaster_status
- traits_json
- description_html
- description_text
- raw_json
- search_text
- path
- version_ref
- created_at
- updated_at

### `rule_alias`
- id
- rule_entry_id
- alias
- weight

### `sync_run`
- id
- source_ref
- commit_sha
- tag_name
- started_at
- finished_at
- status
- stats_json
- error_message

### `search_audit` (futuro)
- id
- query_text
- normalized_query
- top_result_id
- confidence
- created_at

## 17.2 Regras de dados

1. `raw_json` deve ser preservado para rastreabilidade.
2. `description_text` deve ser derivado de forma determinística.
3. `search_text` deve agregar campos úteis para busca.
4. `version_ref` deve permitir saber de qual sync veio o conteúdo.
5. Upsert deve ser idempotente quando aplicável.

---

## 18. Configuração e Ambientes

## 18.1 Perfis sugeridos

- `local`
- `test`
- `dev`
- `prod`

## 18.2 Configurações relevantes

- token do Discord
- guild ID para registro local de comandos em dev
- URL do banco
- parâmetros de confiança
- tamanho de cache
- flags de features
- limites de resposta
- política de sync

## 18.3 Regras

1. Nunca commitar segredos.
2. Sempre usar variáveis de ambiente ou secrets manager.
3. Toda configuração deve ter valor default razoável no ambiente local quando possível.
4. Flags experimentais devem ser nomeadas claramente.

---

## 19. Observabilidade

## 19.1 Logs

Devem existir logs estruturados para:
- startup
- registro de comandos
- sync
- parsing
- falhas de busca
- score/confiança
- erros externos

## 19.2 Métricas mínimas

- tempo médio de consulta
- tempo médio de importação
- taxa de erro por comando
- quantidade de respostas por confiança
- quantidade de desambiguações
- tamanho do índice
- quantidade de syncs bem-sucedidos

## 19.3 Health checks

- app up
- banco conectado
- bot conectado
- última sync conhecida

---

## 20. Segurança e Resiliência

1. Validar entrada do usuário.
2. Limitar tamanho de query.
3. Prevenir spam de menções.
4. Não executar conteúdo vindo do compêndio.
5. Sanitizar HTML.
6. Tratar timeout e falhas externas.
7. Evitar vazamento de stacktrace ao usuário.
8. Manter logs internos detalhados, mas resposta pública enxuta.
9. Usar rate limiting em pontos sensíveis.
10. Considerar circuit breaker apenas se o projeto realmente passar a depender de serviços externos.

---

## 21. Política de Respostas do Bot

## 21.1 Tom

- claro
- objetivo
- confiável
- sem persona excessivamente espalhafatosa
- pode ter identidade temática, mas nunca prejudicar clareza

## 21.2 Estrutura preferencial

```text
<Nome> — <Tipo>

Resumo curto da regra em linguagem simples.

Observações:
- ...
- ...

Fonte: <Livro / Pack / referência>
```

## 21.3 Quando a confiança for baixa

Resposta preferencial:
- admitir incerteza;
- listar opções prováveis;
- sugerir refino.

## 21.4 Quando houver exceção relevante

Estrutura:
- regra geral
- exceção importante
- observação de contexto

---

## 22. Evolução futura prevista

O projeto deve permitir no futuro:

- API REST administrativa
- dashboard web
- sync automatizado
- índice externo
- histórico de consultas
- recomendações relacionadas
- assistente contextual controlado
- múltiplos sistemas além de PF2e
- modo web/docs além de Discord

---

## 23. Regras explícitas para o Codex

1. Sempre ler este arquivo antes de alterar o projeto.
2. Não pular a etapa de spec em features não triviais.
3. Manter o núcleo desacoplado do Discord.
4. Implementar incrementalmente.
5. Escrever testes junto com a feature.
6. Atualizar documentação sempre que o comportamento mudar.
7. Registrar ADR para decisão estrutural.
8. Não adotar atalhos que prejudiquem rastreabilidade.
9. Não tratar busca de regras como chat genérico.
10. Priorizar confiabilidade e legibilidade.
11. Quando houver dúvida entre “responder sempre” e “responder corretamente”, priorizar correção.
12. Preferir simplicidade operacional no MVP.
13. Não introduzir LLM como dependência central sem aprovação explícita.
14. Respeitar package by feature.
15. Preservar versionamento dos dados importados.

---

## 24. Primeiras Specs que o Codex deve criar

Ao iniciar o projeto, o Codex deve criar pelo menos:

1. `docs/specs/FEAT-000-project-bootstrap/spec.md`
2. `docs/specs/FEAT-010-pf2e-ingestion/spec.md`
3. `docs/specs/FEAT-020-rule-search/spec.md`
4. `docs/specs/FEAT-030-rule-explanation/spec.md`
5. `docs/specs/FEAT-040-discord-rule-command/spec.md`

E ao menos os seguintes ADRs:

1. `ADR-0001-package-by-feature.md`
2. `ADR-0002-postgresql-search-strategy.md`
3. `ADR-0003-foundry-import-versioning.md`
4. `ADR-0004-discord-interaction-strategy.md`

---

## 25. Entrega incremental recomendada

## Sprint 1
- bootstrap do projeto
- JDA conectado
- banco e migrações
- docs iniciais
- ADRs base

## Sprint 2
- importador mínimo
- parser de actions/conditions
- persistência
- sanitização

## Sprint 3
- busca exata + alias
- score inicial
- resposta textual simples

## Sprint 4
- slash command `/rule`
- desambiguação
- observabilidade básica

## Sprint 5
- compare
- source formatting
- sync manual
- tuning

---

## 26. Encerramento

Este projeto deve ser construído como um **produto de engenharia de software sério**, com domínio explícito, specs claras e evolução controlada.

O sucesso do Dr Marut não depende apenas de “fazer o bot responder”, mas de garantir que ele:
- encontre a regra correta;
- explique de forma útil;
- exponha a origem;
- saiba quando não tem confiança suficiente.

Se houver dúvida durante o desenvolvimento, a prioridade é:

1. preservar confiabilidade;
2. preservar arquitetura;
3. preservar rastreabilidade;
4. preservar simplicidade do MVP.

**Regra final:** o Codex deve agir como um engenheiro responsável pelo produto, e não como um gerador de código sem contexto.

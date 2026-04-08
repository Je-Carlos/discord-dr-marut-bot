# FEAT-040 Discord Delivery

## Objetivo

Documentar a parte do produto voltada a interacao no Discord: login, comandos, mencoes, formatacao de resposta e experiencia de uso.

## Subfeatures associadas

- FEAT-040 Slash command `/rule`
- FEAT-041 Slash command `/compare`
- FEAT-042 Slash command `/search`
- FEAT-043 Mention handler
- FEAT-044 Embeds e botoes
- FEAT-045 Help / onboarding

## Estado atual

`parcial`

## O que existe hoje

- Dependencia JDA presente no `pom.xml`.
- Campo `JDA jda` declarado na classe principal.
- Arquivo `src/main/resources/config.properties` contendo `botToken`.
- Classe principal empacotada como `mainClass` do projeto.

## Evidencias

- `pom.xml`
- `src/main/java/discord/bot/discordDrMarutBot.java`
- `src/main/resources/config.properties`

## Leitura do estado real

- O bot ainda nao faz login no Discord.
- O `main()` apenas imprime `Hello World!`.
- Nao existe `JDABuilder`, nao ha registro de listeners, nao ha slash commands, nao ha tratamento de mencoes nem DMs.
- A integracao Discord esta mais no nivel de "dependencia escolhida" do que de "feature implementada".

## Lacunas para fechar esta macrofeature

- Carregar configuracao de runtime sem segredos hardcoded.
- Construir e inicializar JDA.
- Registrar slash commands do MVP.
- Implementar handlers desacoplados do dominio.
- Definir formato de resposta curto e navegavel.
- Tratar baixa confianca e desambiguacao dentro do UX do Discord.

## Riscos e observacoes

- O token em `config.properties` e um risco claro de seguranca e contradiz a diretriz "nunca commitar segredos".
- Como nao ha logica real de Discord ainda, qualquer sensacao de "bot pronto" seria enganosa.

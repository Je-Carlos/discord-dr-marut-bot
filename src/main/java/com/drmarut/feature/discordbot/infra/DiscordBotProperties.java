package com.drmarut.feature.discordbot.infra;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "drmarut.discord")
public record DiscordBotProperties(
        boolean enabled,
        String token,
        String guildId
) {
}

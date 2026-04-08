package com.drmarut.feature.discordbot.infra;

import com.drmarut.feature.discordbot.application.DiscordBotClient;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;

public class DiscordBotHealthIndicator implements HealthIndicator {

    private final DiscordBotClient discordBotClient;

    public DiscordBotHealthIndicator(DiscordBotClient discordBotClient) {
        this.discordBotClient = discordBotClient;
    }

    @Override
    public Health health() {
        if (!discordBotClient.enabled()) {
            return Health.unknown()
                    .withDetail("discord", "disabled")
                    .build();
        }

        if (discordBotClient.connected()) {
            return Health.up()
                    .withDetail("discord", "connected")
                    .withDetail("status", discordBotClient.status())
                    .build();
        }

        return Health.outOfService()
                .withDetail("discord", "disconnected")
                .withDetail("status", discordBotClient.status())
                .build();
    }
}

package com.drmarut.feature.discordbot.infra;

import com.drmarut.feature.discordbot.application.DiscordBotClient;
import org.junit.jupiter.api.Test;
import org.springframework.boot.actuate.health.Status;

import static org.assertj.core.api.Assertions.assertThat;

class DiscordBotHealthIndicatorTest {

    @Test
    void reportsUnknownWhenDiscordIsDisabled() {
        DiscordBotHealthIndicator indicator = new DiscordBotHealthIndicator(new StubDiscordBotClient(false, false, "disabled"));

        assertThat(indicator.health().getStatus()).isEqualTo(Status.UNKNOWN);
    }

    @Test
    void reportsOutOfServiceWhenDiscordIsEnabledButDisconnected() {
        DiscordBotHealthIndicator indicator = new DiscordBotHealthIndicator(new StubDiscordBotClient(true, false, "connecting"));

        assertThat(indicator.health().getStatus()).isEqualTo(Status.OUT_OF_SERVICE);
    }

    @Test
    void reportsUpWhenDiscordIsConnected() {
        DiscordBotHealthIndicator indicator = new DiscordBotHealthIndicator(new StubDiscordBotClient(true, true, "connected"));

        assertThat(indicator.health().getStatus()).isEqualTo(Status.UP);
    }

    private record StubDiscordBotClient(boolean enabled, boolean connected, String status) implements DiscordBotClient {
    }
}

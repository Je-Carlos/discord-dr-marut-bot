package com.drmarut.feature.discordbot.infra;

import com.drmarut.feature.discordbot.application.DiscordBotClient;
import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.AutoConfigurations;
import org.springframework.boot.autoconfigure.context.ConfigurationPropertiesAutoConfiguration;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;

import static org.assertj.core.api.Assertions.assertThat;

class DiscordBotConfigurationTest {

    private final ApplicationContextRunner contextRunner = new ApplicationContextRunner()
            .withConfiguration(AutoConfigurations.of(ConfigurationPropertiesAutoConfiguration.class))
            .withUserConfiguration(DiscordBotConfiguration.class);

    @Test
    void createsNoopClientWhenDiscordIsDisabled() {
        contextRunner
                .withPropertyValues("drmarut.discord.enabled=false")
                .run(context -> {
                    assertThat(context).hasSingleBean(DiscordBotClient.class);
                    assertThat(context.getBean(DiscordBotClient.class)).isInstanceOf(NoopDiscordBotClient.class);
                });
    }

    @Test
    void failsFastWhenDiscordIsEnabledWithoutAToken() {
        contextRunner
                .withPropertyValues("drmarut.discord.enabled=true")
                .run(context -> {
                    assertThat(context).hasFailed();
                    assertThat(context.getStartupFailure())
                            .hasRootCauseMessage("drmarut.discord.token must be provided when Discord is enabled");
                });
    }
}

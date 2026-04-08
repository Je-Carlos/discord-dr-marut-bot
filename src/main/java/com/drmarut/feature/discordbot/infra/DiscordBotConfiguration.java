package com.drmarut.feature.discordbot.infra;

import com.drmarut.feature.discordbot.application.DiscordBotClient;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;

@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties(DiscordBotProperties.class)
class DiscordBotConfiguration {

    @Bean
    @ConditionalOnProperty(prefix = "drmarut.discord", name = "enabled", havingValue = "true")
    DiscordBotClient discordBotClient(DiscordBotProperties properties) {
        if (!StringUtils.hasText(properties.token())) {
            throw new IllegalStateException("drmarut.discord.token must be provided when Discord is enabled");
        }

        return new JdaDiscordBotClient(properties);
    }

    @Bean
    @ConditionalOnMissingBean(DiscordBotClient.class)
    DiscordBotClient noopDiscordBotClient() {
        return new NoopDiscordBotClient();
    }

    @Bean
    DiscordBotHealthIndicator discordBotHealthIndicator(DiscordBotClient discordBotClient) {
        return new DiscordBotHealthIndicator(discordBotClient);
    }
}

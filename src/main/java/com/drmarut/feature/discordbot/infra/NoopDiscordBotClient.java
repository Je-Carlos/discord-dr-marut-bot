package com.drmarut.feature.discordbot.infra;

import com.drmarut.feature.discordbot.application.DiscordBotClient;

final class NoopDiscordBotClient implements DiscordBotClient {

    @Override
    public boolean enabled() {
        return false;
    }

    @Override
    public boolean connected() {
        return false;
    }

    @Override
    public String status() {
        return "disabled";
    }
}

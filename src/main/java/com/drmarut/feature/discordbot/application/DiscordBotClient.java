package com.drmarut.feature.discordbot.application;

public interface DiscordBotClient {

    boolean enabled();

    boolean connected();

    String status();
}

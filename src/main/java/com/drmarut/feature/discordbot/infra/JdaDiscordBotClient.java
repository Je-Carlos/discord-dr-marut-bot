package com.drmarut.feature.discordbot.infra;

import com.drmarut.feature.discordbot.application.DiscordBotClient;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;

final class JdaDiscordBotClient implements DiscordBotClient, DisposableBean {

    private static final Logger log = LoggerFactory.getLogger(JdaDiscordBotClient.class);

    private final JDA jda;

    JdaDiscordBotClient(DiscordBotProperties properties) {
        log.info("Starting Discord gateway");
        this.jda = JDABuilder.createDefault(properties.token()).build();
    }

    @Override
    public boolean enabled() {
        return true;
    }

    @Override
    public boolean connected() {
        return this.jda.getStatus() == JDA.Status.CONNECTED;
    }

    @Override
    public String status() {
        return this.jda.getStatus().name();
    }

    @Override
    public void destroy() {
        log.info("Shutting down Discord gateway");
        this.jda.shutdown();
    }
}

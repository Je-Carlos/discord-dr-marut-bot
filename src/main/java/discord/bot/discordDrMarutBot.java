package discord.bot;
import discord.bot.config.BotConfig;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.requests.GatewayIntent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.EnumSet;


public class discordDrMarutBot
{
    private static JDA jda;
    private static final Logger logger = LoggerFactory.getLogger(discordDrMarutBot.class);

    public static void main( String[] args )
    {
        String botToken = BotConfig.getBotToken();

        if (botToken == null || botToken.isEmpty())
        {
            logger.error("bot token not found in config.properties. Provide a valid token");

        }

        try {
            jda = JDABuilder.createDefault(botToken)
                    .enableIntents(EnumSet.allOf(GatewayIntent.class))
                    // todo: arrumar isso aqui:
//                    .addEventListeners(new CommandListener())
                    .build();
            jda.awaitReady();
            logger.info("Bot is ready!");
        } catch (Exception error) {
            logger.error("error while initializing discord dr marutbot bot", error);

        }
    }
}

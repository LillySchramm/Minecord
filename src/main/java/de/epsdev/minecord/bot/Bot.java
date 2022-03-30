package de.epsdev.minecord.bot;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;

import javax.security.auth.login.LoginException;

public class Bot {
    private String discordToken;
    private JDA jda;

    public Bot() {
        discordToken = System.getenv("discordtoken");

        try {
            jda = JDABuilder.createDefault(discordToken).build();
            jda.addEventListener(new MessageListener());

            jda.awaitReady();
        } catch (LoginException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void sendMessage(String message) {
        jda.getGuilds()
                .get(0).getTextChannelsByName("minecraft-server-chat", true)
                .get(0).sendMessage(message).complete();
    }

    public void shutdown() {
        jda.shutdownNow();
    }
}

package de.epsdev.minecord.bot;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

public class MessageListener extends ListenerAdapter {
    public static final String CHANNEL_NAME = "minecraft-server-chat";

    @Override
    public void onMessageReceived(MessageReceivedEvent event)
    {
        if (event.getTextChannel().getName().equals(CHANNEL_NAME) && !event.getAuthor().isBot()) {
            Bukkit.getServer().broadcastMessage
                    (String.format("[" + ChatColor.BLUE + "%s" + ChatColor.RESET + "] %s",
                            event.getAuthor().getAsTag(),
                            event.getMessage().getContentDisplay()
                    ));

            //System.out.printf("[%s][%s] %s: %s\n", event.getGuild().getName(),
            //        event.getTextChannel().getName(), event.getMember().getEffectiveName(),
            //        event.getMessage().getContentDisplay());
        }
    }
}

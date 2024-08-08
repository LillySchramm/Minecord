package de.epsdev.minecord.bot;

import de.epsdev.minecord.Minecord;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.channel.ChannelType;
import net.dv8tion.jda.api.entities.channel.middleman.GuildMessageChannel;
import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel;
import net.dv8tion.jda.api.entities.channel.unions.MessageChannelUnion;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class Bot extends ListenerAdapter {
    private final JDA discordClient;
    private final String CHANNEL_ID;

    private final HashMap<String, String> linkRequests = new HashMap<>();

    private GuildMessageChannel activeGuildMessageChannel = null;

    public Bot() {
        String DISCORD_TOKEN = Minecord.pluginConfig.getDiscordToken();
        CHANNEL_ID = Minecord.pluginConfig.getChannelId();

        this.discordClient = JDABuilder.createLight(DISCORD_TOKEN, EnumSet.of(GatewayIntent.GUILD_MESSAGES, GatewayIntent.MESSAGE_CONTENT, GatewayIntent.DIRECT_MESSAGES))
                .addEventListeners(this)
                .build();
    }

    private String getUserTagFromMessage(Message message) {
        return message.getAuthor().getName();
    }

    private void handleDM(Message message) {
        MessageChannelUnion messageChannel = message.getChannel();
        String messageContent = message.getContentDisplay();

        if (!messageContent.startsWith("!link ")) {
            sendMessage(messageChannel, "Unknown command.");

            return;
        }

        String id = messageContent.replace("!link ", "");

        if (!linkRequests.containsKey(id)) {
            sendMessage(messageChannel, "ID not valid.");

            return;
        }

        String userTag = message.getAuthor().getName();
        String name = linkRequests.get(id);

        linkRequests.remove(id);
        NameLink.setLink(userTag, name);

        sendMessage(messageChannel, "Link done!");
    }

    private MessageChannel getActiveGuildMessageChannel() {
        if (activeGuildMessageChannel == null) {
            activeGuildMessageChannel = discordClient.getChannelById(GuildMessageChannel.class, this.CHANNEL_ID);
        }

        return activeGuildMessageChannel;
    }

    public void sendMessage(MessageChannel messageChannel, String message) {
        messageChannel.sendMessage(
                message
        ).queue();
    }

    public void sendMessage(String message) {
        sendMessage(getActiveGuildMessageChannel(), message);
    }


    public void updatePlayerCount() {
        int maxPlayers = Bukkit.getMaxPlayers();
        int currentPlayers = Bukkit.getOnlinePlayers().size();

        discordClient.getPresence().setActivity(Activity.playing(String.format("%s / %s currently online.", currentPlayers, maxPlayers)));
    }

    public String addLinkRequest(String name) {
        String id = UUID.randomUUID().toString().substring(0, 4);
        linkRequests.put(id, name);

        return id;
    }

    public void shutdown() {
        this.discordClient.shutdownNow();
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        Message message = event.getMessage();
        String userTag = getUserTagFromMessage(message);

        boolean isBot = message.getAuthor().isBot();
        if (isBot) return;

        var channelType = message.getChannel().getType();
        if (channelType == ChannelType.PRIVATE) {
            handleDM(message);
            return;
        }

        if (!message.getChannelId().equals(CHANNEL_ID)) return;

        var name = NameLink.getLink(userTag);
        userTag = name == null ? userTag : name;


        Bukkit.getServer().broadcastMessage
            (String.format("<" + ChatColor.BLUE +  (name == null ? "" : ChatColor.BOLD)  + "%s" + ChatColor.RESET + "> %s", userTag,
                message.getContentDisplay()
            ));
    }
}

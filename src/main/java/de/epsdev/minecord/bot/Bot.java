package de.epsdev.minecord.bot;

import de.epsdev.minecord.Minecord;
import discord4j.common.util.Snowflake;
import discord4j.core.DiscordClient;
import discord4j.core.GatewayDiscordClient;
import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.core.object.entity.Guild;
import discord4j.core.object.entity.Message;
import discord4j.core.object.entity.channel.GuildMessageChannel;
import discord4j.core.spec.MessageCreateSpec;
import discord4j.discordjson.Id;
import discord4j.rest.entity.RestChannel;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

import java.util.HashMap;
import java.util.List;

public class Bot {
    private final DiscordClient discordClient;
    private final GatewayDiscordClient gateway;
    private final String CHANNEL_NAME;
    private final String DISCORD_TOKEN;

    private final HashMap<Snowflake, String> channelNameCache = new HashMap<>();
    private final HashMap<Id, String> userTagCache = new HashMap<>();
    private List<Guild> guildCache = null;
    private GuildMessageChannel activeGuildMessageChannel = null;

    public Bot() {
        DISCORD_TOKEN = Minecord.pluginConfig.getDiscordToken();
        CHANNEL_NAME = Minecord.pluginConfig.getChannelName();

        discordClient = DiscordClient.create(DISCORD_TOKEN);
        gateway = discordClient.login().block();

        initializeMessageListener();
    }

    private String getUserTagFromMessage(Message message) {
        Id userId = message.getUserData().id();
        String tag = userTagCache.getOrDefault(userId, null);

        if (tag == null) {
            tag = message.getAuthorAsMember().block().getTag();

            userTagCache.put(userId, tag);
        }

        return tag;
    }

    private String getChannelNameFromSnowflake(Snowflake channelId) {
        String name = channelNameCache.getOrDefault(channelId, null);

        if (name == null) {
            RestChannel restChannel = gateway.getChannelById(channelId).block().getRestChannel();
            name = restChannel.getData().block().name().get();

            channelNameCache.put(channelId, name);
        }

        return name;
    }

    private String getChannelNameFromMessage(Message message) {
        Snowflake channelId = message.getChannelId();

        return getChannelNameFromSnowflake(channelId);
    }

    private void initializeMessageListener() {
        gateway.on(MessageCreateEvent.class).subscribe(event -> {
            Message message = event.getMessage();

            String userTag = getUserTagFromMessage(message);
            String channelName = getChannelNameFromMessage(message);

            boolean isRealUser = message.getUserData().bot().isAbsent();

            if (channelName.equals(CHANNEL_NAME) && isRealUser) {
                Bukkit.getServer().broadcastMessage
                    (String.format("<" + ChatColor.BLUE + "%s" + ChatColor.RESET + "> %s",
                        userTag,
                        message.getContent()
                    ));
            }
        });
    }

    private List<Guild> getGuilds() {
        if (guildCache == null) {
            guildCache = gateway.getGuilds().collectList().block();
        }

        return guildCache;
    }

    private GuildMessageChannel getTextChannelFromGuildByName(Guild guild, String name) {
        return guild.getChannels().ofType(GuildMessageChannel.class)
                .filter((channel) -> channel.getName().equals(name)).blockFirst();
    }

    private GuildMessageChannel getActiveGuildMessageChannel(Guild guild) {
        if (activeGuildMessageChannel == null) {
            activeGuildMessageChannel = getTextChannelFromGuildByName(guild, CHANNEL_NAME);
        }

        return activeGuildMessageChannel;
    }

    public void sendMessage(String message) {
        Guild guild = getGuilds().get(0);
        GuildMessageChannel messageChannel = getActiveGuildMessageChannel(guild);

        messageChannel.createMessage(
            MessageCreateSpec.
                builder().
                content(message).
                build()
        ).block();
    }

    public void clearCache() {
        activeGuildMessageChannel = null;
        guildCache = null;
        userTagCache.clear();
        channelNameCache.clear();
    }

    public void shutdown() {
        gateway.logout().block();
    }
}

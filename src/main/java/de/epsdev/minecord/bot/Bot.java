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
import discord4j.rest.entity.RestChannel;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

import java.util.List;

public class Bot {
    private final DiscordClient discordClient;
    private final GatewayDiscordClient gateway;
    private final String CHANNEL_NAME;
    private final String DISCORD_TOKEN;

    public Bot() {
        DISCORD_TOKEN = Minecord.pluginConfig.getDiscordToken();
        CHANNEL_NAME = Minecord.pluginConfig.getChannelName();

        discordClient = DiscordClient.create(DISCORD_TOKEN);
        gateway = discordClient.login().block();

        initializeMessageListener();
    }

    private String getChannelNameFromMessage(Message message) {
        Snowflake channelId = message.getChannelId();
        RestChannel restChannel = gateway.getChannelById(channelId).block().getRestChannel();

        return restChannel.getData().block().name().get();
    }

    private void initializeMessageListener() {
        gateway.on(MessageCreateEvent.class).subscribe(event -> {
            Message message = event.getMessage();
            String channelName = getChannelNameFromMessage(message);

            boolean isRealUser = message.getUserData().bot().isAbsent();

            if (channelName.equals(CHANNEL_NAME) && isRealUser) {
                Bukkit.getServer().broadcastMessage
                    (String.format("<" + ChatColor.BLUE + "%s" + ChatColor.RESET + "> %s",
                        message.getAuthorAsMember().block().getTag(),
                        message.getContent()
                    ));
            }
        });
    }

    private List<Guild> getGuilds() {
        return gateway.getGuilds().collectList().block();
    }

    private GuildMessageChannel getTextChannelFromGuildByName(Guild guild, String name) {
       return guild.getChannels().ofType(GuildMessageChannel.class)
               .filter((channel) -> channel.getName().equals(name)).blockFirst();
    }

    public void sendMessage(String message) {
        Guild guild = getGuilds().get(0);
        GuildMessageChannel messageChannel = getTextChannelFromGuildByName(guild, CHANNEL_NAME);

        messageChannel.createMessage(
            MessageCreateSpec.
                builder().
                content(message).
                build()
        ).block();
    }

    public void shutdown() {
        gateway.logout();
    }
}

package de.epsdev.minecord.config;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.Plugin;

public class PluginConfig {
    private FileConfiguration fileConfiguration;
    private final Plugin plugin;

    public PluginConfig(Plugin plugin) {
        this.plugin = plugin;

        fileConfiguration = plugin.getConfig();

        setDefaults();
    }

    private void setDefaults() {
        fileConfiguration.addDefault("discord_token", "aToken");
        fileConfiguration.addDefault("channel_id", "chatId");
        fileConfiguration.addDefault("enable_player_count", true);

        fileConfiguration.options().copyDefaults(true);
        plugin.saveConfig();
    }

    public String getDiscordToken() {
        return fileConfiguration.getString("discord_token");
    }

    public String getChannelId() {
        return fileConfiguration.getString("channel_id");
    }

    public Boolean getEnablePlayerCount() {
        return fileConfiguration.getBoolean("enable_player_count");
    }
}

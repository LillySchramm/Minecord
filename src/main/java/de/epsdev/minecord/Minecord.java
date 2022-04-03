package de.epsdev.minecord;

import de.epsdev.minecord.bot.Bot;
import de.epsdev.minecord.bot.NameLink;
import de.epsdev.minecord.commands.c_ClearDiscordBotCache;
import de.epsdev.minecord.commands.c_LinkDiscord;
import de.epsdev.minecord.commands.c_UnlinkDiscord;
import de.epsdev.minecord.config.PluginConfig;
import de.epsdev.minecord.events.e_OnPlayerChat;
import de.epsdev.minecord.events.e_OnPlayerJoin;
import de.epsdev.minecord.version.Version;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.logging.Logger;

public final class Minecord extends JavaPlugin {

    public static Bot bot;
    public static PluginConfig pluginConfig;
    public static Logger pluginLogger;
    public static PluginDescriptionFile pluginDescriptionFile;
    public static File dataFolder;

    @Override
    public void onEnable() {
        pluginConfig = new PluginConfig(this);
        pluginLogger = this.getLogger();
        pluginDescriptionFile = this.getDescription();
        dataFolder = this.getDataFolder();

        bot = new Bot();

        registerEvents();
        registerCommands();

        Version.checkVersion();
        NameLink.load();

        Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(
                this,
                () -> bot.updatePlayerCount(),
                0L,
                20L * 30);
    }

    private void registerEvents(){
        PluginManager pm = getServer().getPluginManager();
        pm.registerEvents(new e_OnPlayerChat(), this);
        pm.registerEvents(new e_OnPlayerJoin(), this);
    }

    private void registerCommands(){
        getCommand("clearDiscordBotCache").setExecutor(new c_ClearDiscordBotCache());
        getCommand("linkDiscord").setExecutor(new c_LinkDiscord());
        getCommand("unlinkDiscord").setExecutor(new c_UnlinkDiscord());
    }

    @Override
    public void onDisable() {
        bot.shutdown();
    }
}

package de.epsdev.minecord;

import de.epsdev.minecord.bot.Bot;
import de.epsdev.minecord.commands.c_ClearDiscordBotCache;
import de.epsdev.minecord.config.PluginConfig;
import de.epsdev.minecord.events.e_OnPlayerChat;
import de.epsdev.minecord.events.e_OnPlayerJoin;
import de.epsdev.minecord.version.Version;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Level;
import java.util.logging.Logger;

public final class Minecord extends JavaPlugin {

    public static Bot bot;
    public static PluginConfig pluginConfig;
    public static Logger pluginLogger;
    public static PluginDescriptionFile pluginDescriptionFile;

    @Override
    public void onEnable() {
        pluginConfig = new PluginConfig(this);
        pluginLogger = this.getLogger();
        pluginDescriptionFile = this.getDescription();

        bot = new Bot();

        registerEvents();
        registerCommands();

        Version.checkVersion();
    }

    private void registerEvents(){
        PluginManager pm = getServer().getPluginManager();
        pm.registerEvents(new e_OnPlayerChat(), this);
        pm.registerEvents(new e_OnPlayerJoin(), this);
    }

    private void registerCommands(){
        getCommand("clearDiscordBotCache").setExecutor(new c_ClearDiscordBotCache());
    }

    @Override
    public void onDisable() {
        bot.shutdown();
    }
}

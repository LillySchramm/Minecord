package de.epsdev.minecord;

import de.epsdev.minecord.bot.Bot;
import de.epsdev.minecord.config.PluginConfig;
import de.epsdev.minecord.events.e_OnPlayerChat;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

public final class Minecord extends JavaPlugin {

    public static Bot bot;
    public static PluginConfig pluginConfig;
    public static Logger pluginLogger;

    @Override
    public void onEnable() {
        pluginConfig = new PluginConfig(this);
        pluginLogger = this.getLogger();
        bot = new Bot();

        registerEvents();
    }

    private void registerEvents(){
        PluginManager pm = getServer().getPluginManager();
        pm.registerEvents(new e_OnPlayerChat(), this);
    }

    @Override
    public void onDisable() {
        bot.shutdown();
    }
}

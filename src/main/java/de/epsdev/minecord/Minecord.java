package de.epsdev.minecord;

import de.epsdev.minecord.bot.Bot;
import de.epsdev.minecord.events.e_OnPlayerChat;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class Minecord extends JavaPlugin {

    public static Bot bot;

    @Override
    public void onEnable() {
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

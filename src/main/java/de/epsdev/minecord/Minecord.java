package de.epsdev.minecord;

import de.epsdev.minecord.bot.Bot;
import org.bukkit.plugin.java.JavaPlugin;

public final class Minecord extends JavaPlugin {

    public static Bot bot;

    @Override
    public void onEnable() {
        bot = new Bot();
    }

    @Override
    public void onDisable() {
        bot.shutdown();
    }
}

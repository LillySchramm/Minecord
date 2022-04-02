package de.epsdev.minecord.commands;

import de.epsdev.minecord.Minecord;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class c_ClearDiscordBotCache implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Minecord.bot.clearCache();
        sender.sendMessage(ChatColor.DARK_GREEN + "Cleared Bot Cache!");

        return true;
    }
}

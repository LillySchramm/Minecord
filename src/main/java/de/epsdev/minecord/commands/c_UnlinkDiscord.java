package de.epsdev.minecord.commands;

import de.epsdev.minecord.Minecord;
import de.epsdev.minecord.bot.NameLink;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class c_UnlinkDiscord implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            return false;
        }

        Player player = (Player) sender;
        String name = player.getName();

        if (!NameLink.removeLink(name)) {
            player.sendMessage(ChatColor.RED + "No link to your account existent.");
            return true;
        }

        player.sendMessage(ChatColor.DARK_GREEN + "Discord link removed.");

        return true;
    }
}
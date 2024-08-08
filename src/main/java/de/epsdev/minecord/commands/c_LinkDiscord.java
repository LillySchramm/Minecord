package de.epsdev.minecord.commands;

import de.epsdev.minecord.Minecord;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class c_LinkDiscord implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            return false;
        }

        Player player = (Player) sender;
        String name = player.getName();

        String requestId = Minecord.bot.addLinkRequest(name);

        player.sendMessage(ChatColor.DARK_GREEN +
                String.format("To confirm the link DM \"!link %s\" to the bot.", requestId));

        return true;
    }
}

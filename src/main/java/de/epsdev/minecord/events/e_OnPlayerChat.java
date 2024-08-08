package de.epsdev.minecord.events;

import de.epsdev.minecord.Minecord;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class e_OnPlayerChat implements Listener {
    @EventHandler
    void onPlayerChat(AsyncPlayerChatEvent e){
        Player player = e.getPlayer();
        String message = e.getMessage();

        Minecord.bot.sendMessage(String.format("<%s> %s", player.getDisplayName(), message));

        System.out.println(String.format("<%s> %s", player.getDisplayName(), message));
    }
}

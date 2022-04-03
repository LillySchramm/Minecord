package de.epsdev.minecord.events;

import de.epsdev.minecord.Minecord;
import de.epsdev.minecord.version.Version;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class e_OnPlayerJoin implements Listener {
    @EventHandler
    void onPlayerJoin(PlayerJoinEvent e){
        Player player = e.getPlayer();

        if (Version.isUpToDate()) {
            return;
        }

        String currentVersion = Minecord.pluginDescriptionFile.getVersion();

        TextComponent message = new TextComponent(String.format(
                "Minecord v%s is outdated! Download the latest version (v%s) ",
                currentVersion,
                Version.getLatestVersion()
        ));
        message.setColor(ChatColor.GOLD);

        TextComponent link = new TextComponent("here");
        link.setBold(true);
        link.setUnderlined(true);
        link.setColor(ChatColor.RED);
        link.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL,
                "https://github.com/EliasSchramm/Minecord/releases/latest"));

        message.addExtra(link);

        player.spigot().sendMessage(message);
    }
}

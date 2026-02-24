package me.ipapervn.vinehopper.listeners;

import me.ipapervn.vinehopper.manager.DataManager;
import me.ipapervn.vinehopper.manager.SettingsManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.UUID;

public class PlayerJoinListener implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        UUID uuid = player.getUniqueId();
        
        DataManager.setPlayerName(uuid, player.getName());
        
        if (!DataManager.hasPlayerData(uuid)) {
            DataManager.setPlayerLimit(uuid, SettingsManager.getDefaultLimit());
        }
    }
}

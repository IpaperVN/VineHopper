package me.ipapervn.vinehopper.listeners;

import com.bgsoftware.superiorskyblock.api.SuperiorSkyblockAPI;
import com.bgsoftware.superiorskyblock.api.events.IslandEnterEvent;
import com.bgsoftware.superiorskyblock.api.island.Island;
import com.bgsoftware.superiorskyblock.api.key.Key;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

public class SuperiorSkyblockListener implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onIslandEnter(IslandEnterEvent event) {
        Island island = event.getIsland();
        island.setBlockLimit(Key.of(Material.HOPPER), -1);
    }

    public static void disableHopperLimitForAllIslands() {
        SuperiorSkyblockAPI.getGrid().getIslands().forEach(island -> 
            island.setBlockLimit(Key.of(Material.HOPPER), -1)
        );
    }
}

package me.ipapervn.vinehopper.cache;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class HopperCache {

    private static final Map<UUID, Location> cache = new HashMap<>();

    public static void setHopperLocation(Player player, Location location) {
        cache.put(player.getUniqueId(), location);
    }

    public static Location getHopperLocation(Player player) {
        return cache.get(player.getUniqueId());
    }

    public static void removeHopperLocation(Player player) {
        cache.remove(player.getUniqueId());
    }
}

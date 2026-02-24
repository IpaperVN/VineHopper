package me.ipapervn.vinehopper.manager;

import me.ipapervn.vinehopper.VineHopper;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

public class DataManager {

    private static File file;
    private static FileConfiguration config;

    public static void load() {
        file = new File(VineHopper.getInstance().getDataFolder(), "data.yml");
        if (!file.exists()) {
            VineHopper.getInstance().saveResource("data.yml", false);
        }
        config = YamlConfiguration.loadConfiguration(file);
    }

    public static int getPlayerLimit(UUID uuid) {
        return config.getInt("players." + uuid + ".limit", -1);
    }

    public static void setPlayerLimit(UUID uuid, int limit) {
        config.set("players." + uuid + ".limit", limit);
        save();
    }

    public static boolean hasPlayerData(UUID uuid) {
        return config.contains("players." + uuid);
    }

    public static int getHopperCount(UUID uuid) {
        return config.getInt("players." + uuid + ".hoppers", 0);
    }

    public static void setHopperCount(UUID uuid, int count) {
        config.set("players." + uuid + ".hoppers", count);
        save();
    }

    public static void setPlayerName(UUID uuid, String name) {
        config.set("players." + uuid + ".name", name);
        save();
    }

    public static int getPickupRange(UUID uuid) {
        return config.getInt("players." + uuid + ".pickup-range", SettingsManager.getPickupRange());
    }

    public static void setPickupRange(UUID uuid, int range) {
        config.set("players." + uuid + ".pickup-range", range);
        save();
    }

    public static void addHopperLocation(UUID uuid, Location location) {
        List<String> locations = config.getStringList("players." + uuid + ".hopper-locations");
        String locString = locationToString(location);
        if (!locations.contains(locString)) {
            locations.add(locString);
            config.set("players." + uuid + ".hopper-locations", locations);
            config.set("hopper-owners." + locationToString(location), uuid.toString());
            save();
        }
    }

    public static void removeHopperLocation(UUID uuid, Location location) {
        List<String> locations = config.getStringList("players." + uuid + ".hopper-locations");
        locations.remove(locationToString(location));
        config.set("players." + uuid + ".hopper-locations", locations);
        config.set("hopper-owners." + locationToString(location), null);
        save();
    }

    public static UUID getHopperOwner(Location location) {
        String uuidString = config.getString("hopper-owners." + locationToString(location));
        if (uuidString == null) return null;
        try {
            return UUID.fromString(uuidString);
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

    private static String locationToString(Location loc) {
        return loc.getWorld().getName() + "," + loc.getBlockX() + "," + loc.getBlockY() + "," + loc.getBlockZ();
    }

    private static void save() {
        try {
            config.save(file);
        } catch (IOException e) {
            VineHopper.getInstance().getLogger().severe("Không thể lưu data.yml: " + e.getMessage());
        }
    }
}

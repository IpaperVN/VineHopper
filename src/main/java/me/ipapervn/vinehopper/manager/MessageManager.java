package me.ipapervn.vinehopper.manager;

import me.ipapervn.vinehopper.VineHopper;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

public class MessageManager {

    private static FileConfiguration messagesConfig;

    public static void load() {
        File file = new File(VineHopper.getInstance().getDataFolder(), "messages.yml");
        if (!file.exists()) {
            VineHopper.getInstance().saveResource("messages.yml", false);
        }
        messagesConfig = YamlConfiguration.loadConfiguration(file);
    }

    public static String get(String key) {
        return messagesConfig.getString("messages." + key, "");
    }

    public static String get(String key, String placeholder, String value) {
        return get(key).replace("{" + placeholder + "}", value);
    }

    public static String get(String key, String placeholder1, String value1, String placeholder2, String value2) {
        return get(key).replace("{" + placeholder1 + "}", value1).replace("{" + placeholder2 + "}", value2);
    }

    public static String get(String key, java.util.UUID playerUUID) {
        int pickupRange = DataManager.getPickupRange(playerUUID);
        return get(key).replace("{pickup-range}", String.valueOf(pickupRange));
    }
}

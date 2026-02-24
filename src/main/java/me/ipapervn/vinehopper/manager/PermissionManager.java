package me.ipapervn.vinehopper.manager;

import me.ipapervn.vinehopper.VineHopper;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

public class PermissionManager {

    private static FileConfiguration permissionsConfig;

    public static void load() {
        File file = new File(VineHopper.getInstance().getDataFolder(), "permissions.yml");
        if (!file.exists()) {
            VineHopper.getInstance().saveResource("permissions.yml", false);
        }
        permissionsConfig = YamlConfiguration.loadConfiguration(file);
    }

    public static String get(String key) {
        return permissionsConfig.getString("permissions." + key, "");
    }
}

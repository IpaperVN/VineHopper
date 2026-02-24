package me.ipapervn.vinehopper.manager;

import me.ipapervn.vinehopper.VineHopper;
import me.ipapervn.vinehopper.utils.ColorUtils;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class SettingsManager {

    private static FileConfiguration config;

    public static void load() {
        File file = new File(VineHopper.getInstance().getDataFolder(), "settings.yml");
        if (!file.exists()) {
            VineHopper.getInstance().saveResource("settings.yml", false);
        }
        config = YamlConfiguration.loadConfiguration(file);
    }

    public static int getDefaultLimit() {
        return config.getInt("settings.player-limit", 3);
    }

    @SuppressWarnings("unused")
    public static boolean isBlockWorldEnabled() {
        return config.getBoolean("block-world.enable", true);
    }

    @SuppressWarnings("unused")
    public static List<String> getBlockedWorlds() {
        return config.getStringList("block-world.worlds");
    }

    public static String getHopperDisplayName() {
        return config.getString("hopper.display_name", "<gradient:#FF6B6B:#4ECDC4>VineHopper</gradient>");
    }

    public static List<String> getHopperLore() {
        return config.getStringList("hopper.lore");
    }

    @SuppressWarnings("unused")
    public static int getTransferRate() {
        return config.getInt("hopper.transfer_rate", 8);
    }

    public static int getPickupRate() {
        return config.getInt("hopper.pickup_rate", 8);
    }

    public static int getPickupRange() {
        return config.getInt("hopper.pickup_range", 1);
    }

    public static ItemStack createCustomHopper(int amount) {
        ItemStack hopper = new ItemStack(Material.HOPPER, amount);
        ItemMeta meta = hopper.getItemMeta();

        if (meta != null) {
            meta.displayName(ColorUtils.colorize(getHopperDisplayName()));

            List<String> lore = getHopperLore();
            if (!lore.isEmpty()) {
                List<net.kyori.adventure.text.Component> coloredLore = new ArrayList<>();
                for (String line : lore) {
                    coloredLore.add(ColorUtils.colorize(line));
                }
                meta.lore(coloredLore);
            }

            hopper.setItemMeta(meta);
        }

        return hopper;
    }
}

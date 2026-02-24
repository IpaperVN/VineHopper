package me.ipapervn.vinehopper.manager;

import me.ipapervn.vinehopper.VineHopper;
import me.ipapervn.vinehopper.cache.HopperCache;
import me.ipapervn.vinehopper.utils.ColorUtils;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class GuiManager {

    private static FileConfiguration config;

    public static void load() {
        File file = new File(VineHopper.getInstance().getDataFolder(), "gui.yml");
        if (!file.exists()) {
            VineHopper.getInstance().saveResource("gui.yml", false);
        }
        config = YamlConfiguration.loadConfiguration(file);
    }

    public static void openHopperMenu(Player player, Block hopper) {
        String title = config.getString("gui.hopper-menu.title", "Quản Lý Hopper");
        int size = config.getInt("gui.hopper-menu.size", 27);

        HopperCache.setHopperLocation(player, hopper.getLocation());

        Inventory inv = Bukkit.createInventory(null, size, ColorUtils.colorize(title));

        ConfigurationSection items = config.getConfigurationSection("gui.hopper-menu.items");
        if (items != null) {
            ConfigurationSection fillSection = items.getConfigurationSection("fill");
            if (fillSection != null) {
                ItemStack fill = createItem(fillSection, null);
                for (int i = 0; i < size; i++) {
                    inv.setItem(i, fill);
                }
            }

            ConfigurationSection infoSection = items.getConfigurationSection("info");
            if (infoSection != null) {
                ItemStack info = createItem(infoSection, hopper);
                int slot = items.getInt("info.slot", 11);
                inv.setItem(slot, info);
            }

            ConfigurationSection removeSection = items.getConfigurationSection("remove");
            if (removeSection != null) {
                ItemStack remove = createItem(removeSection, null);
                int slot = items.getInt("remove.slot", 13);
                inv.setItem(slot, remove);
            }

            ConfigurationSection closeSection = items.getConfigurationSection("close");
            if (closeSection != null) {
                ItemStack close = createItem(closeSection, null);
                int slot = items.getInt("close.slot", 15);
                inv.setItem(slot, close);
            }
        }

        player.openInventory(inv);
    }

    private static ItemStack createItem(ConfigurationSection section, Block hopper) {
        String materialName = section.getString("material", "STONE");
        Material material = Material.getMaterial(materialName);
        if (material == null) material = Material.STONE;

        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();

        if (meta != null) {
            String name = section.getString("name", "");
            String ownerName = "Unknown";
            int ownerPickupRange = 1;

            if (hopper != null) {
                UUID ownerUUID = DataManager.getHopperOwner(hopper.getLocation());
                if (ownerUUID != null) {
                    String fetchedName = Bukkit.getOfflinePlayer(ownerUUID).getName();
                    ownerName = fetchedName != null ? fetchedName : "Unknown";
                    ownerPickupRange = DataManager.getPickupRange(ownerUUID);
                }

                name = name.replace("{x}", String.valueOf(hopper.getX()))
                           .replace("{y}", String.valueOf(hopper.getY()))
                           .replace("{z}", String.valueOf(hopper.getZ()))
                           .replace("{world}", hopper.getWorld().getName())
                           .replace("{owner}", ownerName)
                           .replace("{pickup-range}", String.valueOf(ownerPickupRange));
            }
            meta.displayName(ColorUtils.colorize(name));

            List<String> lore = section.getStringList("lore");
            if (!lore.isEmpty()) {
                List<Component> coloredLore = new ArrayList<>();
                for (String line : lore) {
                    if (hopper != null) {
                        line = line.replace("{x}", String.valueOf(hopper.getX()))
                                   .replace("{y}", String.valueOf(hopper.getY()))
                                   .replace("{z}", String.valueOf(hopper.getZ()))
                                   .replace("{world}", hopper.getWorld().getName())
                                   .replace("{owner}", ownerName)
                                   .replace("{pickup-range}", String.valueOf(ownerPickupRange));
                    }
                    coloredLore.add(ColorUtils.colorize(line));
                }
                meta.lore(coloredLore);
            }

            item.setItemMeta(meta);
        }

        return item;
    }
}

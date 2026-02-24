package me.ipapervn.vinehopper.listeners;

import me.ipapervn.vinehopper.cache.HopperCache;
import me.ipapervn.vinehopper.manager.DataManager;
import me.ipapervn.vinehopper.manager.HopperManager;
import me.ipapervn.vinehopper.manager.MessageManager;
import me.ipapervn.vinehopper.manager.SettingsManager;
import me.ipapervn.vinehopper.manager.TaskManager;
import me.ipapervn.vinehopper.utils.ColorUtils;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;

public class GuiListener implements Listener {

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        Component title = ColorUtils.colorize("<gradient:#FF6B6B:#4ECDC4>Quản Lý Hopper</gradient>");
        String titleString = PlainTextComponentSerializer.plainText().serialize(title);
        String viewTitle = PlainTextComponentSerializer.plainText().serialize(event.getView().title());
        
        if (!viewTitle.equals(titleString)) return;

        event.setCancelled(true);

        if (!(event.getWhoClicked() instanceof Player player)) return;

        ItemStack clicked = event.getCurrentItem();
        if (clicked == null || clicked.getType() == Material.AIR) return;

        int slot = event.getSlot();

        if (slot == 13 && clicked.getType() == Material.RED_CONCRETE) {
            Location hopperLoc = HopperCache.getHopperLocation(player);
            if (hopperLoc == null || hopperLoc.getBlock().getType() != Material.HOPPER) {
                player.closeInventory();
                return;
            }

            UUID owner = DataManager.getHopperOwner(hopperLoc);
            if (owner == null || !owner.equals(player.getUniqueId())) {
                player.sendMessage(ColorUtils.colorize(MessageManager.get("hopper-not-owner")));
                player.closeInventory();
                return;
            }

            TaskManager.stopHopperTask(hopperLoc);
            hopperLoc.getBlock().setType(Material.AIR);
            DataManager.removeHopperLocation(player.getUniqueId(), hopperLoc);
            
            player.closeInventory();
            
            ItemStack customHopper = SettingsManager.createCustomHopper(1);
            player.getInventory().addItem(customHopper);
            
            HopperManager.removeHopper(player.getUniqueId());
            player.sendMessage(ColorUtils.colorize(MessageManager.get("hopper-removed")));
        }
        else if (slot == 15 && clicked.getType() == Material.BARRIER) {
            player.closeInventory();
        }
    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event) {
        Component title = ColorUtils.colorize("<gradient:#FF6B6B:#4ECDC4>Quản Lý Hopper</gradient>");
        String titleString = PlainTextComponentSerializer.plainText().serialize(title);
        String viewTitle = PlainTextComponentSerializer.plainText().serialize(event.getView().title());
        
        if (!viewTitle.equals(titleString)) return;

        if (event.getPlayer() instanceof Player player) {
            HopperCache.removeHopperLocation(player);
        }
    }
}

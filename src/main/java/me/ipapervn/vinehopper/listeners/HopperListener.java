package me.ipapervn.vinehopper.listeners;

import me.ipapervn.vinehopper.manager.DataManager;
import me.ipapervn.vinehopper.manager.GuiManager;
import me.ipapervn.vinehopper.manager.HopperManager;
import me.ipapervn.vinehopper.manager.MessageManager;
import me.ipapervn.vinehopper.manager.SettingsManager;
import me.ipapervn.vinehopper.manager.TaskManager;
import me.ipapervn.vinehopper.utils.ColorUtils;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class HopperListener implements Listener {

    @EventHandler(priority = EventPriority.LOWEST)
    public void onBlockPlace(BlockPlaceEvent event) {
        Block block = event.getBlock();
        if (block.getType() != Material.HOPPER) return;

        Player player = event.getPlayer();
        String worldName = player.getWorld().getName();

        if (HopperManager.isWorldBlocked(worldName)) {
            return;
        }

        ItemStack item = event.getItemInHand();
        if (!isVineHopper(item)) {
            event.setCancelled(true);
            player.sendMessage(ColorUtils.colorize(MessageManager.get("hopper-craft-blocked")));
            return;
        }

        if (!HopperManager.canPlaceHopper(player.getUniqueId())) {
            event.setCancelled(true);
            player.sendMessage(ColorUtils.colorize(MessageManager.get("hopper-limit-reached")));
            return;
        }

        HopperManager.addHopper(player.getUniqueId());
        DataManager.addHopperLocation(player.getUniqueId(), block.getLocation());
        TaskManager.startHopperTask(block.getLocation(), player.getUniqueId());
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        Block block = event.getBlock();
        if (block.getType() != Material.HOPPER) return;

        if (HopperManager.isWorldBlocked(block.getWorld().getName())) {
            return;
        }

        event.setCancelled(true);
        event.getPlayer().sendMessage(ColorUtils.colorize(MessageManager.get("hopper-break-blocked")));
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        if (event.getAction() != Action.RIGHT_CLICK_BLOCK) return;
        
        Block block = event.getClickedBlock();
        if (block == null || block.getType() != Material.HOPPER) return;

        if (HopperManager.isWorldBlocked(block.getWorld().getName())) {
            return;
        }

        Player player = event.getPlayer();
        
        if (player.isSneaking()) {
            event.setCancelled(true);
            GuiManager.openHopperMenu(player, block);
        }
    }

    @EventHandler
    public void onCraftItem(CraftItemEvent event) {
        if (event.getRecipe().getResult().getType() != Material.HOPPER) return;

        event.setCancelled(true);
        if (event.getWhoClicked() instanceof Player player) {
            player.sendMessage(ColorUtils.colorize(MessageManager.get("hopper-craft-blocked")));
        }
    }

    private boolean isVineHopper(ItemStack item) {
        if (item == null || item.getType() != Material.HOPPER) return false;
        ItemMeta meta = item.getItemMeta();
        if (meta == null || !meta.hasDisplayName()) return false;

        net.kyori.adventure.text.Component displayName = meta.displayName();
        net.kyori.adventure.text.Component vineHopperName = ColorUtils.colorize(SettingsManager.getHopperDisplayName());
        
        return displayName != null && displayName.equals(vineHopperName) && meta.hasLore();
    }
}

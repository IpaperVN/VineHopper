package me.ipapervn.vinehopper.task;

import me.ipapervn.vinehopper.VineHopper;
import me.ipapervn.vinehopper.manager.SettingsManager;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Hopper;
import org.bukkit.entity.Item;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

public class HopperTask extends BukkitRunnable {

    private final Location hopperLocation;
    private final int pickupRange;

    public HopperTask(Location hopperLocation, int pickupRange) {
        this.hopperLocation = hopperLocation;
        this.pickupRange = pickupRange;
    }

    @Override
    public void run() {
        Block block = hopperLocation.getBlock();
        if (block.getType() != Material.HOPPER) {
            cancel();
            return;
        }

        Hopper hopper = (Hopper) block.getState();
        Inventory inv = hopper.getInventory();

        Chunk hopperChunk = hopperLocation.getChunk();
        int chunkX = hopperChunk.getX();
        int chunkZ = hopperChunk.getZ();

        for (int x = chunkX - pickupRange; x <= chunkX + pickupRange; x++) {
            for (int z = chunkZ - pickupRange; z <= chunkZ + pickupRange; z++) {
                Chunk chunk = hopperLocation.getWorld().getChunkAt(x, z);
                if (!chunk.isLoaded()) continue;

                for (org.bukkit.entity.Entity entity : chunk.getEntities()) {
                    if (!(entity instanceof Item item)) continue;

                    if (item.isDead() || !item.isValid()) continue;

                    ItemStack itemStack = item.getItemStack();
                    if (inv.firstEmpty() == -1 && !canAddItem(inv, itemStack)) continue;

                    inv.addItem(itemStack);
                    item.remove();
                    break;
                }
            }
        }
    }

    private boolean canAddItem(Inventory inv, ItemStack item) {
        for (ItemStack slot : inv.getContents()) {
            if (slot != null && slot.isSimilar(item) && slot.getAmount() < slot.getMaxStackSize()) {
                return true;
            }
        }
        return false;
    }

    public void start() {
        int pickupRate = SettingsManager.getPickupRate();
        runTaskTimer(VineHopper.getInstance(), pickupRate, pickupRate);
    }
}

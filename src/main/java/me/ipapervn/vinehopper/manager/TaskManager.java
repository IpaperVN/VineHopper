package me.ipapervn.vinehopper.manager;

import me.ipapervn.vinehopper.task.HopperTask;
import org.bukkit.Location;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class TaskManager {

    private static final Map<Location, HopperTask> tasks = new HashMap<>();

    public static void startHopperTask(Location location, UUID playerUUID) {
        if (tasks.containsKey(location)) return;

        int pickupRange = DataManager.getPickupRange(playerUUID);
        HopperTask task = new HopperTask(location, pickupRange);
        task.start();
        tasks.put(location, task);
    }

    public static void stopHopperTask(Location location) {
        HopperTask task = tasks.remove(location);
        if (task != null) {
            task.cancel();
        }
    }

    public static void stopAllTasks() {
        for (HopperTask task : tasks.values()) {
            task.cancel();
        }
        tasks.clear();
    }
}

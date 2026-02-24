package me.ipapervn.vinehopper.manager;

import java.util.UUID;

public class HopperManager {

    public static void load() {
        // HopperManager không cần load riêng, dữ liệu được quản lý bởi DataManager
    }

    public static int getHopperCount(UUID uuid) {
        return DataManager.getHopperCount(uuid);
    }

    public static void addHopper(UUID uuid) {
        int count = getHopperCount(uuid) + 1;
        DataManager.setHopperCount(uuid, count);
    }

    public static void removeHopper(UUID uuid) {
        int count = Math.max(0, getHopperCount(uuid) - 1);
        DataManager.setHopperCount(uuid, count);
    }

    public static boolean canPlaceHopper(UUID uuid) {
        int limit = DataManager.getPlayerLimit(uuid);
        if (limit == -1) {
            limit = SettingsManager.getDefaultLimit();
        }
        return getHopperCount(uuid) < limit;
    }

    public static boolean isWorldBlocked(String worldName) {
        if (!SettingsManager.isBlockWorldEnabled()) {
            return false;
        }
        return SettingsManager.getBlockedWorlds().contains(worldName);
    }
}

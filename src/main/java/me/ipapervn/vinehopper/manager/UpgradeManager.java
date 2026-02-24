package me.ipapervn.vinehopper.manager;

import java.util.UUID;

public class UpgradeManager {

    public static void load() {
        // UpgradeManager không cần load riêng, dữ liệu được quản lý bởi DataManager
    }

    @SuppressWarnings("unused")
    public static int getPickupRange(UUID uuid) {
        return DataManager.getPickupRange(uuid);
    }

    public static void setPickupRange(UUID uuid, int range) {
        DataManager.setPickupRange(uuid, range);
    }
}

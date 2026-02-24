package me.ipapervn.vinehopper.expansion;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import me.ipapervn.vinehopper.VineHopper;
import me.ipapervn.vinehopper.manager.DataManager;
import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.NotNull;

public class VineHopperExpansion extends PlaceholderExpansion {

    private final VineHopper plugin;

    public VineHopperExpansion(VineHopper plugin) {
        this.plugin = plugin;
    }

    @Override
    public @NotNull String getIdentifier() {
        return "vh";
    }

    @Override
    public @NotNull String getAuthor() {
        return "iPaperVN";
    }

    @Override
    public @NotNull String getVersion() {
        return plugin.getPluginMeta().getVersion();
    }

    @Override
    public boolean persist() {
        return true;
    }

    @Override
    public String onRequest(OfflinePlayer player, @NotNull String params) {
        if (player == null) return "";

        return switch (params.toLowerCase()) {
            case "pickup_range", "pickuprange", "range" ->
                String.valueOf(DataManager.getPickupRange(player.getUniqueId()));
            case "hopper_count", "hoppercount", "hoppers" ->
                String.valueOf(DataManager.getHopperCount(player.getUniqueId()));
            case "hopper_limit", "hopperlimit", "limit" -> {
                int limit = DataManager.getPlayerLimit(player.getUniqueId());
                yield String.valueOf(limit == -1 ? "∞" : limit);
            }
            default -> null;
        };
    }
}

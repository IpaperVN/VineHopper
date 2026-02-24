package me.ipapervn.vinehopper;

import me.ipapervn.vinehopper.expansion.VineHopperExpansion;
import me.ipapervn.vinehopper.listeners.GuiListener;
import me.ipapervn.vinehopper.listeners.HopperListener;
import me.ipapervn.vinehopper.listeners.PlayerJoinListener;
import me.ipapervn.vinehopper.listeners.SuperiorSkyblockListener;
import me.ipapervn.vinehopper.manager.CommandManager;
import me.ipapervn.vinehopper.manager.DataManager;
import me.ipapervn.vinehopper.manager.GuiManager;
import me.ipapervn.vinehopper.manager.HopperManager;
import me.ipapervn.vinehopper.manager.MessageManager;
import me.ipapervn.vinehopper.manager.PermissionManager;
import me.ipapervn.vinehopper.manager.SettingsManager;
import me.ipapervn.vinehopper.manager.TaskManager;
import me.ipapervn.vinehopper.manager.UpgradeManager;
import me.ipapervn.vinehopper.utils.ColorUtils;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class VineHopper extends JavaPlugin {

    private static VineHopper instance;

    @Override
    public void onEnable() {
        instance = this;
        SettingsManager.load();
        DataManager.load();
        HopperManager.load();
        GuiManager.load();
        MessageManager.load();
        PermissionManager.load();
        UpgradeManager.load();
        
        Bukkit.getPluginManager().registerEvents(new PlayerJoinListener(), this);
        Bukkit.getPluginManager().registerEvents(new HopperListener(), this);
        Bukkit.getPluginManager().registerEvents(new GuiListener(), this);
        
        if (Bukkit.getPluginManager().getPlugin("SuperiorSkyblock2") != null) {
            Bukkit.getPluginManager().registerEvents(new SuperiorSkyblockListener(), this);
            Bukkit.getScheduler().runTaskLater(this, SuperiorSkyblockListener::disableHopperLimitForAllIslands, 20L);
        }
        
        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
            new VineHopperExpansion(this).register();
        }
        
        var command = getCommand("vinehopper");
        if (command != null) {
            CommandManager commandManager = new CommandManager();
            command.setExecutor(commandManager);
            command.setTabCompleter(commandManager);
        }
        
        ColorUtils.sendMultiline(Bukkit.getConsoleSender(),
            "================================",
            "  VineHopper v" + getPluginMeta().getVersion(),
            "  Tác giả: " + getPluginMeta().getAuthors(),
            "  Trạng thái: KÍCH HOẠT",
            "================================"
        );
    }

    @Override
    public void onDisable() {
        TaskManager.stopAllTasks();
        ColorUtils.sendMultiline(Bukkit.getConsoleSender(),
            "================================",
            "  VineHopper đã tắt!",
            "================================"
        );
    }

    public static VineHopper getInstance() {
        return instance;
    }
}

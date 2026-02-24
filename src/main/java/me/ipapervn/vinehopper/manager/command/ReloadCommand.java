package me.ipapervn.vinehopper.manager.command;

import me.ipapervn.vinehopper.VineHopper;
import me.ipapervn.vinehopper.manager.DataManager;
import me.ipapervn.vinehopper.manager.HopperManager;
import me.ipapervn.vinehopper.manager.MessageManager;
import me.ipapervn.vinehopper.manager.PermissionManager;
import me.ipapervn.vinehopper.manager.SettingsManager;
import me.ipapervn.vinehopper.utils.ColorUtils;
import org.bukkit.command.CommandSender;

public class ReloadCommand {

    public void execute(CommandSender sender) {
        if (!sender.hasPermission(PermissionManager.get("reload"))) {
            sender.sendMessage(ColorUtils.colorize(MessageManager.get("no-permission")));
            return;
        }

        VineHopper.getInstance().reloadConfig();
        SettingsManager.load();
        DataManager.load();
        HopperManager.load();
        MessageManager.load();
        PermissionManager.load();
        sender.sendMessage(ColorUtils.colorize(MessageManager.get("reload-success")));
    }
}

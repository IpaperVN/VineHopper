package me.ipapervn.vinehopper.manager.command;

import me.ipapervn.vinehopper.manager.DataManager;
import me.ipapervn.vinehopper.manager.MessageManager;
import me.ipapervn.vinehopper.manager.PermissionManager;
import me.ipapervn.vinehopper.utils.ColorUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SetLimitCommand {

    public void execute(CommandSender sender, String[] args) {
        if (!sender.hasPermission(PermissionManager.get("setlimit"))) {
            sender.sendMessage(ColorUtils.colorize(MessageManager.get("no-permission")));
            return;
        }

        if (args.length < 3) {
            sender.sendMessage(ColorUtils.colorize(MessageManager.get("setlimit-usage")));
            return;
        }

        Player target = Bukkit.getPlayer(args[1]);
        if (target == null) {
            sender.sendMessage(ColorUtils.colorize(MessageManager.get("player-not-found", "player", args[1])));
            return;
        }

        try {
            int limit = Integer.parseInt(args[2]);
            DataManager.setPlayerLimit(target.getUniqueId(), limit);
            sender.sendMessage(ColorUtils.colorize(MessageManager.get("setlimit-success", "player", target.getName(), "limit", String.valueOf(limit))));
        } catch (NumberFormatException e) {
            sender.sendMessage(ColorUtils.colorize(MessageManager.get("setlimit-invalid")));
        }
    }
}

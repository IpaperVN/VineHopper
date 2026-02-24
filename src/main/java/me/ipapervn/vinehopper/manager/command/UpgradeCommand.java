package me.ipapervn.vinehopper.manager.command;

import me.ipapervn.vinehopper.manager.MessageManager;
import me.ipapervn.vinehopper.manager.UpgradeManager;
import me.ipapervn.vinehopper.utils.ColorUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public class UpgradeCommand {

    public void execute(CommandSender sender, String[] args) {
        if (!(sender instanceof ConsoleCommandSender)) {
            sender.sendMessage(ColorUtils.colorize(MessageManager.get("upgrade-console-only")));
            return;
        }

        if (args.length < 4) {
            sender.sendMessage(ColorUtils.colorize(MessageManager.get("upgrade-usage")));
            return;
        }

        if (!args[1].equalsIgnoreCase("pickup-range")) {
            sender.sendMessage(ColorUtils.colorize(MessageManager.get("upgrade-invalid-type")));
            return;
        }

        Player target = Bukkit.getPlayer(args[2]);
        if (target == null) {
            sender.sendMessage(ColorUtils.colorize(MessageManager.get("player-not-found", "player", args[2])));
            return;
        }

        int range;
        try {
            range = Integer.parseInt(args[3]);
        } catch (NumberFormatException e) {
            sender.sendMessage(ColorUtils.colorize(MessageManager.get("upgrade-invalid-range")));
            return;
        }

        if (range < 1 || range > 4) {
            sender.sendMessage(ColorUtils.colorize(MessageManager.get("upgrade-range-limit")));
            return;
        }

        UpgradeManager.setPickupRange(target.getUniqueId(), range);
        sender.sendMessage(ColorUtils.colorize(MessageManager.get("upgrade-success", "player", target.getName(), "range", String.valueOf(range))));
    }
}

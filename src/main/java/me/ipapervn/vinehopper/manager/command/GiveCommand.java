package me.ipapervn.vinehopper.manager.command;

import me.ipapervn.vinehopper.manager.MessageManager;
import me.ipapervn.vinehopper.manager.PermissionManager;
import me.ipapervn.vinehopper.manager.SettingsManager;
import me.ipapervn.vinehopper.utils.ColorUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class GiveCommand {

    public void execute(CommandSender sender, String[] args) {
        if (!sender.hasPermission(PermissionManager.get("give"))) {
            sender.sendMessage(ColorUtils.colorize(MessageManager.get("no-permission")));
            return;
        }

        if (args.length < 3) {
            sender.sendMessage(ColorUtils.colorize(MessageManager.get("give-usage")));
            return;
        }

        Player target = Bukkit.getPlayer(args[1]);
        if (target == null) {
            sender.sendMessage(ColorUtils.colorize(MessageManager.get("player-not-found", "player", args[1])));
            return;
        }

        try {
            int amount = Integer.parseInt(args[2]);
            if (amount <= 0) {
                sender.sendMessage(ColorUtils.colorize(MessageManager.get("give-invalid-amount")));
                return;
            }

            ItemStack hopper = SettingsManager.createCustomHopper(amount);
            target.getInventory().addItem(hopper);
            
            sender.sendMessage(ColorUtils.colorize(MessageManager.get("give-success-sender", "player", target.getName(), "amount", String.valueOf(amount))));
            target.sendMessage(ColorUtils.colorize(MessageManager.get("give-success-receiver", "amount", String.valueOf(amount))));
        } catch (NumberFormatException e) {
            sender.sendMessage(ColorUtils.colorize(MessageManager.get("give-invalid-amount")));
        }
    }
}

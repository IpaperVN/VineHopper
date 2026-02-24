package me.ipapervn.vinehopper.manager;

import me.ipapervn.vinehopper.VineHopper;
import me.ipapervn.vinehopper.manager.command.GiveCommand;
import me.ipapervn.vinehopper.manager.command.ReloadCommand;
import me.ipapervn.vinehopper.manager.command.SetLimitCommand;
import me.ipapervn.vinehopper.manager.command.UpgradeCommand;
import me.ipapervn.vinehopper.utils.ColorUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class CommandManager implements CommandExecutor, TabCompleter {

    private final ReloadCommand reloadCommand;
    private final SetLimitCommand setLimitCommand;
    private final GiveCommand giveCommand;
    private final UpgradeCommand upgradeCommand;

    public CommandManager() {
        this.reloadCommand = new ReloadCommand();
        this.setLimitCommand = new SetLimitCommand();
        this.giveCommand = new GiveCommand();
        this.upgradeCommand = new UpgradeCommand();
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String @NotNull [] args) {
        if (args.length == 0) {
            sender.sendMessage(ColorUtils.colorize(MessageManager.get("help-header", "version", VineHopper.getInstance().getPluginMeta().getVersion())));
            sender.sendMessage(ColorUtils.colorize(MessageManager.get("help-reload")));
            sender.sendMessage(ColorUtils.colorize(MessageManager.get("help-setlimit")));
            sender.sendMessage(ColorUtils.colorize(MessageManager.get("help-give")));
            sender.sendMessage(ColorUtils.colorize(MessageManager.get("help-upgrade")));
            return true;
        }

        switch (args[0].toLowerCase()) {
            case "reload":
                reloadCommand.execute(sender);
                break;
            case "setlimit":
                setLimitCommand.execute(sender, args);
                break;
            case "give":
                giveCommand.execute(sender, args);
                break;
            case "upgrade":
                upgradeCommand.execute(sender, args);
                break;
            default:
                sender.sendMessage(ColorUtils.colorize(MessageManager.get("command-not-found")));
        }
        return true;
    }

    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String @NotNull [] args) {
        List<String> completions = new ArrayList<>();
        
        if (args.length == 1) {
            String input = args[0].toLowerCase();
            if ("reload".startsWith(input)) completions.add("reload");
            if ("setlimit".startsWith(input)) completions.add("setlimit");
            if ("give".startsWith(input)) completions.add("give");
            if ("upgrade".startsWith(input)) completions.add("upgrade");
        } else if (args[0].equalsIgnoreCase("setlimit")) {
            if (args.length == 2) {
                return null;
            } else if (args.length == 3) {
                completions.add("<số>");
            }
        } else if (args[0].equalsIgnoreCase("give")) {
            if (args.length == 2) {
                return null;
            } else if (args.length == 3) {
                completions.add("<amount>");
            }
        } else if (args[0].equalsIgnoreCase("upgrade")) {
            if (args.length == 2) {
                completions.add("pickup-range");
            } else if (args.length == 3) {
                return null;
            } else if (args.length == 4) {
                completions.add("<1-4>");
            }
        }
        
        return completions;
    }
}

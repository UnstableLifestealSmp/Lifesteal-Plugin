package com.unstable.lifestealranks.rank;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class RankCommand implements CommandExecutor, TabCompleter {

    private final RankManager rankManager;

    public RankCommand(RankManager rankManager) {
        this.rankManager = rankManager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0) {
            sender.sendMessage(Component.text("Usage: /rank <list|check|give|remove>").color(NamedTextColor.RED));
            return true;
        }

        String subcommand = args[0].toLowerCase();

        switch (subcommand) {
            case "list":
                sender.sendMessage(Component.text("Available ranks:").color(NamedTextColor.GOLD));
                for (Rank rank : Rank.values()) {
                    sender.sendMessage(Component.text("  - " + rank.getDisplayName()).color(NamedTextColor.GRAY));
                }
                return true;

            case "check":
                if (args.length < 2) {
                    sender.sendMessage(Component.text("Usage: /rank check <player>").color(NamedTextColor.RED));
                    return true;
                }
                Player target = Bukkit.getPlayer(args[1]);
                if (target == null) {
                    sender.sendMessage(Component.text("Player not found").color(NamedTextColor.RED));
                    return true;
                }
                Rank rank = rankManager.getRank(target.getUniqueId());
                sender.sendMessage(Component.text(target.getName() + " has rank: " + rank.getDisplayName()).color(NamedTextColor.GREEN));
                return true;

            case "give":
                if (!sender.hasPermission("lsr.admin")) {
                    sender.sendMessage(Component.text("No permission").color(NamedTextColor.RED));
                    return true;
                }
                if (args.length < 3) {
                    sender.sendMessage(Component.text("Usage: /rank give <player> <rank>").color(NamedTextColor.RED));
                    return true;
                }
                target = Bukkit.getPlayer(args[1]);
                if (target == null) {
                    sender.sendMessage(Component.text("Player not found").color(NamedTextColor.RED));
                    return true;
                }
                Rank newRank = Rank.fromString(args[2]);
                rankManager.setRank(target.getUniqueId(), newRank);
                sender.sendMessage(Component.text("Gave " + target.getName() + " rank: " + newRank.getDisplayName()).color(NamedTextColor.GREEN));
                return true;

            case "remove":
                if (!sender.hasPermission("lsr.admin")) {
                    sender.sendMessage(Component.text("No permission").color(NamedTextColor.RED));
                    return true;
                }
                if (args.length < 2) {
                    sender.sendMessage(Component.text("Usage: /rank remove <player>").color(NamedTextColor.RED));
                    return true;
                }
                target = Bukkit.getPlayer(args[1]);
                if (target == null) {
                    sender.sendMessage(Component.text("Player not found").color(NamedTextColor.RED));
                    return true;
                }
                rankManager.removeRank(target.getUniqueId());
                sender.sendMessage(Component.text("Removed rank from " + target.getName()).color(NamedTextColor.GREEN));
                return true;

            default:
                sender.sendMessage(Component.text("Unknown subcommand").color(NamedTextColor.RED));
                return true;
        }
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        List<String> completions = new ArrayList<>();

        if (args.length == 1) {
            completions.add("list");
            completions.add("check");
            if (sender.hasPermission("lsr.admin")) {
                completions.add("give");
                completions.add("remove");
            }
        } else if (args.length == 2 && (args[0].equalsIgnoreCase("check") || args[0].equalsIgnoreCase("give") || args[0].equalsIgnoreCase("remove"))) {
            for (Player player : Bukkit.getOnlinePlayers()) {
                completions.add(player.getName());
            }
        } else if (args.length == 3 && args[0].equalsIgnoreCase("give")) {
            for (Rank rank : Rank.values()) {
                completions.add(rank.getDisplayName());
            }
        }

        return completions;
    }
}
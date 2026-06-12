package com.unstable.lifestealranks.aura;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class AuraCommand implements CommandExecutor, TabCompleter {

    private final AuraManager auraManager;

    public AuraCommand(AuraManager auraManager) {
        this.auraManager = auraManager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(Component.text("Only players can use this command").color(NamedTextColor.RED));
            return true;
        }

        Player player = (Player) sender;

        if (args.length == 0) {
            // Open aura menu
            sender.sendMessage(Component.text("Opening aura menu...").color(NamedTextColor.GOLD));
            // Would open inventory here
            return true;
        }

        String subcommand = args[0].toLowerCase();

        switch (subcommand) {
            case "toggle":
                auraManager.toggleAura(player.getUniqueId());
                boolean enabled = auraManager.isAuraEnabled(player.getUniqueId());
                player.sendMessage(Component.text("Aura ").append(Component.text(enabled ? "enabled" : "disabled")).color(NamedTextColor.GREEN));
                return true;

            case "set":
                if (args.length < 2) {
                    player.sendMessage(Component.text("Usage: /aura set <aura>").color(NamedTextColor.RED));
                    return true;
                }
                String auraName = args[1].toUpperCase().replace("-", "_");
                try {
                    AuraType aura = AuraType.valueOf(auraName);
                    auraManager.setSelectedAura(player.getUniqueId(), aura);
                    player.sendMessage(Component.text("Selected aura: ").append(Component.text(aura.getDisplayName())).color(NamedTextColor.GREEN));
                } catch (IllegalArgumentException e) {
                    player.sendMessage(Component.text("Unknown aura").color(NamedTextColor.RED));
                }
                return true;

            default:
                player.sendMessage(Component.text("Unknown subcommand").color(NamedTextColor.RED));
                return true;
        }
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        List<String> completions = new ArrayList<>();

        if (args.length == 1) {
            completions.add("toggle");
            completions.add("set");
        } else if (args.length == 2 && args[0].equalsIgnoreCase("set")) {
            for (AuraType aura : AuraType.values()) {
                completions.add(aura.name().toLowerCase().replace("_", "-"));
            }
        }

        return completions;
    }
}
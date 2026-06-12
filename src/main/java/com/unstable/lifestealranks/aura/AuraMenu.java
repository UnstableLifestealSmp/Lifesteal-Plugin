package com.unstable.lifestealranks.aura;

import com.unstable.lifestealranks.rank.Rank;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class AuraMenu {

    private static final int SIZE = 54;
    private static final int TOGGLE_SLOT = 49;
    private static final int INFO_SLOT = 4;
    private static final int[] AURA_SLOTS = {10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34};

    public static Inventory createMenu(Player player, AuraManager auraManager, Rank playerRank) {
        Inventory inv = Bukkit.createInventory(null, SIZE, Component.text("Auras").color(NamedTextColor.LIGHT_PURPLE));

        // Fill border with purple glass
        ItemStack purpleGlass = new ItemStack(Material.PURPLE_STAINED_GLASS_PANE);
        ItemMeta meta = purpleGlass.getItemMeta();
        meta.displayName(Component.empty());
        purpleGlass.setItemMeta(meta);

        for (int i = 0; i < SIZE; i++) {
            if (i < 9 || i % 9 == 0 || i % 9 == 8 || i >= 45) {
                inv.setItem(i, purpleGlass);
            }
        }

        // Info item
        ItemStack infoItem = new ItemStack(Material.BOOK);
        ItemMeta infoMeta = infoItem.getItemMeta();
        infoMeta.displayName(Component.text("Aura Info").color(NamedTextColor.GOLD));
        infoItem.setItemMeta(infoMeta);
        inv.setItem(INFO_SLOT, infoItem);

        // Toggle button
        boolean auraEnabled = auraManager.isAuraEnabled(player.getUniqueId());
        ItemStack toggleItem = new ItemStack(auraEnabled ? Material.LIME_CONCRETE : Material.RED_CONCRETE);
        ItemMeta toggleMeta = toggleItem.getItemMeta();
        toggleMeta.displayName(Component.text(auraEnabled ? "Enabled" : "Disabled").color(auraEnabled ? NamedTextColor.GREEN : NamedTextColor.RED));
        toggleItem.setItemMeta(toggleMeta);
        inv.setItem(TOGGLE_SLOT, toggleItem);

        // Add aura items
        int slotIndex = 0;
        for (AuraType aura : AuraType.values()) {
            if (slotIndex >= AURA_SLOTS.length) break;

            boolean hasRank = playerRank.ordinal() >= aura.getRequiredRank().ordinal();
            ItemStack auraItem = new ItemStack(hasRank ? aura.getIcon() : Material.RED_STAINED_GLASS_PANE);
            ItemMeta auraMeta = auraItem.getItemMeta();
            auraMeta.displayName(Component.text(aura.getDisplayName()).color(hasRank ? NamedTextColor.WHITE : NamedTextColor.RED));
            auraMeta.setLore(java.util.List.of(
                    Component.text(aura.getDescription()).color(NamedTextColor.GRAY),
                    Component.text("Rank: " + aura.getRequiredRank().getDisplayName()).color(NamedTextColor.YELLOW),
                    Component.text(hasRank ? "Click to select" : "Locked").color(hasRank ? NamedTextColor.GREEN : NamedTextColor.RED)
            ));
            auraItem.setItemMeta(auraMeta);
            inv.setItem(AURA_SLOTS[slotIndex], auraItem);
            slotIndex++;
        }

        return inv;
    }
}
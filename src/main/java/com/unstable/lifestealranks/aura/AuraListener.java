package com.unstable.lifestealranks.aura;

import com.unstable.lifestealranks.rank.RankManager;
import io.papermc.paper.threadedregions.scheduler.GlobalRegionScheduler;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class AuraListener implements Listener {

    private final JavaPlugin plugin;
    private final AuraManager auraManager;
    private final Map<UUID, Long> lastRenderTick = new HashMap<>();

    public AuraListener(JavaPlugin plugin, AuraManager auraManager) {
        this.plugin = plugin;
        this.auraManager = auraManager;
        startAuraRenderer();
    }

    private void startAuraRenderer() {
        GlobalRegionScheduler scheduler = plugin.getServer().getAsyncScheduler().getScheduler();
        plugin.getServer().getGlobalRegionScheduler().runAtFixedRate(plugin, task -> {
            long tickOffset = System.currentTimeMillis() / 50; // Convert to ticks

            for (Player player : plugin.getServer().getOnlinePlayers()) {
                UUID uuid = player.getUniqueId();
                if (!auraManager.isAuraEnabled(uuid)) continue;

                AuraType aura = auraManager.getSelectedAura(uuid);
                if (aura != null) {
                    AuraRenderer.render(player, aura, tickOffset);
                }
            }
        }, 0, 1); // Run every tick (20 ticks/second)
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (!(event.getWhoClicked() instanceof Player)) return;
        Player player = (Player) event.getWhoClicked();
        Inventory inv = event.getInventory();

        if (!inv.getHolder() == null) return; // Only handle menus without holders

        int slot = event.getSlot();
        event.setCancelled(true);

        // Toggle button
        if (slot == 49) {
            auraManager.toggleAura(player.getUniqueId());
            player.sendMessage(Component.text("Aura ").append(Component.text(auraManager.isAuraEnabled(player.getUniqueId()) ? "enabled" : "disabled")).color(NamedTextColor.GREEN));
            return;
        }

        // Aura selection
        int auraIndex = -1;
        int[] auraSlots = {10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34};
        for (int i = 0; i < auraSlots.length; i++) {
            if (auraSlots[i] == slot) {
                auraIndex = i;
                break;
            }
        }

        if (auraIndex >= 0 && auraIndex < AuraType.values().length) {
            AuraType[] auras = AuraType.values();
            AuraType selectedAura = auras[auraIndex];
            auraManager.setSelectedAura(player.getUniqueId(), selectedAura);
            player.sendMessage(Component.text("Selected aura: ").append(Component.text(selectedAura.getDisplayName())).color(NamedTextColor.GREEN));
        }
    }
}
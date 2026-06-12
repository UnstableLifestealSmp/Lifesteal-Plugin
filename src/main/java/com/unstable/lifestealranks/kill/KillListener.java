package com.unstable.lifestealranks.kill;

import com.unstable.lifestealranks.rank.Rank;
import com.unstable.lifestealranks.rank.RankManager;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class KillListener implements Listener {

    private final JavaPlugin plugin;
    private final RankManager rankManager;
    private final Map<Rank, List<String>> deathMessages = new HashMap<>();
    private final Random random = new Random();

    public KillListener(JavaPlugin plugin, RankManager rankManager) {
        this.plugin = plugin;
        this.rankManager = rankManager;
        loadDeathMessages();
    }

    private void loadDeathMessages() {
        deathMessages.put(Rank.DEFAULT, List.of(
                "was eliminated by",
                "fell to",
                "lost to"
        ));
        deathMessages.put(Rank.VIP, List.of(
                "was destroyed by the VIP",
                "got outplayed by [VIP]",
                "couldn't stand against [VIP]"
        ));
        deathMessages.put(Rank.VIP_PLUS, List.of(
                "was crushed by the VIP+",
                "bowed before [VIP+]",
                "couldn't compete with [VIP+]"
        ));
        deathMessages.put(Rank.MVP, List.of(
                "was annihilated by the MVP",
                "got outmatched by [MVP]",
                "fell before the power of [MVP]"
        ));
        deathMessages.put(Rank.MVP_PLUS, List.of(
                "was obliterated by the MVP+",
                "was no match for [MVP+]",
                "got crushed by the unstoppable [MVP+]"
        ));
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        Player victim = event.getEntity();
        Player killer = victim.getKiller();

        if (killer == null) return; // Not a PvP kill

        // Suppress vanilla death message
        event.deathMessage(null);

        Rank killerRank = rankManager.getRank(killer.getUniqueId());
        Location deathLoc = victim.getLocation();

        // Broadcast custom death message
        String deathMsg = deathMessages.get(killerRank).get(random.nextInt(deathMessages.get(killerRank).size()));
        deathMsg = deathMsg.replace("[VIP]", killerRank.getDisplayName())
                .replace("[VIP+]", killerRank.getDisplayName())
                .replace("[MVP]", killerRank.getDisplayName())
                .replace("[MVP+]", killerRank.getDisplayName());
        plugin.getServer().broadcast(Component.text(victim.getName() + " " + deathMsg + " " + killer.getName()).color(NamedTextColor.RED));

        // Spawn particles at death location
        deathLoc.getWorld().spawnParticle(org.bukkit.Particle.EXPLOSION, deathLoc, 50, 0.5, 0.5, 0.5, 1);

        // Play world sound
        deathLoc.getWorld().playSound(deathLoc, org.bukkit.Sound.ENTITY_WITHER_DEATH, 1.0f, 1.0f);

        // Play sound to killer
        killer.playSound(killer, org.bukkit.Sound.ENTITY_PLAYER_LEVELUP, 1.0f, 1.0f);

        // Send title to killer
        killer.showTitle(net.kyori.adventure.title.Title.title(
                Component.text("KILL!").color(NamedTextColor.RED),
                Component.text(victim.getName()).color(NamedTextColor.YELLOW)
        ));

        // Send action bar to victim
        victim.sendActionBar(Component.text("You were killed by " + killer.getName()).color(NamedTextColor.RED));
    }
}
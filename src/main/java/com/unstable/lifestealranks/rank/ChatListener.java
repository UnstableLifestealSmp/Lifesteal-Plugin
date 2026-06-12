package com.unstable.lifestealranks.rank;

import io.papermc.paper.event.player.AsyncChatEvent;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.entity.Player;

public class ChatListener implements Listener {

    private final RankManager rankManager;

    public ChatListener(RankManager rankManager) {
        this.rankManager = rankManager;
    }

    @EventHandler
    public void onAsyncChat(AsyncChatEvent event) {
        Player player = event.getPlayer();
        Rank rank = rankManager.getRank(player.getUniqueId());

        if (rank != Rank.DEFAULT) {
            Component prefix = rank.getPrefix();
            Component message = event.message();
            Component colored = prefix.append(player.name().color(NamedTextColor.WHITE)).append(Component.text(": ")).append(message);
            event.renderer((source, sourceDisplayName, message1, viewer) -> colored);
        }
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        Rank rank = rankManager.getRank(player.getUniqueId());

        if (rank != Rank.DEFAULT) {
            Component displayName = rank.getPrefix().append(player.name());
            player.displayName(displayName);
            player.playerListName(displayName);
        }
    }
}
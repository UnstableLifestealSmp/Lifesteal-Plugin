package com.unstable.lifestealranks;

import com.unstable.lifestealranks.rank.RankManager;
import com.unstable.lifestealranks.rank.ChatListener;
import com.unstable.lifestealranks.rank.RankCommand;
import com.unstable.lifestealranks.aura.AuraManager;
import com.unstable.lifestealranks.aura.AuraListener;
import com.unstable.lifestealranks.aura.AuraCommand;
import com.unstable.lifestealranks.kill.KillListener;
import org.bukkit.plugin.java.JavaPlugin;

public class LifeStealRanks extends JavaPlugin {

    private RankManager rankManager;
    private AuraManager auraManager;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        getLogger().info("Loading LifeStealRanks v2.0.0...");

        // Initialize managers
        rankManager = new RankManager(this);
        auraManager = new AuraManager(this);

        // Register listeners
        getServer().getPluginManager().registerEvents(new ChatListener(rankManager), this);
        getServer().getPluginManager().registerEvents(new AuraListener(this, auraManager), this);
        getServer().getPluginManager().registerEvents(new KillListener(this, rankManager), this);

        // Register commands
        getCommand("rank").setExecutor(new RankCommand(rankManager));
        getCommand("aura").setExecutor(new AuraCommand(auraManager));

        getLogger().info("LifeStealRanks enabled!");
    }

    @Override
    public void onDisable() {
        getLogger().info("LifeStealRanks disabled!");
    }

    public RankManager getRankManager() {
        return rankManager;
    }

    public AuraManager getAuraManager() {
        return auraManager;
    }
}
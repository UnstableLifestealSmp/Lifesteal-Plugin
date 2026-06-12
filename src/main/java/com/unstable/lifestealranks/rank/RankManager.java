package com.unstable.lifestealranks.rank;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class RankManager {

    private final JavaPlugin plugin;
    private final File playerDataFile;
    private FileConfiguration playerDataConfig;
    private final Map<UUID, Rank> rankCache = new HashMap<>();

    public RankManager(JavaPlugin plugin) {
        this.plugin = plugin;
        this.playerDataFile = new File(plugin.getDataFolder(), "playerdata.yml");
        loadPlayerData();
    }

    private void loadPlayerData() {
        if (!playerDataFile.exists()) {
            try {
                plugin.getDataFolder().mkdirs();
                playerDataFile.createNewFile();
            } catch (Exception e) {
                plugin.getLogger().severe("Failed to create playerdata.yml: " + e.getMessage());
            }
        }
        playerDataConfig = YamlConfiguration.loadConfiguration(playerDataFile);
        rankCache.clear();

        for (String uuidStr : playerDataConfig.getKeys(false)) {
            try {
                UUID uuid = UUID.fromString(uuidStr);
                String rankStr = playerDataConfig.getString(uuidStr, "DEFAULT");
                rankCache.put(uuid, Rank.fromString(rankStr));
            } catch (IllegalArgumentException e) {
                plugin.getLogger().warning("Invalid UUID in playerdata.yml: " + uuidStr);
            }
        }
    }

    public Rank getRank(UUID uuid) {
        return rankCache.getOrDefault(uuid, Rank.DEFAULT);
    }

    public void setRank(UUID uuid, Rank rank) {
        rankCache.put(uuid, rank);
        playerDataConfig.set(uuid.toString(), rank.name().replace("_PLUS", "+"));
        savePlayerData();
    }

    private void savePlayerData() {
        try {
            playerDataConfig.save(playerDataFile);
        } catch (Exception e) {
            plugin.getLogger().severe("Failed to save playerdata.yml: " + e.getMessage());
        }
    }

    public void removeRank(UUID uuid) {
        rankCache.remove(uuid);
        playerDataConfig.set(uuid.toString(), null);
        savePlayerData();
    }
}
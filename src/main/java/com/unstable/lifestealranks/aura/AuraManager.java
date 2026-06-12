package com.unstable.lifestealranks.aura;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class AuraManager {

    private final JavaPlugin plugin;
    private final File aurDataFile;
    private FileConfiguration auraDataConfig;
    private final Map<UUID, AuraData> auraCache = new HashMap<>();

    public static class AuraData {
        public AuraType selectedAura;
        public boolean enabled;

        public AuraData(AuraType selectedAura, boolean enabled) {
            this.selectedAura = selectedAura;
            this.enabled = enabled;
        }
    }

    public AuraManager(JavaPlugin plugin) {
        this.plugin = plugin;
        this.aurDataFile = new File(plugin.getDataFolder(), "auradata.yml");
        loadAuraData();
    }

    private void loadAuraData() {
        if (!aurDataFile.exists()) {
            try {
                plugin.getDataFolder().mkdirs();
                aurDataFile.createNewFile();
            } catch (Exception e) {
                plugin.getLogger().severe("Failed to create auradata.yml: " + e.getMessage());
            }
        }
        auraDataConfig = YamlConfiguration.loadConfiguration(aurDataFile);
        auraCache.clear();

        for (String uuidStr : auraDataConfig.getKeys(false)) {
            try {
                UUID uuid = UUID.fromString(uuidStr);
                String auraStr = auraDataConfig.getString(uuidStr + ".aura", "");
                boolean enabled = auraDataConfig.getBoolean(uuidStr + ".enabled", true);

                AuraType aura = null;
                if (!auraStr.isEmpty()) {
                    try {
                        aura = AuraType.valueOf(auraStr);
                    } catch (IllegalArgumentException ignored) {
                    }
                }

                if (aura != null) {
                    auraCache.put(uuid, new AuraData(aura, enabled));
                }
            } catch (IllegalArgumentException e) {
                plugin.getLogger().warning("Invalid UUID in auradata.yml: " + uuidStr);
            }
        }
    }

    public AuraType getSelectedAura(UUID uuid) {
        AuraData data = auraCache.get(uuid);
        return data != null ? data.selectedAura : null;
    }

    public boolean isAuraEnabled(UUID uuid) {
        AuraData data = auraCache.get(uuid);
        return data != null && data.enabled;
    }

    public void setSelectedAura(UUID uuid, AuraType aura) {
        auraCache.put(uuid, new AuraData(aura, true));
        auraDataConfig.set(uuid.toString() + ".aura", aura.name());
        auraDataConfig.set(uuid.toString() + ".enabled", true);
        saveAuraData();
    }

    public void toggleAura(UUID uuid) {
        AuraData data = auraCache.get(uuid);
        if (data != null) {
            data.enabled = !data.enabled;
            auraDataConfig.set(uuid.toString() + ".enabled", data.enabled);
            saveAuraData();
        }
    }

    private void saveAuraData() {
        try {
            auraDataConfig.save(aurDataFile);
        } catch (Exception e) {
            plugin.getLogger().severe("Failed to save auradata.yml: " + e.getMessage());
        }
    }
}
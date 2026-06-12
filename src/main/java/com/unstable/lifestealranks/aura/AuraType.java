package com.unstable.lifestealranks.aura;

import com.unstable.lifestealranks.rank.Rank;
import org.bukkit.Material;
import org.bukkit.Particle;

public enum AuraType {
    // VIP Tier
    EMERALD_NATURE("Emerald Nature", "Green nature aura", Material.EMERALD, Rank.VIP, AuraShape.RING, Particle.HAPPY_VILLAGER, 15, 0.8, 0.3),
    INFERNO("Inferno", "Burning fire aura", Material.FIRE_CHARGE, Rank.VIP, AuraShape.PILLAR, Particle.FLAME, 20, 0.6, 1.5),

    // VIP+ Tier
    LOVE_STORM("Love Storm", "Romantic pink aura", Material.PINK_CONCRETE, Rank.VIP_PLUS, AuraShape.CLOUD, Particle.HEART, 25, 1.0, 0.5),
    BLIZZARD("Blizzard", "Icy spiral aura", Material.ICE, Rank.VIP_PLUS, AuraShape.HELIX, Particle.SNOWFLAKE, 18, 0.7, 1.8),

    // MVP Tier
    ARCANE("Arcane", "Mystical purple aura", Material.AMETHYST_CLUSTER, Rank.MVP, AuraShape.ORBIT, Particle.PORTAL, 20, 0.9, 0.7),
    DRAGON_SOUL("Dragon Soul", "Dragon-like aura", Material.DRAGON_EGG, Rank.MVP, AuraShape.CLOUD, Particle.DRAGON_BREATH, 15, 0.8, 0.4),
    RAINBOW("Rainbow", "Colorful gradient aura", Material.RAINBOW_CONCRETE, Rank.MVP, AuraShape.RING, Particle.NOTE, 30, 1.0, 0.3),

    // MVP+ Tier
    SOUL_FIRE("Soul Fire", "Blue soul flames", Material.SOUL_FIRE, Rank.MVP_PLUS, AuraShape.PILLAR, Particle.SOUL_FIRE_FLAME, 22, 0.7, 1.6),
    VOID("Void", "Dark void aura", Material.CRYING_OBSIDIAN, Rank.MVP_PLUS, AuraShape.HELIX, Particle.REVERSE_PORTAL, 20, 0.8, 2.0),
    UNDYING("Undying", "Resurrection energy", Material.WITHER_ROSE, Rank.MVP_PLUS, AuraShape.ORBIT, Particle.ENTITY_EFFECT, 25, 0.9, 0.8),
    WITHER_KING("Wither King", "Wither boss aura", Material.WITHER_SKELETON_SKULL, Rank.MVP_PLUS, AuraShape.CLOUD, Particle.SMOKE, 28, 1.1, 0.6);

    private final String displayName;
    private final String description;
    private final Material icon;
    private final Rank requiredRank;
    private final AuraShape shape;
    private final Particle particle;
    private final int particleCount;
    private final double radius;
    private final double height;

    AuraType(String displayName, String description, Material icon, Rank requiredRank, AuraShape shape, Particle particle, int particleCount, double radius, double height) {
        this.displayName = displayName;
        this.description = description;
        this.icon = icon;
        this.requiredRank = requiredRank;
        this.shape = shape;
        this.particle = particle;
        this.particleCount = particleCount;
        this.radius = radius;
        this.height = height;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getDescription() {
        return description;
    }

    public Material getIcon() {
        return icon;
    }

    public Rank getRequiredRank() {
        return requiredRank;
    }

    public AuraShape getShape() {
        return shape;
    }

    public Particle getParticle() {
        return particle;
    }

    public int getParticleCount() {
        return particleCount;
    }

    public double getRadius() {
        return radius;
    }

    public double getHeight() {
        return height;
    }
}
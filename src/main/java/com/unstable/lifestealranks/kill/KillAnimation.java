package com.unstable.lifestealranks.kill;

import com.unstable.lifestealranks.rank.Rank;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.configuration.ConfigurationSection;

import java.util.List;

public class KillAnimation {

    private final Rank rank;
    private final Sound sound;
    private final float volume;
    private final float pitch;
    private final Particle particle;
    private final int particleCount;
    private final double spread;
    private final String titleLine1;
    private final String titleLine2;

    public KillAnimation(Rank rank, ConfigurationSection config) {
        this.rank = rank;

        // Sound config
        String soundName = config.getString("sound.type", "ENTITY_PLAYER_LEVELUP");
        this.sound = Sound.valueOf(soundName);
        this.volume = (float) config.getDouble("sound.volume", 1.0);
        this.pitch = (float) config.getDouble("sound.pitch", 1.0);

        // Particle config
        String particleName = config.getString("particles.type", "FLAME");
        this.particle = Particle.valueOf(particleName);
        this.particleCount = config.getInt("particles.count", 20);
        this.spread = config.getDouble("particles.spread", 0.5);

        // Title config
        this.titleLine1 = config.getString("title.line1", "You've been slayed!");
        this.titleLine2 = config.getString("title.line2", "by " + rank.getDisplayName());
    }

    public Rank getRank() {
        return rank;
    }

    public Sound getSound() {
        return sound;
    }

    public float getVolume() {
        return volume;
    }

    public float getPitch() {
        return pitch;
    }

    public Particle getParticle() {
        return particle;
    }

    public int getParticleCount() {
        return particleCount;
    }

    public double getSpread() {
        return spread;
    }

    public String getTitleLine1() {
        return titleLine1;
    }

    public String getTitleLine2() {
        return titleLine2;
    }
}
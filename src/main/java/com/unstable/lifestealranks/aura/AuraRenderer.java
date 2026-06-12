package com.unstable.lifestealranks.aura;

import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Player;

import java.util.Random;

public class AuraRenderer {

    private static final Random RANDOM = new Random();

    public static void render(Player player, AuraType aura, long tickOffset) {
        if (aura == null) return;
        if (player.isSneaking() || player.isInvisible()) return;

        Location loc = player.getLocation().add(0, 0, 0);
        double radius = aura.getRadius();
        double height = aura.getHeight();
        Particle particle = aura.getParticle();
        int count = aura.getParticleCount();

        switch (aura.getShape()) {
            case RING:
                renderRing(loc, radius, particle, count, tickOffset);
                break;
            case PILLAR:
                renderPillar(loc, radius, height, particle, count, tickOffset);
                break;
            case CLOUD:
                renderCloud(loc, radius, height, particle, count);
                break;
            case HELIX:
                renderHelix(loc, radius, height, particle, count, tickOffset);
                break;
            case ORBIT:
                renderOrbit(loc, radius, particle, count, tickOffset);
                break;
        }
    }

    private static void renderRing(Location center, double radius, Particle particle, int count, long tickOffset) {
        double angle = (tickOffset % 360) * 0.01745329251; // Convert to radians
        double speed = 0.1;

        for (int i = 0; i < count; i++) {
            double theta = (2 * Math.PI * i) / count + angle;
            double x = center.getX() + Math.cos(theta) * radius;
            double z = center.getZ() + Math.sin(theta) * radius;
            Location particleLoc = new Location(center.getWorld(), x, center.getY(), z);
            center.getWorld().spawnParticle(particle, particleLoc, 0, 0, 0, 0, speed);
        }
    }

    private static void renderPillar(Location center, double radius, double height, Particle particle, int count, long tickOffset) {
        double angle = (tickOffset % 360) * 0.01745329251;

        for (int i = 0; i < count; i++) {
            double yProgress = (i / (double) count);
            double y = center.getY() + (yProgress * height);
            double theta = angle + (yProgress * Math.PI * 4);
            double x = center.getX() + Math.cos(theta) * (radius * yProgress);
            double z = center.getZ() + Math.sin(theta) * (radius * yProgress);
            Location particleLoc = new Location(center.getWorld(), x, y, z);
            center.getWorld().spawnParticle(particle, particleLoc, 0);
        }
    }

    private static void renderCloud(Location center, double radius, double height, Particle particle, int count) {
        for (int i = 0; i < count; i++) {
            double x = center.getX() + (RANDOM.nextDouble() - 0.5) * radius * 2;
            double y = center.getY() + (RANDOM.nextDouble() * height);
            double z = center.getZ() + (RANDOM.nextDouble() - 0.5) * radius * 2;
            Location particleLoc = new Location(center.getWorld(), x, y, z);
            center.getWorld().spawnParticle(particle, particleLoc, 0);
        }
    }

    private static void renderHelix(Location center, double radius, double height, Particle particle, int count, long tickOffset) {
        double angle = (tickOffset % 360) * 0.01745329251;

        for (int i = 0; i < count; i++) {
            double yProgress = (i / (double) count);
            double y = center.getY() + (yProgress * height);

            // First strand
            double theta1 = angle + (yProgress * Math.PI * 4);
            double x1 = center.getX() + Math.cos(theta1) * radius;
            double z1 = center.getZ() + Math.sin(theta1) * radius;
            Location particleLoc1 = new Location(center.getWorld(), x1, y, z1);
            center.getWorld().spawnParticle(particle, particleLoc1, 0);

            // Second strand (opposite side)
            double theta2 = theta1 + Math.PI;
            double x2 = center.getX() + Math.cos(theta2) * radius;
            double z2 = center.getZ() + Math.sin(theta2) * radius;
            Location particleLoc2 = new Location(center.getWorld(), x2, y, z2);
            center.getWorld().spawnParticle(particle, particleLoc2, 0);
        }
    }

    private static void renderOrbit(Location center, double radius, Particle particle, int count, long tickOffset) {
        double angle = (tickOffset % 360) * 0.01745329251;
        double midHeight = 0.75;

        for (int i = 0; i < count; i++) {
            double theta = (2 * Math.PI * i) / count + angle;
            double x = center.getX() + Math.cos(theta) * radius;
            double z = center.getZ() + Math.sin(theta) * radius;
            Location particleLoc = new Location(center.getWorld(), x, center.getY() + midHeight, z);
            center.getWorld().spawnParticle(particle, particleLoc, 0);
        }
    }
}
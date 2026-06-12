package com.unstable.lifestealranks.rank;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;

public enum Rank {
    DEFAULT("Default", Component.empty(), 10, 0),
    VIP("VIP", Component.text("[VIP] ").color(NamedTextColor.GREEN), 3, 5),
    VIP_PLUS("VIP+", Component.text("[VIP+] ").color(NamedTextColor.AQUA), 5, 12),
    MVP("MVP", Component.text("[MVP] ").color(NamedTextColor.LIGHT_PURPLE), 7, 20),
    MVP_PLUS("MVP+", Component.text("[MVP+] ").color(NamedTextColor.GOLD), 10, 35);

    private final String displayName;
    private final Component prefix;
    private final int maxHomes;
    private final double price;

    Rank(String displayName, Component prefix, int maxHomes, double price) {
        this.displayName = displayName;
        this.prefix = prefix;
        this.maxHomes = maxHomes;
        this.price = price;
    }

    public String getDisplayName() {
        return displayName;
    }

    public Component getPrefix() {
        return prefix;
    }

    public int getMaxHomes() {
        return maxHomes;
    }

    public double getPrice() {
        return price;
    }

    public static Rank fromString(String name) {
        try {
            return Rank.valueOf(name.toUpperCase().replace("+", "_PLUS"));
        } catch (IllegalArgumentException e) {
            return DEFAULT;
        }
    }
}
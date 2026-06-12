# LifeStealRanks v2

**A comprehensive rank, aura, and kill animation system for Folia 1.21.11**

## Features

### Rank System
- 5 rank tiers: DEFAULT, VIP, VIP+, MVP, MVP+
- Configurable max homes and pricing per rank
- Colored chat prefixes and display names
- Persistent player rank storage
- Admin commands: `/rank give`, `/rank remove`, `/rank check`, `/rank list`

### Aura System
- 12 unique auras across rank tiers
- 5 particle shapes: RING, PILLAR, CLOUD, HELIX, ORBIT
- Animated rendering with tick-based offsets
- Sneaking suppression (auras hide while sneaking)
- Inventory-based GUI menu for aura selection
- Toggle on/off per player
- Commands: `/aura`, `/aura toggle`, `/aura set <aura>`

### Kill System
- Rank-specific kill animations
- Custom death messages per rank
- Particle effects at death location
- World sound effects
- Personal killer sounds (layered by rank)
- Title card display to killer
- Action bar message to victim

## Architecture

```
com.unstable.lifestealranks/
├── LifeStealRanks.java          (Main plugin class)
├── rank/
│   ├── Rank.java                (Enum: DEFAULT, VIP, VIP+, MVP, MVP+)
│   ├── RankManager.java         (Persistence & caching)
│   ├── ChatListener.java        (Chat prefix injection, display names)
│   └── RankCommand.java         (Commands & tab completion)
├── aura/
│   ├── AuraType.java            (Enum: all 12 auras with properties)
│   ├── AuraShape.java           (Enum: RING, PILLAR, CLOUD, HELIX, ORBIT)
│   ├── AuraRenderer.java        (Particle rendering logic)
│   ├── AuraManager.java         (Persistence & selection)
│   ├── AuraMenu.java            (GUI inventory)
│   ├── AuraListener.java        (Rendering scheduler & menu clicks)
│   └── AuraCommand.java         (Commands & tab completion)
├── kill/
│   ├── KillAnimation.java       (Animation config per rank)
│   └── KillListener.java        (Death event handler)
└── util/
    └── (Text helpers as needed)
```

## Configuration

Edit `config.yml` to customize:
- Aura rendering radius and interval
- Per-rank kill animation (sound, particles, title)

## Build

```bash
mvn clean package
```

Copy the JAR to your Folia server's `plugins/` folder.

## Requirements

- Java 21+
- Folia 1.21.1+ (Paper fork for async regions)
- Maven 3.8+

## License

MIT

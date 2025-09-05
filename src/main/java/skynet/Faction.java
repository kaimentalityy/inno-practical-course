package skynet;

import lombok.Getter;

import java.util.*;

/**
 * Represents a faction competing to build robots.
 * Each faction collects parts from the factory and uses them
 * to build complete robots consisting of head, torso, hand, and feet.
 */

@Getter
public class Faction {
    private final String name;
    private final Factory factory;
    private final List<Part> collectedParts = new ArrayList<>();
    private final int maxPartsPerNight;

    /**
     * Creates a new faction.
     *
     * @param name            the name of the faction
     * @param factory         the factory producing parts
     * @param maxPartsPerNight the maximum parts the faction can collect each night
     */
    public Faction(String name, Factory factory, int maxPartsPerNight) {
        this.name = name;
        this.factory = factory;
        this.maxPartsPerNight = maxPartsPerNight;
    }

    /**
     * Collects available parts from the factory up to the allowed maximum.
     */
    public void collectParts() {
        List<Part> taken = factory.takeParts(maxPartsPerNight);
        collectedParts.addAll(taken);
    }

    /**
     * Calculates the number of complete robots built from collected parts.
     * A robot requires one of each part type.
     *
     * @return the number of complete robots built
     */
    public int getCompleteRobots() {
        Map<Part, Long> partCount = new EnumMap<>(Part.class);
        for (Part part : collectedParts) {
            partCount.put(part, partCount.getOrDefault(part, 0L) + 1);
        }
        return partCount.size() < Part.values().length
                ? 0
                : Collections.min(partCount.values()).intValue();
    }
}


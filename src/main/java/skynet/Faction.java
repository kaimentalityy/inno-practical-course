package skynet;

import lombok.Getter;

import java.util.*;

@Getter
public class Faction {
    private final String name;
    private final Factory factory;
    private final List<Part> collectedParts = new ArrayList<>();
    private final int maxPartsPerNight;

    public Faction(String name, Factory factory, int maxPartsPerNight) {
        this.name = name;
        this.factory = factory;
        this.maxPartsPerNight = maxPartsPerNight;
    }

    public void collectParts() {
        List<Part> taken = factory.takeParts(maxPartsPerNight);
        collectedParts.addAll(taken);
    }

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


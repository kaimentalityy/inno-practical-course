package skynet;

import java.util.*;

public class Factory {
    private final List<Part> parts = new ArrayList<>();

    public synchronized void produceParts(Random random) {
        int count = random.nextInt(1, 11);
        for (int i = 0; i < count; i++) {
            parts.add(Part.values()[random.nextInt(Part.values().length)]);
        }
    }

    public synchronized List<Part> takeParts(int maxParts) {
        List<Part> taken = new ArrayList<>();
        for (int i = 0; i < maxParts && !parts.isEmpty(); i++) {
            taken.add(parts.remove(0));
        }
        return taken;
    }

    public List<Part> getParts() {
        return new ArrayList<>(parts);
    }
}
package skynet;

import java.util.*;

/**
 * Represents a neutral factory that produces robot parts.
 * Parts are produced randomly, and factions collect them each night.
 */
public class Factory {
    private final List<Part> parts = new ArrayList<>();

    /**
     * Produces up to 10 random parts and adds them to the inventory.
     *
     * @param random the random generator used for producing parts
     */
    public synchronized void produceParts(Random random) {
        int count = random.nextInt(1, 11);
        for (int i = 0; i < count; i++) {
            parts.add(Part.values()[random.nextInt(Part.values().length)]);
        }
    }

    /**
     * Allows a faction to take up to a maximum number of parts.
     * Parts are removed from the factory inventory in FIFO order.
     *
     * @param maxParts the maximum number of parts to take
     * @return the list of parts taken
     */
    public synchronized List<Part> takeParts(int maxParts) {
        List<Part> taken = new ArrayList<>();
        for (int i = 0; i < maxParts && !parts.isEmpty(); i++) {
            taken.add(parts.remove(0));
        }
        return taken;
    }

    /**
     * Returns a snapshot of the current parts in the factory.
     *
     * @return a list of parts currently in stock
     */
    public List<Part> getParts() {
        return new ArrayList<>(parts);
    }
}
package skynet;

import java.util.*;

/**
 * Represents a neutral factory that produces and stores robot parts.
 * <p>
 * The factory operates on a day/night cycle:
 * <ul>
 *   <li>During the day, parts are produced and added to the inventory.</li>
 *   <li>During the night, factions collect parts from the inventory.</li>
 * </ul>
 * Thread coordination ensures that production and collection
 * happen in alternating cycles without overlap.
 */
public class Factory {
    private final List<Part> parts = new ArrayList<>();
    private boolean day = true;

    /**
     * Produces between 1 and 10 random robot parts and adds them to the inventory.
     * This method blocks if it is currently night, waiting until it becomes day.
     *
     * @param random the random generator used for producing parts
     */
    public synchronized void produceParts(Random random) {
        while (!day) {
            try {
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        int count = random.nextInt(1, 11);
        for (int i = 0; i < count; i++) {
            parts.add(Part.values()[random.nextInt(Part.values().length)]);
        }

        System.out.println("Produced " + count + " parts: " + parts);
        day = false;
        notifyAll();
    }

    /**
     * Allows a faction to take up to a maximum number of parts from the inventory.
     * Parts are removed in FIFO order. This method blocks if it is currently day,
     * waiting until it becomes night.
     *
     * @param maxParts the maximum number of parts to take
     * @return a list of parts taken, up to the requested maximum or fewer if inventory is low
     */
    public synchronized List<Part> takeParts(int maxParts) {
        while (day) {
            try {
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        List<Part> taken = new ArrayList<>();
        for (int i = 0; i < maxParts && !parts.isEmpty(); i++) {
            taken.add(parts.remove(0));
        }

        System.out.println("Taken " + taken.size() + " parts: " + taken);
        day = true;
        notifyAll();
        return taken;
    }

    /**
     * Adds the given parts directly to the factory inventory.
     * Used for testing purposes only.
     */
    public synchronized void produceFixedParts(List<Part> newParts) {
        parts.addAll(newParts);
    }


    /**
     * Returns a snapshot of the current parts in the factory.
     *
     * @return a list of parts currently in stock
     */
    public synchronized List<Part> getParts() {
        return List.copyOf(parts);
    }

}
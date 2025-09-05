package skynet;

import java.util.Random;


/**
 * Main simulation class for the Robot War.
 * Simulates 100 days of part production and collection
 * and determines which faction builds the most robots.
 */
public class RobotWarSimulation {
    /**
     * Runs the Robot War simulation.
     * Factory produces parts, factions collect them,
     * and the winner is determined by the number of complete robots built.
     *
     * @param args command-line arguments (not used)
     */
    public static void main(String[] args) {
        Factory factory = new Factory();
        Faction world = new Faction("World", factory, 5);
        Faction wednesday = new Faction("Wednesday", factory, 5);
        Random random = new Random();

        for (int day = 1; day <= 100; day++) {
            factory.produceParts(random);
            world.collectParts();
            wednesday.collectParts();
        }

        int worldRobots = world.getCompleteRobots();
        int wednesdayRobots = wednesday.getCompleteRobots();

        System.out.println("World built " + worldRobots + " robots.");
        System.out.println("Wednesday built " + wednesdayRobots + " robots.");

        if (worldRobots > wednesdayRobots) {
            System.out.println("Winner: World!");
        } else if (wednesdayRobots > worldRobots) {
            System.out.println("Winner: Wednesday!");
        } else {
            System.out.println("It's a tie!");
        }
    }
}

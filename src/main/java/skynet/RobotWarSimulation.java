package skynet;

import java.util.Random;

public class RobotWarSimulation {
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

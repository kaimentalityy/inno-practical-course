package skynet;

import java.util.Random;
import java.util.concurrent.CountDownLatch;

/**
 * Runs the Robot War simulation with concurrent threads and explicit day/night phases.
 */
public class RobotWarSimulation {

    private final Factory factory;
    private final Faction world;
    private final Faction wednesday;
    private final Random random;
    private static final int DAYS = 100;

    public RobotWarSimulation(long seed) {
        this.factory = new Factory();
        this.world = new Faction("World", factory, 5);
        this.wednesday = new Faction("Wednesday", factory, 5);
        this.random = new Random(seed);
    }

    /**
     * Runs the simulation for a fixed number of days.
     */
    public void runSimulation() throws InterruptedException {
        for (int day = 1; day <= DAYS; day++) {
            CountDownLatch dayDone = new CountDownLatch(1);
            CountDownLatch nightDone = new CountDownLatch(2);

            Thread producer = new Thread(() -> {
                factory.produceParts(random);
                dayDone.countDown();
            });

            Thread worldCollector = new Thread(() -> {
                try {
                    dayDone.await();
                    world.collectParts();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                } finally {
                    nightDone.countDown();
                }
            });

            Thread wednesdayCollector = new Thread(() -> {
                try {
                    dayDone.await();
                    wednesday.collectParts();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                } finally {
                    nightDone.countDown();
                }
            });

            producer.start();
            worldCollector.start();
            wednesdayCollector.start();

            nightDone.await();
        }

        printResults();
    }

    private void printResults() {
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

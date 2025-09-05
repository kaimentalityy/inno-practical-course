

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import skynet.Faction;
import skynet.Factory;
import skynet.Part;
import skynet.RobotWarSimulation;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class RobotWarSimulationTest {

    private Factory factory;
    private Faction world;
    private Faction wednesday;

    @BeforeEach
    void setUp() {
        factory = new Factory();
        world = new Faction("World", factory, 5);
        wednesday = new Faction("Wednesday", factory, 5);
    }

    @Test
    void testFactoryProducesParts() {
        Random fixedRandom = new Random(0);
        factory.produceParts(fixedRandom);
        List<Part> parts = factory.getParts();
        assertFalse(parts.isEmpty());
        assertTrue(parts.size() <= 10);
    }

    @Test
    void testFactionCollectsUpToMaxParts() {
        factory.produceFixedParts(Arrays.asList(Part.HEAD, Part.TORSO, Part.HAND, Part.FEET, Part.HEAD));
        world.collectParts();
        assertEquals(5, world.getCollectedParts().size());
    }

    @Test
    void testCompleteRobotsCalculation() {
        factory.produceFixedParts(Arrays.asList(Part.HEAD, Part.TORSO, Part.HAND, Part.FEET,
                Part.HEAD, Part.TORSO, Part.HAND, Part.FEET));
        world.collectParts();
        assertEquals(2, world.getCompleteRobots());
    }

    @Test
    void testWinnerDetermination() {
        factory.produceFixedParts(Arrays.asList(Part.HEAD, Part.TORSO, Part.HAND, Part.FEET,
                Part.HEAD, Part.TORSO, Part.HAND, Part.FEET));
        world.collectParts();
        factory.produceFixedParts(Arrays.asList(Part.HEAD, Part.TORSO, Part.HAND, Part.FEET));
        wednesday.collectParts();
        assertTrue(world.getCompleteRobots() > wednesday.getCompleteRobots());
    }

    @Test
    void testFullSimulationRunsWithoutErrors() throws InterruptedException {
        RobotWarSimulation simulation = new RobotWarSimulation(42L);
        simulation.runSimulation();
    }
}

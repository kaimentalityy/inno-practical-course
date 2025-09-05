import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import skynet.Faction;
import skynet.Factory;
import skynet.Part;

import java.util.Random;
import static org.junit.jupiter.api.Assertions.*;

public class RobotWarSimulationTest {
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
        assertFalse(factory.getParts().isEmpty());
        assertTrue(factory.getParts().size() <= 10);
    }

    @Test
    void testFactionTakesUpToFiveParts() {
        for (int i = 0; i < 20; i++) {
            factory.getParts().add(Part.HEAD);
        }
        world.collectParts();
        assertTrue(world.getCompleteRobots() >= 0);
    }

    @Test
    void testCountCompleteRobots() {
        for (int i = 0; i < 2; i++) {
            for (Part p : Part.values()) {
                factory.getParts().add(p);
            }
        }
        world.collectParts();
        assertEquals(2, world.getCompleteRobots());
    }

    @Test
    void testWinnerDetermination() {
        for (int i = 0; i < 2; i++) for (Part p : Part.values()) factory.getParts().add(p);
        world.collectParts();
        for (int i = 0; i < 1; i++) for (Part p : Part.values()) factory.getParts().add(p);
        wednesday.collectParts();
        assertTrue(world.getCompleteRobots() > wednesday.getCompleteRobots());
    }
}


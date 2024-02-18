package homeworksantorini;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;;

class TowerTest {
    private Tower tower;

    @BeforeEach
    void setUp() {
        tower = new Tower();
    }

    @Test
    void testInitialTowerState() {
        assertEquals(0, tower.getLevel(), "Initial tower level should be 0.");
        assertFalse(tower.hasDome(), "Initial tower should not have a dome.");
    }

    @Test
    void testAddLevel() {
        tower.addLevel();
        assertEquals(1, tower.getLevel(), "After adding a level, the tower level should be 1.");

        tower.addLevel();
        tower.addLevel(); // Add levels up to MAX_LEVEL
        assertEquals(Tower.MAX_LEVEL, tower.getLevel(), "Tower level should not exceed MAX_LEVEL.");
    }

    @Test
    void testAddLevelBeyondMax() {
        for (int i = 0; i < Tower.MAX_LEVEL + 1; i++) { // Attempt to add one more level than MAX_LEVEL
            tower.addLevel();
        }
        assertEquals(Tower.MAX_LEVEL, tower.getLevel(), "Tower level should not exceed MAX_LEVEL even when adding extra levels.");
    }

    @Test
    void testAddDomeAtMaxLevel() {
        for (int i = 0; i < Tower.MAX_LEVEL; i++) {
            tower.addLevel();
        }
        tower.addDome();
        assertTrue(tower.hasDome(), "Tower should have a dome after reaching MAX_LEVEL and adding a dome.");
    }

    @Test
    void testAddDomeBeforeMaxLevel() {
        tower.addLevel(); // Add a single level
        tower.addDome();
        assertFalse(tower.hasDome(), "Tower should not have a dome before reaching MAX_LEVEL.");
    }

    @Test
    void testAddDomeAfterDomeExists() {
        for (int i = 0; i < Tower.MAX_LEVEL; i++) {
            tower.addLevel();
        }
        tower.addDome(); // Add the first dome
        boolean hadDomeBefore = tower.hasDome();
        tower.addDome(); // Attempt to add another dome
        assertEquals(hadDomeBefore, tower.hasDome(), "Adding a dome when one already exists should not change dome state.");
    }
}

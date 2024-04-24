package homeworksantorini;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;;

public class CellTest {
    private Cell cell;

    @BeforeEach
    public void setUp() {
        cell = new Cell(0, 0);
    }

    @Test
    public void testHasWorker() {
        assertFalse(cell.hasWorker());
        cell.setWorker(new Worker(new Player("player1")));
        assertTrue(cell.hasWorker());
    }

    @Test
    public void testHasDome() {
        assertFalse(cell.hasDome());
        cell.getTower().addLevel();
        cell.getTower().addLevel();
        cell.getTower().addLevel();
        cell.getTower().addDome();
        assertTrue(cell.hasDome());
    }

    @Test
    public void testGetTowerLevel() {
        assertEquals(0, cell.getTowerLevel());
        cell.getTower().addLevel();
        assertEquals(1, cell.getTowerLevel());
    }
}
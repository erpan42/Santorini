package homeworksantorini;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;;

public class GridTest {
    private Grid grid;
    private final int magic5 = 5;
    private final int magic8 = 8;

    @BeforeEach
    public void setUp() {
        grid = new Grid();
    }

    @Test
    public void testGetCell() {
        assertNotNull(grid.getCell(0, 0));
        assertNull(grid.getCell(-1, 0));
        assertNull(grid.getCell(0, magic5));
    }

    @Test
    public void testIsValidMove() {
        Cell fromCell = grid.getCell(0, 0);
        Cell toCell = grid.getCell(0, 1);
        assertTrue(grid.isValidMove(fromCell, toCell, false));
    }

    @Test
    public void testIsValidBuild() {
        Cell workerCell = grid.getCell(0, 0);
        Cell targetCell = grid.getCell(0, 1);
        assertTrue(grid.isValidBuild(workerCell, targetCell));
    }

    @Test
    public void testGetAdjacentCells() {
        Cell cell = grid.getCell(2, 2);
        List<Cell> adjacentCells = grid.getAdjacentCells(cell);
        assertEquals(magic8, adjacentCells.size());
    }
}
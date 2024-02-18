package homeworksantorini;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;;

class GridTest {
    private Grid grid;

    @BeforeEach
    void setUp() {
        grid = new Grid();
    }

    @Test
    void testGridInitialization() {
        for (int x = 0; x < Grid.GRID_SIZE; x++) {
            for (int y = 0; y < Grid.GRID_SIZE; y++) {
                assertNotNull(grid.getCell(x, y), "Cell at (" + x + "," + y + ") should not be null.");
            }
        }
    }

    private static final int CELL_Y = 3;

    @Test
    void testGetCellValidCoordinates() {
        Cell cell = grid.getCell(2, CELL_Y);
        assertNotNull(cell, "Cell at valid coordinates (2," + CELL_Y + ") should not be null.");
        assertEquals(2, cell.getX(), "Cell X coordinate should be 2.");
        assertEquals(CELL_Y, cell.getY(), "Cell Y coordinate should be " + CELL_Y + ".");
    }

    private static final int INVALID_X = -1;
    private static final int INVALID_Y = 5;

    @Test
    void testGetCellInvalidCoordinates() {
        assertNull(grid.getCell(INVALID_X, 2), "Cell at invalid coordinates (" + INVALID_X + ",2) should be null.");
        assertNull(grid.getCell(INVALID_Y, INVALID_Y), "Cell at invalid coordinates (" + INVALID_Y + "," + INVALID_Y + ") should be null.");
    }

    @Test
    void testIsValidMove() {
        Cell fromCell = grid.getCell(1, 1);
        Cell toCell = grid.getCell(1, 2); // Adjacent cell
        assertTrue(grid.isValidMove(fromCell, toCell), "Move to an adjacent, unoccupied cell should be valid.");

        Cell occupiedCell = grid.getCell(2, 2);
        Worker worker = new Worker(new Player("Player1", grid), grid);
        occupiedCell.setWorker(worker); // Simulate an occupied cell
        assertFalse(grid.isValidMove(fromCell, occupiedCell), "Move to an occupied cell should be invalid.");
    }

    @Test
    void testIsValidBuild() {
        Cell workerCell = grid.getCell(1, 1);
        Cell targetCell = grid.getCell(1, 2); // Adjacent cell
        assertTrue(grid.isValidBuild(workerCell, targetCell), "Building on an adjacent, unoccupied cell should be valid.");

        Cell domeCell = grid.getCell(2, 2);
        domeCell.getTower().addLevel();
        domeCell.getTower().addLevel();
        domeCell.getTower().addLevel(); // Tower is at maximum level
        domeCell.getTower().addDome(); // Add a dome
        assertFalse(grid.isValidBuild(workerCell, domeCell), "Building on a cell with a dome should be invalid.");
    }

    private static final int CELL1_X = 2;
    private static final int CELL1_Y = 2;
    private static final int CELL2_X = 3;
    private static final int CELL2_Y = 3;

    @Test
    void testIsAdjacent() {
        Cell cell1 = grid.getCell(CELL1_X, CELL1_Y);
        Cell cell2 = grid.getCell(CELL2_X, CELL2_Y);
        assertTrue(grid.isAdjacent(cell1, cell2), "Cells (" + CELL1_X + "," + CELL1_Y + ") and (" + CELL2_X + "," + CELL2_Y + ") should be adjacent.");

        Cell cell3 = grid.getCell(0, 0);
        assertFalse(grid.isAdjacent(cell1, cell3), "Cells (" + CELL1_X + "," + CELL1_Y + ") and (0,0) should not be adjacent.");
    }
}

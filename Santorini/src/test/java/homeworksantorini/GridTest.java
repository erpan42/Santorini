package homeworksantorini;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;;

class GridTest {
    private Grid grid;
    private final int xCoordinate = 2;
    private final int yCoordinate = 3;
    private final int magic5 = 5;
    private final int magic8 = 8;

    public int getYCoordinate() {
        return yCoordinate;
    }

    public int getYCoordinate5() {
        return magic5;
    }

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

    @Test
    void testGetCellValidCoordinates() {
        Cell cell = grid.getCell(xCoordinate, yCoordinate);
        assertNotNull(cell, "Cell at valid coordinates (" + xCoordinate + "," + yCoordinate + ") should not be null.");
        assertEquals(xCoordinate, cell.getX(), "Cell X coordinate should be " + xCoordinate + ".");
        assertEquals(yCoordinate, cell.getY(), "Cell Y coordinate should be " + yCoordinate + ".");
    }

    @Test
    void testGetCellInvalidCoordinates() {
        assertNull(grid.getCell(-1, 2), "Cell at invalid coordinates (-1,2) should be null.");
        assertNull(grid.getCell(magic5, magic5), "Cell at invalid coordinates (5,5) should be null.");
    }

    @Test
    void testGetAdjacentCells() {
        Cell cell = grid.getCell(2, 2);
        List<Cell> adjacentCells = grid.getAdjacentCells(cell);
        assertEquals(magic8, adjacentCells.size(), "There should be 8 adjacent cells for a cell not on the edge.");

        // Verifying one of the adjacent cells
        assertTrue(adjacentCells.contains(grid.getCell(yCoordinate, yCoordinate)), "Cell at (3,3) should be in the list of adjacent cells for cell at (2,2).");
    }

    @Test
    void testGetAdjacentCellsEdgeCase() {
        Cell cell = grid.getCell(0, 0);
        List<Cell> adjacentCells = grid.getAdjacentCells(cell);
        assertEquals(yCoordinate, adjacentCells.size(), "There should be 3 adjacent cells for a cell at the corner (0,0).");

        // Verifying one of the adjacent cells
        assertTrue(adjacentCells.contains(grid.getCell(1, 0)), "Cell at (1,0) should be in the list of adjacent cells for cell at (0,0).");
    }
}
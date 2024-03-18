package homeworksantorini;

import java.util.ArrayList;
import java.util.List;

public class Grid {
    public static final int GRID_SIZE = 5;
    private Cell[][] cells = new Cell[GRID_SIZE][GRID_SIZE];

    public Grid() {
        for (int x = 0; x < GRID_SIZE; x++) {
            for (int y = 0; y < GRID_SIZE; y++) {
                cells[x][y] = new Cell(x, y, this); // Initialize each cell with its coordinates
            }
        }
    }

    /**
     * Retrieves the cell at the specified coordinates.
     * 
     * @param x The x-coordinate of the cell.
     * @param y The y-coordinate of the cell.
     * @return The cell at the given coordinates, or null if the coordinates are out of bounds.
     */
    public Cell getCell(int x, int y) {
        // Check if the provided coordinates are within the grid bounds
        if (x >= 0 && x < GRID_SIZE && y >= 0 && y < GRID_SIZE) {
            return cells[x][y];
        } else {
            // Return null if the coordinates are out of bounds to indicate an invalid cell
            return null;
        }
    }
    
    /**
     * Retrieves the list of adjacent cells to the specified cell.
     * 
     * @param cell The cell to retrieve the adjacent cells for.
     * @return The list of adjacent cells to the specified cell.
     */
    public List<Cell> getAdjacentCells(Cell cell) {
        List<Cell> adjacentCells = new ArrayList<>();
        int x = cell.getX();
        int y = cell.getY();

        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if (i == 0 && j == 0) {
                    // Skip the cell itself
                    continue;
                }

                int adjacentX = x + i;
                int adjacentY = y + j;

                // Check if the adjacent cell is within the grid bounds
                if (adjacentX >= 0 && adjacentX < GRID_SIZE && adjacentY >= 0 && adjacentY < GRID_SIZE) {
                    Cell adjacentCell = cells[adjacentX][adjacentY];
                    adjacentCells.add(adjacentCell);
                }
            }
        }

        return adjacentCells;
    }

}

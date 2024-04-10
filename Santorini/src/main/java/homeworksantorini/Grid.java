package homeworksantorini;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Grid {
    public static final int GRID_SIZE = 5;
    private Cell[][] cells = new Cell[GRID_SIZE][GRID_SIZE];

    public Grid() {
        for (int x = 0; x < GRID_SIZE; x++) {
            for (int y = 0; y < GRID_SIZE; y++) {
                cells[x][y] = new Cell(x, y);
            }
        }
    }

    /**
     * Converts the grid data to a serializable format.
     *
     * @return A list containing the grid data in a serializable format.
     */
    public List<List<Map<String, Object>>> toSerializableFormat() {
        List<List<Map<String, Object>>> gridData = new ArrayList<>();
        for (int i = 0; i < GRID_SIZE; i++) {
            List<Map<String, Object>> rowData = new ArrayList<>();
            for (int j = 0; j < GRID_SIZE; j++) {
                rowData.add(cells[i][j].toSerializableFormat());
            }
            gridData.add(rowData);
        }
        return gridData;
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
     * Checks if the specified cell is a valid move for a worker.
     * 
     * @param fromCell The cell where the worker is moving from.
     * @param toCell   The cell where the worker is moving to.
     * @return true if the move is valid, false otherwise.
     */
    public boolean isValidMove(Cell fromCell, Cell toCell) {
        if (fromCell == null || toCell == null) return false;
        if (toCell.hasWorker() || toCell.getTower().hasDome()) return false;
    
        List<Cell> adjacentCells = getAdjacentCells(fromCell);
        if (!adjacentCells.contains(toCell)) return false;
    
        return Math.abs(toCell.getTowerLevel() - fromCell.getTowerLevel()) <= 1;
    }

    /**
     * Checks if the specified cell is a valid build for a worker.
     * 
     * @param workerCell The cell where the worker is located.
     * @param targetCell The cell where the worker is building.
     * @return true if the build is valid, false otherwise.
     */
    public boolean isValidBuild(Cell workerCell, Cell targetCell) {
        if (workerCell == null || targetCell == null) return false;
        if (targetCell.hasWorker() || targetCell.getTower().hasDome()) return false; // Target cell is occupied or has a dome
    
        List<Cell> adjacentCells = getAdjacentCells(workerCell);
        return adjacentCells.contains(targetCell); // Check if targetCell is in the list of adjacent cells
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

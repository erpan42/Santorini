package homeworksantorini;

public class Grid {
    public static final int GRID_SIZE = 5;
    public static final int MAX_TOWER_LEVEL = 3;
    private Cell[][] cells = new Cell[GRID_SIZE][GRID_SIZE];

    public Grid() {
        for (int x = 0; x < GRID_SIZE; x++) {
            for (int y = 0; y < GRID_SIZE; y++) {
                cells[x][y] = new Cell(x, y); // Initialize each cell with its coordinates
            }
        }
    }

    public Cell getCell(int x, int y) {
        if (x >= 0 && x < GRID_SIZE && y >= 0 && y < GRID_SIZE) {
            return cells[x][y];
        }
        return null; // Return null for invalid coordinates
    }

    public boolean isValidMove(Cell fromCell, Cell toCell) {
        if (fromCell == null || toCell == null) return false;
        if (toCell.hasWorker() || toCell.getTower().hasDome()) return false; // Target cell is occupied or has dome
        if (!isAdjacent(fromCell, toCell)) return false; // Not adjacent
        if (Math.abs(toCell.getTowerLevel() - fromCell.getTowerLevel()) > 1) return false; // Too high to move

        return true;
    }

    public boolean isValidBuild(Cell workerCell, Cell targetCell) {
        if (workerCell == null || targetCell == null) return false;
        if (!isAdjacent(workerCell, targetCell)) return false; // Not adjacent
        if (targetCell.hasWorker() || targetCell.getTower().hasDome()) return false; // Occupied or has dome

        return true;
    }

    public boolean isAdjacent(Cell cell1, Cell cell2) {
        int x1 = cell1.getX(), y1 = cell1.getY();
        int x2 = cell2.getX(), y2 = cell2.getY();

        return Math.abs(x1 - x2) <= 1 && Math.abs(y1 - y2) <= 1 && !(x1 == x2 && y1 == y2);
    }
}

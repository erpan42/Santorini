package homeworksantorini;

public class Grid {
    private Cell[][] cells;
    private static final int GRID_SIZE = 5;

    public Grid() {
        this.cells = new Cell[GRID_SIZE][GRID_SIZE];
        for (int i = 0; i < GRID_SIZE; i++) {
            for (int j = 0; j < GRID_SIZE; j++) {
                this.cells[i][j] = new Cell();
            }
        }
    }

    

    public Cell getCell(int x, int y) {
        if (x >= 0 && x < GRID_SIZE && y >= 0 && y < GRID_SIZE) {
            return this.cells[x][y];
        }
        return null; // Return null if indices are out of bounds
    }
}

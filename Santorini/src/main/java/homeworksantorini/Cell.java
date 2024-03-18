package homeworksantorini;

import java.util.List;

public class Cell {
    private Tower tower;
    private Worker worker;
    private int x, y; // Coordinates of the cell on the grid
    private Grid grid; // Reference to the grid to access adjacent cells
    public static final int MAX_TOWER_LEVEL = 3;

    public Cell(int x, int y, Grid grid) {
        this.x = x;
        this.y = y;
        this.grid = grid;
        this.tower = new Tower();
        this.worker = null;
    }

    /**
     * Checks if the move from the current cell to the target cell is valid.
     *
     * @param fromCell The cell from which the move is being made.
     * @param toCell   The target cell to move to.
     * @return true if the move is valid, false otherwise.
     */
    public boolean isValidMove(Cell fromCell, Cell toCell) {
        if (fromCell == null || toCell == null) return false;
        if (toCell.hasWorker() || toCell.getTower().hasDome()) return false; // Target cell is occupied or has a dome
    
        List<Cell> adjacentCells = grid.getAdjacentCells(fromCell);
        if (!adjacentCells.contains(toCell)) return false; // Check if toCell is in the list of adjacent cells
    
        return Math.abs(toCell.getTowerLevel() - fromCell.getTowerLevel()) <= 1; // Ensure the move is not too high
    }

    /**
     * Checks if the build action from the worker's cell to the target cell is valid.
     *
     * @param workerCell The cell where the worker is currently located.
     * @param targetCell The target cell to build on.
     * @return true if the build action is valid, false otherwise.
     */
    public boolean isValidBuild(Cell workerCell, Cell targetCell) {
        if (workerCell == null || targetCell == null) return false;
        if (targetCell.hasWorker() || targetCell.getTower().hasDome()) return false; // Target cell is occupied or has a dome
    
        List<Cell> adjacentCells = grid.getAdjacentCells(workerCell);
        return adjacentCells.contains(targetCell); // Check if targetCell is in the list of adjacent cells
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public boolean hasWorker() {
        return this.worker != null;
    }

    public boolean hasDome() {
        return this.tower.hasDome();
    }

    public int getTowerLevel() {
        return this.tower.getLevel();
    }

    public void setWorker(Worker worker) {
        this.worker = worker;
    }

    public Worker getWorker() {
        return this.worker;
    }

    public Tower getTower() {
        return this.tower;
    }

}

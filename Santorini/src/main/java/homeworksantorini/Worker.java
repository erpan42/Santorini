package homeworksantorini;

public class Worker {
    private Cell position;
    private Player owner;
    private Grid grid;

    public Worker(Player owner, Grid grid) {
        this.owner = owner;
        this.grid = grid;
        this.position = null; // No initial position
    }

    public void move(Cell toCell) {
        if (grid.isValidMove(this.position, toCell)) {
            this.position.setWorker(null); // Clear current cell
            this.position = toCell; // Update position
            toCell.setWorker(this); // Assign worker to the new cell
        }
    }

    public void build(Cell targetCell) {
        if (grid.isValidBuild(this.position, targetCell)) {
            if (targetCell.getTowerLevel() < Grid.MAX_TOWER_LEVEL) {
                targetCell.getTower().addLevel();
            } else if (!targetCell.getTower().hasDome()) {
                targetCell.getTower().addDome();
            }
        }
    }

    public Cell getPosition() {
        return this.position;
    }

    public void setPosition(Cell position) {
        this.position = position;
        if (position != null) {
            position.setWorker(this); // Assign this worker to the cell
        }
    }

    public Player getOwner() {
        return this.owner;
    }
}

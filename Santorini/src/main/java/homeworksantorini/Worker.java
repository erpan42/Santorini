package homeworksantorini;

public class Worker {
    private Cell position;
    private Player owner;
    private boolean isMoved;

    public Worker(Player owner) {
        this.owner = owner;
        this.position = null; // No initial position
        this.isMoved = false;
    }
   
    public boolean move(Cell toCell) {
        if (toCell.isValidMove(this.position, toCell)) {
            setPosition(toCell);
            this.isMoved = true;
            return true; // Move was successful
        }
        return false; // Move was unsuccessful
    }

    public boolean build(Cell targetCell) {
        if (targetCell.isValidBuild(this.position, targetCell)) {
            if (targetCell.getTowerLevel() < Cell.MAX_TOWER_LEVEL) {
                targetCell.getTower().addLevel();
            } else if (!targetCell.getTower().hasDome()) {
                targetCell.getTower().addDome();
            }
            return true; // Build was successful
        }
        return false; // Build was unsuccessful
    }

    public Cell getPosition() {
        return this.position;
    }

    public void setPosition(Cell newPosition) {
        this.position.setWorker(null); // Clear the current cell
        this.position = newPosition;
        if (newPosition != null) {
            newPosition.setWorker(this); // Assign this worker to the cell
        }
    }

    public Player getOwner() {
        return this.owner;
    }

    public boolean hasMoved() {
        return this.isMoved;
    }

    public void resetMove() {
        this.isMoved = false;
    }

    public void setMoved(boolean isMoved) {
        this.isMoved = isMoved;
    }
}

package homeworksantorini;

public class Worker {
    private Cell position;
    private Player owner;
    private boolean isActive;

    public Worker(Player owner) {
        this.owner = owner;
        this.position = null; // No initial position
        this.isActive = false;
    }
   
    public boolean move(Cell toCell) {
        // Check if toCell is not null before proceeding with the move
        if (toCell == null) {
            System.out.println("Error: The target cell for the move is invalid.");
            return false; // Move was unsuccessful because the target cell is invalid
        }
    
        if (this.position.isValidMove(this.position, toCell)) {
            setPosition(toCell);
            this.isActive = true;
            return true; // Move was successful
        }
        return false; // Move was unsuccessful due to other reasons (e.g., target cell is occupied or not adjacent)
    }
    

    public boolean build(Cell targetCell) {
        // Check if targetCell is not null before attempting to build
        if (targetCell == null) {
            System.out.println("Error: The target cell for building is invalid.");
            return false; // Building was unsuccessful because the target cell is invalid
        }
        
        if (this.position.isValidBuild(this.position, targetCell)) {
            if (targetCell.getTowerLevel() < Cell.MAX_TOWER_LEVEL) {
                targetCell.getTower().addLevel();
            } else if (!targetCell.getTower().hasDome()) {
                targetCell.getTower().addDome();
            }
            this.isActive = false; // Worker action done
            return true; // Build was successful
        }
        return false; // Build was unsuccessful
    }

    public Cell getPosition() {
        return this.position;
    }

    public void setPosition(Cell newPosition) {
        // Only clear the current cell if position is not null
        if (this.position != null) {
            this.position.setWorker(null); // Clear the current cell
        }
        
        this.position = newPosition; // Update the position to the new cell
    
        // Assign this worker to the new position's cell, if the new position is not null
        if (newPosition != null) {
            newPosition.setWorker(this);
        }
    }

    public Player getOwner() {
        return this.owner;
    }

    public boolean hasMoved() {
        return this.isActive;
    }

    public void resetMove() {
        this.isActive = false;
    }

    public void setMoved(boolean isActive) {
        this.isActive = isActive;
    }
}

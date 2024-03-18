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
   
    /**
     * Moves the worker to the specified cell.
     *
     * @param toCell The target cell to move the worker to.
     * @return true if the move is successful, false otherwise.
     */
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
    
    /**
     * Performs a build action on the specified cell.
     *
     * @param onCell The target cell to build on.
     * @return true if the build is successful, false otherwise.
     */
    public boolean build(Cell onCell) {
        // Check if onCell is not null before attempting to build
        if (onCell == null) {
            System.out.println("Error: The target cell for building is invalid.");
            return false; // Building was unsuccessful because the target cell is invalid
        }

        if (this.position.isValidBuild(this.position, onCell)) {
            if (onCell.getTowerLevel() < Cell.MAX_TOWER_LEVEL) {
                onCell.getTower().addLevel();
            } else if (!onCell.getTower().hasDome()) {
                onCell.getTower().addDome();
            }
            this.isActive = false; // Worker action done
            return true; // Build was successful
        }
        return false; // Build was unsuccessful
    }

    public Cell getPosition() {
        return this.position;
    }

    /**
     * Sets the position of the worker to the specified cell.
     *
     * @param newPosition The new position cell for the worker.
     */
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

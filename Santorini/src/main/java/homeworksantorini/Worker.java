package homeworksantorini;

import java.util.HashMap;
import java.util.Map;

public class Worker {
    private Cell position;
    private Player owner;
    private boolean isActive;

    public Worker(Player owner) {
        this.owner = owner;
        this.position = null;
        this.isActive = false;
    }

    /**
     * Converts the worker data to a serializable format.
     *
     * @return A map containing the worker data in a serializable format.
     */
    public Map<String, Object> toSerializableFormat() {
        Map<String, Object> workerData = new HashMap<>();
        workerData.put("x", position != null ? position.getX() : null);
        workerData.put("y", position != null ? position.getY() : null);
        workerData.put("ownerId", owner != null ? owner.getId() : null);
        return workerData;
    }
   
    /**
     * Moves the worker to the specified cell.
     *
     * @param grid   The grid where the worker is moving.
     * @param toCell The cell where the worker is moving to.
     * @return true if the move is successful, false otherwise.
     */
    public boolean move(Grid grid, Cell toCell) {
        if (grid.isValidMove(this.position, toCell)) {
            setPosition(toCell);
            this.isActive = true;
            return true;
        }
        return false;
    }

    /**
     * Builds a tower on the specified cell.
     *
     * @param grid   The grid where the tower is being built.
     * @param onCell The cell where the tower is being built.
     * @return true if the build is successful, false otherwise.
     */
    public boolean build(Grid grid, Cell onCell) {
        if (grid.isValidBuild(this.position, onCell)) {
            if (onCell.getTowerLevel() < Cell.MAX_TOWER_LEVEL) {
                onCell.getTower().addLevel();
            } else if (!onCell.getTower().hasDome()) {
                onCell.getTower().addDome();
            }
            this.isActive = false;
            return true;
        }
        return false;
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

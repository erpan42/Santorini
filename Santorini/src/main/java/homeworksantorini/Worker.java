package homeworksantorini;

public class Worker {
    private Cell position;
    private Player owner;

    private static final int MAX_TOWER_LEVEL = 3;

    public Worker(Player owner) {
        this.owner = owner;
        this.position = null; // No initial position
    }

    public void move(Cell toCell) {
        if (toCell != null && !toCell.hasWorker() && Math.abs(toCell.getTowerLevel() - this.position.getTowerLevel()) <= 1) {
            this.position.setWorker(null); // Remove worker from current cell
            this.position = toCell;
            toCell.setWorker(this);
        }
    }

    public void build(Cell onCell) {
        if (onCell != null && !onCell.hasWorker() && onCell.getTowerLevel() < MAX_TOWER_LEVEL) {
            onCell.getTower().addLevel();
        } else if (onCell != null && onCell.getTowerLevel() == MAX_TOWER_LEVEL && !onCell.hasDome()) {
            onCell.getTower().addDome();
        }
    }

    public Cell getPosition() {
        return this.position;
    }

    public void setPosition(Cell position) {
        this.position = position;
        if (position != null) {
            position.setWorker(this);
        }
    }

    public Player getOwner() {
        return this.owner;
    }
}


package homeworksantorini;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Player {
    private List<Worker> workers;
    private String id;
    private static final int WIN_CONDITION_TOWER_LEVEL = 3;
    private Worker selectedWorker;
    private GodCard godCard;
    private boolean secondBuildAvailable;
    
    public Player(String id) {
        this.id = id;
        this.workers = new ArrayList<>();
        this.workers.add(new Worker(this));
        this.workers.add(new Worker(this));
    }

    public void setGodCard(GodCard godCard) {
        this.godCard = godCard;
    }

    public GodCard getGodCard() {
        return godCard;
    }

    public void setSecondBuildAvailable(boolean available) {
        this.secondBuildAvailable = available;
    }

    public boolean isSecondBuildAvailable() {
        return secondBuildAvailable;
    }

    /**
     * Converts the player data to a serializable format.
     *
     * @return A map containing the player data in a serializable format.
     */
    public Map<String, Object> toSerializableFormat() {
        Map<String, Object> playerData = new HashMap<>();
        playerData.put("id", id); 
        playerData.put("workers", workers.stream()
                .map(worker -> worker.toSerializableFormat())
                .collect(Collectors.toList()));
        playerData.put("selectedWorker", selectedWorker != null ? selectedWorker.toSerializableFormat() : null);
        playerData.put("secondBuildAvailable", secondBuildAvailable);
        
        // Include the selected god card information
        if (godCard != null) {
            playerData.put("godCard", godCard.getClass().getSimpleName());
        } else {
            playerData.put("godCard", null);
        }
        
        return playerData;
    }

    /**
     * Selects a worker based on the provided coordinates.
     *
     * @param x The x-coordinate of the worker's position.
     * @param y The y-coordinate of the worker's position.
     * @return true if a worker is found at the given coordinates, false otherwise.
     */
    public boolean selectWorkerByCoordinates(int x, int y) {
        for (Worker worker : workers) {
            Cell workerCell = worker.getPosition();
            if (workerCell != null && workerCell.getX() == x && workerCell.getY() == y) {
                selectedWorker = worker;
                System.out.println("Worker at (" + x + ", " + y + ") selected.");
                return true;
            }
        }
        System.out.println("No worker found at the given coordinates belongs to you.");
        return false;
    }

    /**
     * Moves the specified worker to the specified cell.
     *
     * @param grid   The grid where the worker is moving.
     * @param worker The worker performing the move.
     * @param toCell The cell where the worker is moving to.
     * @return true if the move is successful, false otherwise.
     */
    public boolean moveWorker(Grid grid, Worker worker, Cell toCell) {
        if (workers.contains(worker) && worker.move(grid, toCell)) {
            System.out.println("Worker moved successfully.");
            return true;
        }
        System.out.println("Move unsuccessful.");
        return false;
    }

    /**
     * Builds with the specified worker on the specified cell.
     *
     * @param grid   The grid where the build is happening.
     * @param worker The worker performing the build.
     * @param onCell The cell where the build is happening.
     * @return true if the build is successful, false otherwise.
     */
    public boolean buildWithWorker(Grid grid, Worker worker, Cell onCell) {
        if (workers.contains(worker) && worker.hasMoved() && worker.build(grid, onCell)) {
            System.out.println("Build successful.");
            return true;
        }
        System.out.println("Build unsuccessful.");
        return false;
    }

    /**
     * Checks if any of the player's workers meet the win condition.
     *
     * @return true if a worker meets the win condition, false otherwise.
     */
    public boolean checkWinCondition() {
        for (Worker worker : this.workers) {
            // Check if the position is not null before accessing its methods
            if (worker.getPosition() != null && worker.getPosition().getTowerLevel() == WIN_CONDITION_TOWER_LEVEL) {
                return true; // Win condition is met
            }
        }
        return false; // No worker meets the win condition
    }

    public List<Worker> getWorkers() {
        return this.workers;
    }

    public String getId() {
        return this.id;
    }

    /**
     * Retrieves the first worker of the player.
     *
     * @return The first worker of the player, or null if no workers are available.
     */
    public Worker getWorker1() {
        return workers.size() > 0 ? workers.get(0) : null;
    }

    /**
     * Retrieves the second worker of the player.
     *
     * @return The second worker of the player, or null if no workers are available.
     */
    public Worker getWorker2() {
        return workers.size() > 1 ? workers.get(1) : null;
    }

    public Worker getSelectedWorker() {
        return this.selectedWorker;
    }

    public void setSelectedWorker(Worker selectedWorker) {
        this.selectedWorker = selectedWorker;
    }

    /**
     * Retrieves a worker that has not been placed on the grid.
     *
     * @return An unplaced worker, or null if all workers have been placed.
     */
    public Worker getUnplacedWorker() {
        for (Worker worker : workers) {
            if (worker.getPosition() == null) { // Assuming Worker class has a getPosition method
                return worker;
            }
        }
        return null; // All workers have been placed
    }
}

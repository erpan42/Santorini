package homeworksantorini;

import java.util.ArrayList;
import java.util.List;

public class Player {
    private List<Worker> workers;
    private String id;
    private static final int WIN_CONDITION_TOWER_LEVEL = 3;
    private Worker selectedWorker;
    
    public Player(String id, Grid grid) {
        this.id = id;
        this.workers = new ArrayList<>();
        this.workers.add(new Worker(this));
        this.workers.add(new Worker(this));
    }

   // Method to select a worker based on coordinates
    public boolean selectWorkerByCoordinates(int x, int y) {
        for (Worker worker : workers) {
            Cell workerCell = worker.getPosition();
            if (workerCell != null && workerCell.getX() == x && workerCell.getY() == y) {
                selectedWorker = worker;
                System.out.println("Worker at (" + x + ", " + y + ") selected.");
                return true;
            }
        }
        System.out.println("No worker found at the given coordinates.");
        return false;
    }

    // Move a worker to a new cell
    public boolean moveWorker(Worker worker, Cell toCell) {
        if (workers.contains(worker) && worker.move(toCell)) {
            System.out.println("Worker moved successfully.");
            return true;
        }
        System.out.println("Move unsuccessful.");
        return false;
    }

    // Build with a worker
    public boolean buildWithWorker(Worker worker, Cell onCell) {
        if (workers.contains(worker) && worker.build(onCell)) {
            System.out.println("Build successful.");
            return true;
        }
        System.out.println("Build unsuccessful.");
        return false;
    }

    // Check if any of this player's workers meet the win condition
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

    // Method to get the first worker
    public Worker getWorker1() {
        return workers.size() > 0 ? workers.get(0) : null;
    }

    // Method to get the second worker
    public Worker getWorker2() {
        return workers.size() > 1 ? workers.get(1) : null;
    }

    public Worker getSelectedWorker() {
        return this.selectedWorker;
    }
}

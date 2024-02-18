package homeworksantorini;

import java.util.ArrayList;
import java.util.List;

public class Player {
    private List<Worker> workers;
    private String id;
    
    public Player(String id, Grid grid) {
        this.id = id;
        this.workers = new ArrayList<>();
        this.workers.add(new Worker(this, grid));
        this.workers.add(new Worker(this, grid));
    }

    public boolean moveWorker(Worker worker, Cell toCell) {
        return worker.move(toCell); // Move the worker
    }

    public boolean buildWithWorker(Worker worker, Cell onCell) {
        return worker.build(onCell); // Build with the worker
    }

    public List<Worker> getWorkers() {
        return this.workers;
    }

    public String getId() {
        return this.id;
    }
}

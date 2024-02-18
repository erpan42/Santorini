package homeworksantorini;

import java.util.ArrayList;
import java.util.List;

public class Player {
    private List<Worker> workers;
    private String id;

    public Player(String id) {
        this.id = id;
        this.workers = new ArrayList<>();
        this.workers.add(new Worker(this, null));
        this.workers.add(new Worker(this, null));
    }

    public void moveWorker(Worker worker, Cell toCell) {
        worker.move(toCell);
    }

    public void buildWithWorker(Worker worker, Cell onCell) {
        worker.build(onCell);
    }

    public List<Worker> getWorkers() {
        return this.workers;
    }

    public String getId() {
        return this.id;
    }
}

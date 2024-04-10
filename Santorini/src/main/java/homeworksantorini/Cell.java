package homeworksantorini;

import java.util.HashMap;
import java.util.Map;

public class Cell {
    private Tower tower;
    private Worker worker;
    private int x, y;
    public static final int MAX_TOWER_LEVEL = 3;

    public Cell(int x, int y) {
        this.x = x;
        this.y = y;
        this.tower = new Tower();
        this.worker = null;
    }

    public Map<String, Object> toSerializableFormat() {
        Map<String, Object> cellData = new HashMap<>();
        cellData.put("towerLevel", getTowerLevel());
        cellData.put("hasWorker", hasWorker());
        cellData.put("hasDome", hasDome());
        if (hasWorker()) {
            cellData.put("worker", worker.toSerializableFormat());
        }
        return cellData;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public boolean hasWorker() {
        return this.worker != null;
    }

    public boolean hasDome() {
        return this.tower.hasDome();
    }

    public int getTowerLevel() {
        return this.tower.getLevel();
    }

    public void setWorker(Worker worker) {
        this.worker = worker;
    }

    public Worker getWorker() {
        return this.worker;
    }

    public Tower getTower() {
        return this.tower;
    }

}

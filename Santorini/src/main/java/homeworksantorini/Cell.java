package homeworksantorini;

public class Cell {
    private Tower tower;
    private Worker worker;

    public Cell() {
        this.tower = new Tower();
        this.worker = null; // No worker initially
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

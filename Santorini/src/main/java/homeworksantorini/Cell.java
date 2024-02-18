package homeworksantorini;

public class Cell {
    private Tower tower;
    private Worker worker;
    private int x, y; // Coordinates of the cell on the grid

    public Cell(int x, int y) {
        this.x = x;
        this.y = y;
        this.tower = new Tower();
        this.worker = null;
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

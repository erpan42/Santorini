package homeworksantorini;

public class Tower {
    private int level;
    private boolean hasDome;

    private static final int MAX_LEVEL = 3;

    public Tower() {
        this.level = 0; // Initial level
        this.hasDome = false; // No dome initially
    }

    public void addLevel() {
        if (level < MAX_LEVEL) {
            this.level++;
        }
    }

    public void addDome() {
        if (level == MAX_LEVEL) {
            this.hasDome = true;
        }
    }

    public int getLevel() {
        return this.level;
    }

    public boolean hasDome() {
        return this.hasDome;
    }
}

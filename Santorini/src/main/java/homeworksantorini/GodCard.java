package homeworksantorini;

public interface GodCard {
    boolean canMove(Grid grid, Worker worker, Cell targetCell);
    boolean canBuild(Grid grid, Worker worker, Cell targetCell);
    boolean isSecondBuildAllowed();
    void afterMove(Grid grid, Worker worker, Cell targetCell);
    void afterBuild(Grid grid, Worker worker, Cell targetCell);
    boolean hasBuiltOnce();
    void resetBuildState();
    boolean checkWinCondition(Worker worker);
    boolean isSpecialMove(Grid grid, Worker worker, Cell targetCell);
    boolean isMoveLocked();
}
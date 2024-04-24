package homeworksantorini;

public class MinotaurGodCard implements GodCard {
    @Override
    public boolean canMove(Grid grid, Worker worker, Cell targetCell) {
        return grid.isValidMove(worker.getPosition(), targetCell, true);
    }

    @Override
    public boolean canBuild(Grid grid, Worker worker, Cell targetCell) {
        return true;
    }

    @Override
    public void afterMove(Grid grid, Worker worker, Cell targetCell) {
        // No additional behavior
    }

    @Override
    public void afterBuild(Grid grid, Worker worker, Cell targetCell) {
        // No additional behavior
    }

    @Override
    public boolean hasBuiltOnce() {
        return false;
    }

    @Override
    public boolean isSecondBuildAllowed() {
        return false;
    }

    @Override
    public void resetBuildState() {
        // No build state to reset
    }

    @Override
    public boolean checkWinCondition(Worker worker) {
        return false;
    }

    @Override
    public boolean isSpecialMove(Grid grid, Worker worker, Cell targetCell) {
        return targetCell.hasWorker();
    }

    @Override
    public boolean isMoveLocked() {
        return false;
    }
}
package homeworksantorini;

public class DemeterGodCard implements GodCard {
    private Cell firstBuildCell;

    public DemeterGodCard() {
        this.firstBuildCell = null;
    }

    @Override
    public boolean canMove(Grid grid, Worker worker, Cell targetCell) {
        return true;
    }

    // Demeter can build twice, but not on the same cell
    @Override
    public boolean canBuild(Grid grid, Worker worker, Cell targetCell) {
        if (firstBuildCell == null) {
            return true;
        } else {
            return targetCell != firstBuildCell;
        }
    }

    @Override
    public void afterMove(Grid grid, Worker worker, Cell targetCell) {
        // No additional behavior
    }

    // Demeter can build twice, but not on the same cell
    @Override
    public void afterBuild(Grid grid, Worker worker, Cell targetCell) {
        if (firstBuildCell == null) {
            firstBuildCell = targetCell;
        } else {
            firstBuildCell = null;
        }
    }

    @Override
    public boolean hasBuiltOnce() {
        return firstBuildCell != null;
    }
    
    @Override
    public boolean isSecondBuildAllowed() {
        return hasBuiltOnce();
    }

    @Override
    public void resetBuildState() {
        firstBuildCell = null;
    }
    
    @Override
    public boolean checkWinCondition(Worker worker) {
        return false;
    }

    @Override
    public boolean isSpecialMove(Grid grid, Worker worker, Cell targetCell) {
        return false;
    }

    @Override
    public boolean isMoveLocked() {
        return true;
    }
}

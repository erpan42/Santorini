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
}

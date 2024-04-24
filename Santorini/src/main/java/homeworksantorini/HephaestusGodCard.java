package homeworksantorini;

public class HephaestusGodCard implements GodCard {
    private boolean hasBuiltOnce;
    private Cell firstBuildCell;

    public HephaestusGodCard() {
        this.hasBuiltOnce = false;
        this.firstBuildCell = null;
    }

    @Override
    public boolean canMove(Grid grid, Worker worker, Cell targetCell) {
        return true;
    }

    @Override
    public boolean canBuild(Grid grid, Worker worker, Cell targetCell) {
        if (hasBuiltOnce) {
            if (firstBuildCell == null) {
                return targetCell.getTowerLevel() <= Cell.MAX_TOWER_LEVEL;
            } else {
                return firstBuildCell.equals(targetCell) && targetCell.getTowerLevel() < Cell.MAX_TOWER_LEVEL;
            }
        }
        return targetCell.getTowerLevel() <= Cell.MAX_TOWER_LEVEL;
    }

    @Override
    public boolean isSecondBuildAllowed() {
        return hasBuiltOnce && firstBuildCell != null && firstBuildCell.getTowerLevel() < Cell.MAX_TOWER_LEVEL;
    }

    @Override
    public void afterMove(Grid grid, Worker worker, Cell targetCell) {
        // No additional behavior
    }

    @Override
    public void afterBuild(Grid grid, Worker worker, Cell targetCell) {
        if (!hasBuiltOnce) {
            hasBuiltOnce = true;
            firstBuildCell = targetCell;
        } else {
            resetBuildState();
        }
    }
    
    @Override
    public boolean hasBuiltOnce() {
        return hasBuiltOnce;
    }

    @Override
    public void resetBuildState() {
        hasBuiltOnce = false;
        firstBuildCell = null;
    }

    public void resetForNewRound() {
        hasBuiltOnce = false;
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
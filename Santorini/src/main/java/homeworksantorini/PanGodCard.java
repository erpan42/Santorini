package homeworksantorini;

import java.util.HashMap;
import java.util.Map;

public class PanGodCard implements GodCard {
    private Map<Worker, Integer> initialWorkerLevels;

    public PanGodCard() {
        initialWorkerLevels = new HashMap<>();
    }

    @Override
    public boolean canMove(Grid grid, Worker worker, Cell targetCell) {
        if (!initialWorkerLevels.containsKey(worker)) {
            initialWorkerLevels.put(worker, worker.getPosition().getTowerLevel());
        }
        return true;
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

    // Pan wins if he moves down two or more levels
    @Override
    public boolean checkWinCondition(Worker worker) {
        int currentTowerLevel = worker.getPosition().getTowerLevel();
        int initialTowerLevel = worker.getInitialTowerLevel();
        System.out.println("Pan checkWinCondition - Current Tower Level: " + currentTowerLevel);
        System.out.println("Pan checkWinCondition - Initial Tower Level: " + initialTowerLevel);
        return initialTowerLevel - currentTowerLevel >= 2;
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
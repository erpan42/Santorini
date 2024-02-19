package homeworksantorini;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;;

class CellTest {
    private Grid grid;
    private Player player;
    private Cell initialCell, targetCell, occupiedCell;
    private Worker worker, anotherWorker;

    @BeforeEach
    void setUp() {
        grid = new Grid();
        player = new Player("Player1", grid);
        initialCell = grid.getCell(0, 0);
        targetCell = grid.getCell(1, 1);
        occupiedCell = grid.getCell(0, 1); // Cell next to the initial cell

        worker = player.getWorkers().get(0);
        anotherWorker = new Worker(player); // Another worker for testing occupied cells

        worker.setPosition(initialCell);
        initialCell.setWorker(worker);

        anotherWorker.setPosition(occupiedCell);
        occupiedCell.setWorker(anotherWorker); // Set another worker to simulate an occupied cell
    }

    @Test
    void testInitialCellState() {
        Cell newCell = grid.getCell(2, 2); // Assume this cell is unoccupied and untouched
        assertNull(newCell.getWorker());
        assertFalse(newCell.hasDome());
        assertEquals(0, newCell.getTowerLevel());
    }

    @Test
    void testWorkerPlacement() {
        assertEquals(worker, initialCell.getWorker());
    }

    @Test
    void testMoveWorkerToNewCell() {
        assertTrue(worker.move(targetCell));
        assertNull(initialCell.getWorker());
        assertEquals(worker, targetCell.getWorker());
    }

    @Test
    void testInvalidMoveToOccupiedCell() {
        assertFalse(worker.move(occupiedCell)); // Attempt to move to an occupied cell
        assertEquals(worker, initialCell.getWorker()); // Worker should remain in the initial cell
        assertEquals(anotherWorker, occupiedCell.getWorker()); // Occupied cell should still contain the other worker
    }

    @Test
    void testTowerBuilding() {
        assertTrue(worker.build(targetCell)); // Worker builds on the target cell
        assertEquals(1, targetCell.getTowerLevel()); // Target cell tower level should increase
    }

    @Test
    void testDomePlacement() {
        for (int i = 0; i < Cell.MAX_TOWER_LEVEL; i++) {
            worker.build(targetCell); // Build up to the max level
        }
        assertEquals(Cell.MAX_TOWER_LEVEL, targetCell.getTowerLevel()); // Verify max level reached

        assertTrue(worker.build(targetCell)); // Add dome
        assertTrue(targetCell.hasDome()); // Verify dome is added
    }

    @Test
    void testBuildAfterDome() {
        for (int i = 0; i <= Cell.MAX_TOWER_LEVEL; i++) {
            worker.build(targetCell); // Build up to and including dome placement
        }
        assertFalse(worker.build(targetCell)); // Attempt to build after dome should fail
    }

    @Test
    void testWorkerRemoval() {
        worker.move(targetCell); // Move worker to target cell
        assertNull(initialCell.getWorker()); // Initial cell should no longer have the worker
    }
}

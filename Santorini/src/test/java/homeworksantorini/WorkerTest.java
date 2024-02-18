package homeworksantorini;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;;

class WorkerTest {
    private Worker worker;
    private Grid grid;
    private Player player;

    @BeforeEach
    void setUp() {
        grid = new Grid();
        player = new Player("Player1", grid);
        worker = new Worker(player, grid);
    }

    @Test
    void testMoveToValidCell() {
        Cell initialCell = grid.getCell(1, 1);
        Cell targetCell = grid.getCell(1, 2); // Assuming a valid move

        worker.setPosition(initialCell);
        assertTrue(worker.move(targetCell), "Worker should be able to move to an adjacent, unoccupied cell.");
        assertEquals(targetCell, worker.getPosition(), "Worker's position should be updated to the target cell.");
        assertNull(initialCell.getWorker(), "Initial cell should no longer have the worker.");
        assertEquals(worker, targetCell.getWorker(), "Target cell should now contain the worker.");
    }

    @Test
    void testMoveToInvalidCell() {
        Cell initialCell = grid.getCell(1, 1);
        Cell occupiedCell = grid.getCell(1, 2);
        Worker anotherWorker = new Worker(player, grid); // Another worker to occupy the target cell
        anotherWorker.setPosition(occupiedCell);

        worker.setPosition(initialCell);
        assertFalse(worker.move(occupiedCell), "Worker should not be able to move to an occupied cell.");
        assertEquals(initialCell, worker.getPosition(), "Worker's position should remain unchanged.");
        assertEquals(anotherWorker, occupiedCell.getWorker(), "Occupied cell should still contain the other worker.");
    }

    @Test
    void testBuildOnValidCell() {
        Cell workerCell = grid.getCell(1, 1);
        Cell targetBuildCell = grid.getCell(1, 2); // Assuming a valid build

        worker.setPosition(workerCell);
        assertTrue(worker.build(targetBuildCell), "Worker should be able to build on an adjacent, unoccupied cell.");
        assertEquals(1, targetBuildCell.getTowerLevel(), "Target build cell's tower level should increase.");
    }

    @Test
    void testBuildOnInvalidCell() {
        Cell workerCell = grid.getCell(1, 1);
        Cell occupiedCell = grid.getCell(1, 2);
        Worker anotherWorker = new Worker(player, grid); // Another worker to occupy the target build cell
        anotherWorker.setPosition(occupiedCell);

        worker.setPosition(workerCell);
        assertFalse(worker.build(occupiedCell), "Worker should not be able to build on an occupied cell.");
    }

    @Test
    void testGetOwner() {
        assertEquals(player, worker.getOwner(), "Worker's owner should be the player who created it.");
    }
}

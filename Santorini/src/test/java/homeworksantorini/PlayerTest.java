package homeworksantorini;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {
    private Player player;
    private Grid grid;
    private Cell initialCell;
    private Cell targetMoveCell;
    private Cell targetBuildCell;
    private Cell farCell;

    private static final int TARGET_MOVE_CELL_ROW = 0;
    private static final int TARGET_MOVE_CELL_COLUMN = 1;
    private static final int TARGET_BUILD_CELL_ROW = 1;
    private static final int TARGET_BUILD_CELL_COLUMN = 1;
    private static final int FAR_BUILD_CELL_ROW = 3;
    private static final int FAR_BUILD_CELL_COLUMN = 3;

    @BeforeEach
    void setUp() {
        grid = new Grid();
        player = new Player("Player1", grid);
        initialCell = grid.getCell(0, 0); // Assuming a grid with at least 2x2 cells
        targetMoveCell = grid.getCell(TARGET_MOVE_CELL_ROW, TARGET_MOVE_CELL_COLUMN);
        targetBuildCell = grid.getCell(TARGET_BUILD_CELL_ROW, TARGET_BUILD_CELL_COLUMN);
        farCell = grid.getCell(FAR_BUILD_CELL_ROW, FAR_BUILD_CELL_COLUMN);
        

        // Place one of the player's workers on the initial cell for testing
        Worker worker = player.getWorkers().get(0);
        worker.setPosition(initialCell);
        initialCell.setWorker(worker);
    }

    @Test
    void testPlayerId() {
        assertEquals("Player1", player.getId(), "Player ID should match the one provided at construction.");
    }

    @Test
    void testGetWorkers() {
        assertEquals(2, player.getWorkers().size(), "Player should have exactly 2 workers.");
        assertNotNull(player.getWorkers().get(0), "First worker should not be null.");
        assertNotNull(player.getWorkers().get(1), "Second worker should not be null.");
    }

    @Test
    void testMoveWorker() {
        Worker worker = player.getWorkers().get(0);
        assertTrue(player.moveWorker(worker, targetMoveCell), "Worker should be able to move to an unoccupied cell.");
        assertEquals(targetMoveCell, worker.getPosition(), "Worker's position should update to the target move cell.");
        assertNull(initialCell.getWorker(), "Initial cell should no longer have a worker after the move.");
    }

    @Test
    void testBuildWithWorker() {
        Worker worker = player.getWorkers().get(0);
        // Ensure the worker is adjacent to the cell it will build on
        worker.move(targetMoveCell);
        assertTrue(player.buildWithWorker(worker, targetBuildCell), "Worker should be able to build on an adjacent cell.");
        assertEquals(1, targetBuildCell.getTowerLevel(), "Target build cell's tower level should increase after building.");
    }

    @Test
    void testMoveWorkerInvalidCell() {
        Worker worker = player.getWorkers().get(0);
        Cell invalidCell = null; // Simulating an invalid cell (e.g., out of bounds)
        assertFalse(player.moveWorker(worker, invalidCell), "Worker should not be able to move to an invalid cell.");
        assertFalse(player.moveWorker(worker, farCell), "Worker should not be able to move to a non-adjacent cell.");
    }

    @Test
    void testBuildWorkerInvalidCell() {
        Worker worker = player.getWorkers().get(0);
        Cell invalidCell = null; // Simulating an invalid cell (e.g., out of bounds)
        assertFalse(player.buildWithWorker(worker, invalidCell), "Worker should not be able to build on an invalid cell.");
        assertFalse(player.buildWithWorker(worker, farCell), "Worker should not be able to build on a non-adjacent cell.");
    }
}

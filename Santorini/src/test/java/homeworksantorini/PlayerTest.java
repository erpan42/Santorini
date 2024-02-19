package homeworksantorini;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PlayerTest {
    private Player player;
    private Grid grid;
    private Cell initialCell, targetMoveCell, targetBuildCell, farCell;

    @BeforeEach
    void setUp() {
        grid = new Grid();
        player = new Player("Player1", grid);
        initialCell = grid.getCell(0, 0);
        targetMoveCell = grid.getCell(1, 0); // Adjacent cell for valid move
        targetBuildCell = grid.getCell(1, 1); // Adjacent cell for valid build
        farCell = grid.getCell(2, 2); // Non-adjacent cell for invalid move/build

        Worker worker = player.getWorkers().get(0);
        worker.setPosition(initialCell);
        initialCell.setWorker(worker);
    }

    @Test
    void testPlayerId() {
        assertEquals("Player1", player.getId());
    }

    @Test
    void testGetWorkers() {
        assertEquals(2, player.getWorkers().size());
        assertNotNull(player.getWorkers().get(0));
        assertNotNull(player.getWorkers().get(1));
    }

    @Test
    void testMoveWorker() {
        Worker worker = player.getWorkers().get(0);
        assertTrue(player.moveWorker(worker, targetMoveCell));
        assertEquals(targetMoveCell, worker.getPosition());
        assertNull(initialCell.getWorker());
    }

    @Test
    void testBuildWithWorker() {
        Worker worker = player.getWorkers().get(0);
        worker.move(targetMoveCell); // Move worker to be adjacent to build cell
        assertTrue(player.buildWithWorker(worker, targetBuildCell));
        assertEquals(1, targetBuildCell.getTowerLevel());
    }

    @Test
    void testMoveWorkerInvalidCell() {
        Worker worker = player.getWorkers().get(0);
        assertFalse(player.moveWorker(worker, null)); // Invalid cell
        assertFalse(player.moveWorker(worker, farCell)); // Non-adjacent cell
    }

    @Test
    void testBuildWorkerInvalidCell() {
        Worker worker = player.getWorkers().get(0);
        assertFalse(player.buildWithWorker(worker, null)); // Invalid cell
        assertFalse(player.buildWithWorker(worker, farCell)); // Non-adjacent cell
    }
}
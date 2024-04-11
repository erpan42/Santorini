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
        grid = new Grid(); // Assumes a default grid is correctly initialized
        player = new Player("Player1");
        initialCell = grid.getCell(0, 0); // Assuming grid.getCell(x, y) returns a cell
        targetMoveCell = grid.getCell(1, 1);
        targetBuildCell = grid.getCell(1, 2); // Adjacent to targetMoveCell for valid build
        farCell = grid.getCell(2, 2); // Example of a far cell for invalid move/build
    
        Worker worker = player.getWorkers().get(0);
        worker.setPosition(initialCell); // Set worker's initial position
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
        assertTrue(player.moveWorker(grid, worker, targetMoveCell));
        assertEquals(targetMoveCell, worker.getPosition());
        assertNull(initialCell.getWorker());
    }
    
    @Test
    void testBuildWithWorker() {
        Worker worker = player.getWorkers().get(0);
        assertTrue(player.moveWorker(grid, worker, targetMoveCell)); // Move worker to be adjacent to build cell
        assertTrue(player.buildWithWorker(grid, worker, targetBuildCell));
        assertEquals(1, targetBuildCell.getTowerLevel());
    }
    
    @Test
    void testMoveWorkerInvalidCell() {
        Worker worker = player.getWorkers().get(0);
        assertFalse(player.moveWorker(grid, worker, null)); // Invalid cell
        // Assuming grid or player class has a method to check for adjacency or valid move
        assertFalse(player.moveWorker(grid, worker, farCell)); // Non-adjacent cell assuming the grid has such a check
    }
    
    @Test
    void testBuildWorkerInvalidCell() {
        Worker worker = player.getWorkers().get(0);
        assertFalse(player.buildWithWorker(grid, worker, null)); // Invalid cell
        assertFalse(player.buildWithWorker(grid, worker, farCell)); // Non-adjacent cell assuming the grid checks for valid build locations
    }
}
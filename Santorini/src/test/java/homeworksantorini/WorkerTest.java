package homeworksantorini;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;;

class WorkerTest {
    private Grid grid;
    private Player player;
    private Worker worker;
    private Cell initialCell, adjacentCell, nonAdjacentCell, buildCell;
    private final int magic3 = 3;

    @BeforeEach
    void setUp() {
        grid = new Grid(); // Make sure this grid is properly initialized with cells
        player = new Player("Player1");
        worker = new Worker(player);
        initialCell = grid.getCell(1, 1);
        adjacentCell = grid.getCell(1, 2); // Adjacent to initialCell
        nonAdjacentCell = grid.getCell(magic3, magic3); // Not adjacent to initialCell, assuming 'magic3' refers to a distant cell
        buildCell = grid.getCell(2, 1); // Adjacent to initialCell for building
    
        // Set worker's initial position
        worker.setPosition(initialCell);
        initialCell.setWorker(worker); // Assume Cell has a setWorker method to track the worker in the cell
    }

    @Test
    void testMoveToAdjacentCell() {
        assertTrue(worker.move(grid, adjacentCell), "Worker should be able to move to an adjacent, unoccupied cell.");
        assertEquals(adjacentCell, worker.getPosition(), "Worker's new position should be the adjacent cell.");
        assertNull(initialCell.getWorker(), "Initial cell should no longer have the worker.");
        assertEquals(worker, adjacentCell.getWorker(), "Adjacent cell should now have the worker.");
    }
    
    @Test
    void testMoveToNonAdjacentCell() {
        assertFalse(worker.move(grid, nonAdjacentCell), "Worker should not be able to move to a non-adjacent cell.");
        assertEquals(initialCell, worker.getPosition(), "Worker's position should remain unchanged after an invalid move.");
    }
    
    @Test
    void testBuildOnAdjacentCell() {
        worker.move(grid, adjacentCell); // Ensure worker has moved before building
        assertTrue(worker.build(grid, buildCell), "Worker should be able to build on an adjacent, unoccupied cell.");
        assertEquals(1, buildCell.getTowerLevel(), "Build cell's tower level should be increased after building.");
    }
    
    @Test
    void testBuildOnNonAdjacentCell() {
        worker.move(grid, adjacentCell); // Ensure worker has moved before attempting to build on a non-adjacent cell
        assertFalse(worker.build(grid, nonAdjacentCell), "Worker should not be able to build on a non-adjacent cell.");
        assertEquals(0, nonAdjacentCell.getTowerLevel(), "Non-adjacent cell's tower level should remain unchanged after a failed build attempt.");
    }
    
    @Test
    void testResetMove() {
        worker.move(grid, adjacentCell); // Move the worker to trigger the moved flag
        worker.resetMove(); // Reset the move flag
        assertFalse(worker.hasMoved(), "Worker's moved flag should be reset to false.");
    }

    @Test
    void testSetMoved() {
        worker.setMoved(true); // Manually set the moved flag to true
        assertTrue(worker.hasMoved(), "Worker's moved flag should reflect the manually set value.");
        worker.setMoved(false); // Manually set the moved flag to false
        assertFalse(worker.hasMoved(), "Worker's moved flag should reflect the manually set value.");
    }
}
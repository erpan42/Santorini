package homeworksantorini;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;;

public class WorkerTest {
    private Worker worker;
    private Player player;
    private Grid grid;

    @BeforeEach
    public void setUp() {
        player = new Player("player1");
        worker = new Worker(player);
        grid = new Grid();
    }

    @Test
    public void testMove() {
        worker.setPosition(grid.getCell(0, 0));
        assertTrue(worker.move(grid, grid.getCell(0, 1), new NoGodCard()));
    }

    @Test
    public void testBuild() {
        worker.setPosition(grid.getCell(0, 0));
        assertTrue(worker.build(grid, grid.getCell(0, 1), new NoGodCard()));
        assertEquals(1, grid.getCell(0, 1).getTowerLevel());
    }

    @Test
    public void testSetPosition() {
        worker.setPosition(grid.getCell(0, 0));
        assertEquals(grid.getCell(0, 0), worker.getPosition());
    }
}
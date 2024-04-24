package homeworksantorini;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PlayerTest {
    private Player player;
    private final int magic3 = 3;

    @BeforeEach
    public void setUp() {
        player = new Player("player1");
        
        // Set a default GodCard for the player
        player.setGodCard(new NoGodCard());
    }

    @Test
    public void testSelectWorkerByCoordinates() {
        player.getWorker1().setPosition(new Cell(0, 0));
        assertTrue(player.selectWorkerByCoordinates(0, 0));
    }

    @Test
    public void testMoveWorker() {
        Grid grid = new Grid();
        player.getWorker1().setPosition(grid.getCell(0, 0));
        assertTrue(player.moveWorker(grid, player.getWorker1(), grid.getCell(0, 1)));
    }

    @Test
    public void testBuildWithWorker() {
        Grid grid = new Grid();
        player.getWorker1().setPosition(grid.getCell(0, 0));
        player.moveWorker(grid, player.getWorker1(), grid.getCell(0, 1));
        assertTrue(player.buildWithWorker(grid, player.getWorker1(), grid.getCell(1, 1)));
    }

    @Test
    public void testCheckWinCondition() {
        Grid grid = new Grid();
        player.getWorker1().setPosition(grid.getCell(0, 0));
        for (int i = 0; i < magic3; i++) {
            grid.getCell(0, 0).getTower().addLevel();
        }
        player.moveWorker(grid, player.getWorker1(), grid.getCell(0, 1));
        assertFalse(player.checkWinCondition());
    }
}
package homeworksantorini;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class GodIntegrationTest {
    private Grid grid;
    private Player player1;
    private Player player2;

    @BeforeEach
    public void setUp() {
        grid = new Grid();
        player1 = new Player("player1");
        player2 = new Player("player2");
    }

    @Test
    public void testPanInteraction() {
        player1.setGodCard(new PanGodCard());

        // Set up the game state
        grid.getCell(2, 2).getTower().addLevel();
        grid.getCell(2, 2).getTower().addLevel();
        player1.getWorker1().setPosition(grid.getCell(2, 2));

        // Player 1 moves down
        player1.moveWorker(grid, player1.getWorker1(), grid.getCell(1, 1));

        // Check the win condition for player 1
        assertTrue(player1.checkWinCondition());
    }

    @Test
    public void testDemeterInteraction() {
        player1.setGodCard(new DemeterGodCard());

        // Set up the game state
        player1.getWorker1().setPosition(grid.getCell(1, 0));
        player1.moveWorker(grid, player1.getWorker1(), grid.getCell(0, 0));

        // Player 1 builds twice
        player1.buildWithWorker(grid, player1.getWorker1(), grid.getCell(0, 1));
        player1.buildWithWorker(grid, player1.getWorker1(), grid.getCell(1, 0));

        // Check that the second build was successful
        assertEquals(1, grid.getCell(0, 1).getTowerLevel());
        assertEquals(1, grid.getCell(1, 0).getTowerLevel());
    }

    @Test
    public void testHephaestusInteraction() {
        player1.setGodCard(new HephaestusGodCard());

        // Set up the game state
        player1.getWorker1().setPosition(grid.getCell(1, 0));
        player1.moveWorker(grid, player1.getWorker1(), grid.getCell(0, 0));

        // Player 1 builds twice on the same space
        player1.buildWithWorker(grid, player1.getWorker1(), grid.getCell(0, 1));
        player1.buildWithWorker(grid, player1.getWorker1(), grid.getCell(0, 1));

        // Check that the second build increased the tower level
        assertEquals(2, grid.getCell(0, 1).getTowerLevel());
    }

    @Test
    public void testMinotaurInteraction() {
        player1.setGodCard(new MinotaurGodCard());
        player2.setGodCard(new NoGodCard());

        // Set up the game state
        player1.getWorker1().setPosition(grid.getCell(0, 0));
        player2.getWorker1().setPosition(grid.getCell(0, 1));

        // Player 1 moves and pushes player 2's worker
        player1.moveWorker(grid, player1.getWorker1(), grid.getCell(0, 1));

        // Check the new positions
        assertEquals(grid.getCell(0, 1), player1.getWorker1().getPosition());
        assertEquals(grid.getCell(0, 2), player2.getWorker1().getPosition());
    }
}
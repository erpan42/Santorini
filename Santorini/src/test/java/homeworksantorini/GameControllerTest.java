package homeworksantorini;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class GameControllerTest {
    private GameController gameController;
    private Grid grid;
    private Player player1;
    private Player player2;

    @BeforeEach
    void setUp() {
        grid = new Grid();
        player1 = new Player("Player1", grid);
        player2 = new Player("Player2", grid);
        gameController = new GameController("Player1", "Player2", grid);
        gameController.startGame(); // Initialize the game and place workers
    }
    @Test
    void testGetCurrentPlayerAfterTurn() {
        // Access Player2 instance from GameController to ensure reference match
        Player expectedPlayer2 = gameController.getPlayers()[1]; // Add a getPlayers() method in GameController if not present

        // Simulate a complete turn for Player1, ensuring changeTurn() is invoked
        gameController.changeTurn();

        // Now, the current player should be Player 2
        assertEquals(expectedPlayer2, gameController.getCurrentPlayer(), "After a turn, the current player should be Player 2.");
    }

    @Test
    void testCheckWinCondition() {
        Worker worker = player1.getWorkers().get(0);
        // Simulate moving a worker to a winning position
        Cell winningCell = grid.getCell(0, 0); // Assume this cell is at the winning height
        winningCell.getTower().addLevel();
        winningCell.getTower().addLevel();
        winningCell.getTower().addLevel(); // Tower is now at the winning height
        worker.setPosition(winningCell);
        assertTrue(gameController.checkWinCondition(worker)); // Assert that the win condition is met
    }

    @Test
    void testEndGame() {
        gameController.endGame(player1); // End the game declaring Player 1 as the winner
        // Assert game cleanup, for example, grid reset or workers' positions are null
        assertNull(player1.getWorkers().get(0).getPosition());
        assertNull(player1.getWorkers().get(1).getPosition());
        assertNull(player2.getWorkers().get(0).getPosition());
        assertNull(player2.getWorkers().get(1).getPosition());
        // Note: Actual assertions will depend on how endGame is implemented, e.g., if it resets the grid or clears worker positions
    }
}

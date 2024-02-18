package homeworksantorini;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
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
        
        // Define named constants for the worker positions
        final int worker1X = 0;
        final int worker1Y = 0;
        final int worker2X = 0;
        final int worker2Y = 1;
        final int worker3X = 4;
        final int worker3Y = 4;
        final int worker4X = 4;
        final int worker4Y = 3;
        
        gameController.startGameWithPositions(worker1X, worker1Y, worker2X, worker2Y,
                              worker3X, worker3Y, worker4X, worker4Y); // Initialize the game and place workers
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
    void testPlaceWorkerInitial() {

        // Verify that each worker for Player 1 is placed correctly
        for (int i = 0; i < 2; i++) {
            Worker worker = gameController.getPlayers()[0].getWorkers().get(i);
            assertNotNull(worker.getPosition(), "Player 1's worker " + (i + 1) + " should be placed on the grid.");
        }

        // Verify that each worker for Player 2 is placed correctly
        for (int i = 0; i < 2; i++) {
            Worker worker = gameController.getPlayers()[1].getWorkers().get(i);
            assertNotNull(worker.getPosition(), "Player 2's worker " + (i + 1) + " should be placed on the grid.");
        }

        // Additional assertions can be added to check the specific positions if needed
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

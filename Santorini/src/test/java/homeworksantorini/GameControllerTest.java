package homeworksantorini;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.io.IOException;

class GameControllerTest {
    @Mock
    private Grid grid;

    @Mock
    private Player player1;

    @Mock
    private Player player2;

    private GameController gameController;

    @BeforeEach
    void setUp() throws IOException {
        // Initialize mocks created with the @Mock annotation
        MockitoAnnotations.openMocks(this);

        // Setup mock behavior
        // Assuming GameController constructor takes two player names and a grid
        when(player1.getId()).thenReturn("Player1");
        when(player2.getId()).thenReturn("Player2");
        
        // Setup the gameController with mocked dependencies
        gameController = new GameController(player1.getId(), player2.getId(), grid);
    }

    // @Test
    // void testInitialWorkerPlacement() {
    //     // Define positions for workers of Player 1 and Player 2
    //     final int initialPositionXp2 = 3;
    //     final int initialPositionYp2 = 4;

    //     int[] workerPositionsP1 = {0, 0, 1, 1};
    //     int[] workerPositionsP2 = {initialPositionXp2, initialPositionXp2, initialPositionYp2, initialPositionYp2};

    //     assertTrue(gameController.setInitialWorkerPosition(gameController.getPlayers()[0], new int[]{workerPositionsP1[0], workerPositionsP1[1]}, 
    //         new int[]{workerPositionsP1[2], workerPositionsP1[initialPositionXp2]}));
    //     assertTrue(gameController.setInitialWorkerPosition(gameController.getPlayers()[1], new int[]{workerPositionsP2[0], workerPositionsP2[1]}, 
    //         new int[]{workerPositionsP2[2], workerPositionsP2[initialPositionXp2]}));

    //     // Verify workers are correctly placed on the grid
    //     for (Player player : gameController.getPlayers()) {
    //         for (Worker worker : player.getWorkers()) {
    //             assertNotNull(worker.getPosition(), "Worker should have a position on the grid");
    //         }
    //     }
    // }

    // @Test
    // void testChangeTurn() {
    //     gameController.changeTurn();
    //     assertEquals("Player2", gameController.getCurrentPlayer().getId(), "After one turn change, it should be Player 2's turn.");
    // }

    // @Test
    // void testCheckGameStatusNoWin() {
    //     gameController.checkGameStatus();
    //     assertNotNull(gameController.getCurrentPlayer(), "The game should continue if no player has won.");
    // }

    // @Test
    // void testCheckGameStatusWithWin() {
    //     // Manually set a worker to the winning position
    //     Worker winningWorker = gameController.getPlayers()[0].getWorkers().get(0);
    //     Cell winningCell = grid.getCell(0, 0);
    //     for (int i = 0; i < Cell.MAX_TOWER_LEVEL; i++) {
    //         winningCell.getTower().addLevel();
    //     }
    //     winningWorker.setPosition(winningCell);

    //     gameController.checkGameStatus();
    //     assertNull(gameController.getCurrentPlayer(), "Game should end when a player wins.");
    // }

    // @Test
    // void testEndGame() {
    //     gameController.endGame(gameController.getPlayers()[0]);
    //     assertNull(gameController.getCurrentPlayer(), "After the game ends, there should be no current player.");
    // }
}

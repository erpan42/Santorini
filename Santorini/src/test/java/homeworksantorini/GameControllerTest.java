package homeworksantorini;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class GameControllerTest {
    private GameController gameController;
    private final int magic3 = 3;

    @BeforeEach
    public void setUp() throws Exception {
        gameController = new GameController("player1", "player2", new Grid());
        
        // Set a default GodCard for each player
        for (Player player : gameController.getPlayers()) {
            player.setGodCard(new NoGodCard());
        }
    }

    @Test
    public void testResetGame() {
        gameController.resetGame();
        assertEquals(0, gameController.getPlacedWorkers());
        assertNull(gameController.getWinner());
    }

    @Test
    public void testSetInitialWorkerPosition() {
        gameController.setInitialWorkerPosition(gameController.getPlayers()[0], new int[]{0, 0});
        assertEquals(1, gameController.getPlacedWorkers());
    }

    @Test
    public void testSelectWorkerForCurrentPlayer() {
        gameController.setInitialWorkerPosition(gameController.getPlayers()[0], new int[]{0, 0});
        assertTrue(gameController.selectWorkerForCurrentPlayer(0, 0));
    }

    @Test
    public void testPlayerMove() {
        gameController.setInitialWorkerPosition(gameController.getPlayers()[0], new int[]{0, 0});
        gameController.selectWorkerForCurrentPlayer(0, 0);
        
        // Set a default GodCard for the player
        Player currentPlayer = gameController.getCurrentPlayer();
        currentPlayer.setGodCard(new NoGodCard());
        
        assertTrue(gameController.playerMove(0, 1));
    }

    @Test
    public void testPlayerBuild() {
        gameController.setInitialWorkerPosition(gameController.getPlayers()[0], new int[]{0, 0});
        gameController.selectWorkerForCurrentPlayer(0, 0);
        gameController.playerMove(0, 1);
        assertTrue(gameController.playerBuild(1, 1));
    }

    @Test
    public void testCheckGameStatus() {
        gameController.setInitialWorkerPosition(gameController.getPlayers()[0], new int[]{0, 0});
        gameController.selectWorkerForCurrentPlayer(0, 0);
        for (int i = 0; i < magic3; i++) {
            gameController.playerMove(0, 1);
            gameController.playerBuild(0, 0);
        }
        gameController.checkGameStatus();
        assertNull(gameController.getWinner());
    }
}
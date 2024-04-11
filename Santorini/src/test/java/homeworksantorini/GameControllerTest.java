package homeworksantorini;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class GameControllerTest {
    private GameController gameController;
    private Player player1;
    private Player player2;

    @BeforeEach
    public void setUp() throws Exception {
        gameController = new GameController("player1", "player2", new Grid());
        player1 = gameController.getPlayers()[0];
        player2 = gameController.getPlayers()[1];
    }

    @Test
    public void testInitialState() {
        assertEquals(0, gameController.getPlacedWorkers());
        assertNull(gameController.getWinner());
        assertEquals(player1, gameController.getCurrentPlayer());
    }

    @Test
    public void testSetInitialWorkerPosition() {
        gameController.setInitialWorkerPosition(player1, new int[]{0, 0});
        assertEquals(1, gameController.getPlacedWorkers());
        assertNotNull(player1.getWorkers().get(0).getPosition());
        assertEquals(player1, gameController.getCurrentPlayer());

        gameController.setInitialWorkerPosition(player2, new int[]{1, 1});
        assertEquals(1, gameController.getPlacedWorkers());
        assertNotNull(player1.getWorkers().get(0).getPosition());
        assertEquals(player1, gameController.getCurrentPlayer());
    }

    @Test
    public void testInvalidInitialWorkerPosition() {
        gameController.setInitialWorkerPosition(player1, new int[]{0, 0});
        gameController.setInitialWorkerPosition(player2, new int[]{0, 0});
        assertEquals(1, gameController.getPlacedWorkers());
        assertNull(player2.getWorkers().get(0).getPosition());
    }

    @Test
    public void testResetGame() {
        gameController.setInitialWorkerPosition(player1, new int[]{0, 0});
        gameController.setInitialWorkerPosition(player2, new int[]{1, 1});
        gameController.resetGame();

        assertEquals(0, gameController.getPlacedWorkers());
        assertNull(player1.getWorkers().get(0).getPosition());
        assertNull(player2.getWorkers().get(0).getPosition());
    }

    @Test
    public void testSelectWorkerForCurrentPlayer() {
        gameController.setInitialWorkerPosition(player1, new int[]{0, 0});
        gameController.setInitialWorkerPosition(player2, new int[]{1, 1});

        assertTrue(gameController.selectWorkerForCurrentPlayer(0, 0));
        assertNotNull(player1.getSelectedWorker());
    }

    @Test
    public void testPlayerMove() {
        gameController.setInitialWorkerPosition(player1, new int[]{0, 0});
        gameController.setInitialWorkerPosition(player2, new int[]{1, 1});
        gameController.selectWorkerForCurrentPlayer(0, 0);

        assertTrue(gameController.playerMove(0, 1));    }

    @Test
    public void testPlayerBuild() {
        gameController.setInitialWorkerPosition(player1, new int[]{0, 0});
        gameController.setInitialWorkerPosition(player2, new int[]{1, 1});
        gameController.selectWorkerForCurrentPlayer(0, 0);
        gameController.playerMove(0, 1);

        assertTrue(gameController.playerBuild(0, 0));
        assertEquals(1, gameController.getGrid().getCell(0, 0).getTowerLevel());
        assertEquals(player2, gameController.getCurrentPlayer());
    }
}
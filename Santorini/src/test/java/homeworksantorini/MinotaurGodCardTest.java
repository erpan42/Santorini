package homeworksantorini;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class MinotaurGodCardTest {
    private MinotaurGodCard minotaurGodCard;
    private Grid grid;
    private Worker worker;
    private Worker opponentWorker;

    @BeforeEach
    public void setUp() {
        minotaurGodCard = new MinotaurGodCard();
        grid = new Grid();
        worker = new Worker(new Player("player1"));
        opponentWorker = new Worker(new Player("player2"));
    }

    @Test
    public void testCanMove() {
        worker.setPosition(grid.getCell(0, 0));
        opponentWorker.setPosition(grid.getCell(0, 1));

        assertTrue(minotaurGodCard.canMove(grid, worker, grid.getCell(0, 1)));
        assertTrue(minotaurGodCard.canMove(grid, worker, grid.getCell(1, 0)));
    }

    @Test
    public void testIsSpecialMove() {
        worker.setPosition(grid.getCell(0, 0));
        opponentWorker.setPosition(grid.getCell(0, 1));

        assertTrue(minotaurGodCard.isSpecialMove(grid, worker, grid.getCell(0, 1)));
        assertFalse(minotaurGodCard.isSpecialMove(grid, worker, grid.getCell(1, 0)));
    }
}

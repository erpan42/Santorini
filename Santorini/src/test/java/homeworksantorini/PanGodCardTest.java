package homeworksantorini;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PanGodCardTest {
    private PanGodCard panGodCard;
    private Grid grid;
    private Worker worker;

    @BeforeEach
    public void setUp() {
        panGodCard = new PanGodCard();
        grid = new Grid();
        worker = new Worker(new Player("player1"));
    }

    @Test
    public void testCanMove() {
        worker.setPosition(grid.getCell(2, 2));
        assertTrue(panGodCard.canMove(grid, worker, grid.getCell(1, 1)));
    }

    @Test
    public void testCheckWinCondition() {
        grid.getCell(2, 2).getTower().addLevel();
        grid.getCell(2, 2).getTower().addLevel();
        worker.setPosition(grid.getCell(2, 2));
        worker.move(grid, grid.getCell(2, 1), panGodCard);

        assertTrue(panGodCard.checkWinCondition(worker));
    }
}

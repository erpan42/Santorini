package homeworksantorini;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class DemeterGodCardTest {
    private DemeterGodCard demeterGodCard;
    private Grid grid;
    private Worker worker;

    @BeforeEach
    public void setUp() {
        demeterGodCard = new DemeterGodCard();
        grid = new Grid();
        worker = new Worker(new Player("player1"));
    }

    @Test
    public void testCanBuild() {
        assertTrue(demeterGodCard.canBuild(grid, worker, grid.getCell(0, 1)));
        demeterGodCard.afterBuild(grid, worker, grid.getCell(0, 1));
        assertFalse(demeterGodCard.canBuild(grid, worker, grid.getCell(0, 1)));
    }

    @Test
    public void testIsSecondBuildAllowed() {
        assertFalse(demeterGodCard.isSecondBuildAllowed());
        demeterGodCard.afterBuild(grid, worker, grid.getCell(0, 1));
        assertTrue(demeterGodCard.isSecondBuildAllowed());
    }
}
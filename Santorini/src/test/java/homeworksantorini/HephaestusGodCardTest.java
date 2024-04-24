package homeworksantorini;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class HephaestusGodCardTest {
    private HephaestusGodCard hephaestusGodCard;
    private Grid grid;
    private Worker worker;

    @BeforeEach
    public void setUp() {
        hephaestusGodCard = new HephaestusGodCard();
        grid = new Grid();
        worker = new Worker(new Player("player1"));
    }

    @Test
    public void testCanBuild() {
        assertTrue(hephaestusGodCard.canBuild(grid, worker, grid.getCell(0, 1)));
        hephaestusGodCard.afterBuild(grid, worker, grid.getCell(0, 1));
        assertTrue(hephaestusGodCard.canBuild(grid, worker, grid.getCell(0, 1)));
        grid.getCell(0, 1).getTower().addLevel();
        grid.getCell(0, 1).getTower().addLevel();
        assertTrue(hephaestusGodCard.canBuild(grid, worker, grid.getCell(0, 1)));
    }

    @Test
    public void testIsSecondBuildAllowed() {
        assertFalse(hephaestusGodCard.isSecondBuildAllowed());
        hephaestusGodCard.afterBuild(grid, worker, grid.getCell(0, 1));
        assertTrue(hephaestusGodCard.isSecondBuildAllowed());
    }

    @Test
    public void testResetBuildState() {
        hephaestusGodCard.afterBuild(grid, worker, grid.getCell(0, 1));
        assertTrue(hephaestusGodCard.isSecondBuildAllowed());
        hephaestusGodCard.resetBuildState();
        assertFalse(hephaestusGodCard.isSecondBuildAllowed());
    }
}
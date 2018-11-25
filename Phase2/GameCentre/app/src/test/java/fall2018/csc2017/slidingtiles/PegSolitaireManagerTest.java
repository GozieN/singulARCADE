package fall2018.csc2017.slidingtiles;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class PegSolitaireManagerTest {

    /** The board manager for testing. */
    PegSolitaireManager boardManager;

    /**
     * Make a set of tiles.
     * @return a set of tiles
     */
    private List<PegSolitaireTile> makeTiles() {
        List<PegSolitaireTile> tiles = new ArrayList<>();
        final int numTiles = PegSolitaireBoard.NUM_ROWS * PegSolitaireBoard.NUM_COLS;
        for (int tileNum = 0; tileNum != numTiles; tileNum++) {
            tiles.add(new PegSolitaireTile(2));
        }
        return tiles;
    }

    /**
     * Make a starting diamond peg solitaire board.
     */
    private void setUpDiamondStart() {
        PegSolitaireBoard.setDimensions(9);
        PegSolitaireBoard board = new PegSolitaireBoard(makeTiles());
        boardManager = new PegSolitaireManager(board);
    }

    /**
     * Make a solved diamond peg solitaire board.
     */
    private void setUpDiamondEnd() {
        PegSolitaireBoard.setDimensions(9);
        PegSolitaireBoard board = new PegSolitaireBoard(makeTiles());
        boardManager = new PegSolitaireManager(board);
    }

    /**
     * Test isOver
     */
    @Test
    public void testIsOver() {
        setUpDiamondStart();
        assertEquals(false, boardManager.isOver());
    }
}

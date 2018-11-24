package fall2018.csc2017.slidingtiles;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class SlidingTilesBoardAndTileTest {

    /** The board manager for testing. */
    SlidingTilesManager slidingTilesManager;

    /**
     * Make a set of tiles that are in order.
     * @return a set of tiles that are in order
     */
    private List<Tile> makeTiles() {
        List<Tile> tiles = new ArrayList<>();
        final int numTiles = SlidingTilesBoard.NUM_ROWS * SlidingTilesBoard.NUM_COLS;
        for (int tileNum = 0; tileNum != numTiles; tileNum++) {
            if (tileNum == numTiles - 1) {
                tiles.add(new Tile(tileNum, tileNum));
            } else {
                tiles.add(new Tile(tileNum));
            }
        }

        return tiles;
    }

    /**
     * Make a solved SlidingTilesBoard.
     */
    private void setUpCorrect() {
        SlidingTilesBoard.setDimensions(4);
        List<Tile> tiles = makeTiles();
        SlidingTilesBoard slidingTilesBoard = new SlidingTilesBoard(tiles);
        SlidingTilesBoard.setDimensions(4);
        slidingTilesManager = new SlidingTilesManager(slidingTilesBoard);
    }

    /**
     * Shuffle a few tiles.
     */
    private void swapFirstTwoTiles() {
        slidingTilesManager.getBoard().swapTiles(0, 0, 0, 1);
    }

    /**
     * Test whether swapping two tiles makes a solved board unsolved.
     */
    @Test
    public void testIsSolved() {
        setUpCorrect();
        assertEquals(true, slidingTilesManager.isOver());
        swapFirstTwoTiles();
        assertEquals(false, slidingTilesManager.isOver());
    }

    /**
     * Test whether swapping the first two tiles works.
     */
    @Test
    public void testSwapFirstTwo() {
        setUpCorrect();
        assertEquals(1, slidingTilesManager.getBoard().getTile(0, 0).getId());
        assertEquals(2, slidingTilesManager.getBoard().getTile(0, 1).getId());
        slidingTilesManager.getBoard().swapTiles(0, 0, 0, 1);
        assertEquals(2, slidingTilesManager.getBoard().getTile(0, 0).getId());
        assertEquals(1, slidingTilesManager.getBoard().getTile(0, 1).getId());
    }

    /**
     * Test whether swapping the last two tiles works.
     */
    @Test
    public void testSwapLastTwo() {
        setUpCorrect();
        assertEquals(15, slidingTilesManager.getBoard().getTile(3, 2).getId());
        assertEquals(16, slidingTilesManager.getBoard().getTile(3, 3).getId());
        slidingTilesManager.getBoard().swapTiles(3, 3, 3, 2);
        assertEquals(16, slidingTilesManager.getBoard().getTile(3, 2).getId());
        assertEquals(15, slidingTilesManager.getBoard().getTile(3, 3).getId());
    }

    /**
     * Test whether isValidHelp works.
     */
    @Test
    public void testIsValidTap() {
        setUpCorrect();
        assertEquals(true, slidingTilesManager.isValidTap(11));
        assertEquals(true, slidingTilesManager.isValidTap(14));
        assertEquals(false, slidingTilesManager.isValidTap(10));
    }
}


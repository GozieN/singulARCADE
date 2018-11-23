package fall2018.csc2017.slidingtiles;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 * Unit tests for the BoardManager class.
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */

public class BoardTest {

    /** The board manager for testing. */
    Board board;

    /**
     * Make a set of tiles that are in order.
     * @return a set of tiles that are in order
     */
    private List<Tile> makeTiles() {
        List<Tile> tiles = new ArrayList<>();
        final int numTiles = Board.NUM_ROWS * Board.NUM_COLS;
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
     * Make a solved Board.
     */
    private void setUpCorrect() {
        Board.setDimensions(4);
        List<Tile> tiles = makeTiles();
        board = new Board(tiles);
    }

    /**
     * Test numTiles works
     */
    @Test
    public void testNumTiles() {
        setUpCorrect();
        assertEquals(16, board.numTiles());
    }

    /**
     * Test getTile works
     */
    @Test
    public void testGetTile() {
        setUpCorrect();
        assertEquals(16, board.getTile(3, 3).getId());
    }

    /**
     * Test getDimensions works
     */
    @Test
    public void testGetDimensions() {
        setUpCorrect();
        assertEquals(4, board.getDimensions());
        board.setDimensions(3);
        assertEquals(3, board.getDimensions());
    }

    /**
     * Test swapTiles works
     */
    @Test
    public void testSwapTiles() {
        setUpCorrect();
        board.swapTiles(3,3,3,2);
        assertEquals(16, board.getTile(3,2).getId());
        assertEquals(15, board.getTile(3,3).getId());
    }

    /**
     * Test toString works
     */
    @Test
    public void testToString() {
        setUpCorrect();
        String string = "Board{" + "tiles=" + Arrays.toString(board.getTiles()) + "}";
        assertEquals(string, board.toString());

    }

    /**
     * Test hasNext of iterator works
     */
    @Test
    public void testHasNext() {
        setUpCorrect();
        Iterator<Tile> boardIterator = board.iterator();

        //after never looping - should have 15 "hasNext()'s"
        assertEquals(true, boardIterator.hasNext());

        //after fully looping, so no next tiles
        while (boardIterator.hasNext()) {
            boardIterator.next();
        }
        assertEquals(false, boardIterator.hasNext());

    }

    /**
     * Test next of iterator works
     */
    @Test
    public void testNext() {
        setUpCorrect();
        Iterator<Tile> boardIterator = board.iterator();
        //get the first tile
        assertEquals(1, boardIterator.next().getId());

        //get the last tile
        int i = 0;
        while (i < 14) {
            boardIterator.next();
            i++;
        }
        assertEquals(16, boardIterator.next().getId());
    }
}

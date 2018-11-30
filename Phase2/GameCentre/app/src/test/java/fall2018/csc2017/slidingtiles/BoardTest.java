package fall2018.csc2017.slidingtiles;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class BoardTest {

    /**
     * The board used for testing.
     */
    Board board;

    private void setUpCorrect() {
        board = new Board();
    }

    /**
     * Test numTiles works
     */
    @Test
    public void testNumTiles() {
        setUpCorrect();
        assertEquals(0, board.numTiles());
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
     * Test getDimensions works
     */
    @Test
    public void testGetDimensions() {
        setUpCorrect();
        assertEquals(0, board.getDimensions());
    }

    /**
     * Test getTile works
     */
    @Test
    public void testGetTile() {
        setUpCorrect();
    }
}

package fall2018.csc2017.slidingtiles;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class BoardTest {

    Board board;

    /**
     * Make a set of tiles that are in order.
     * @return a set of tiles that are in order
     */
    private List<SlidingTile> makeTiles() {
        List<SlidingTile> tiles = new ArrayList<>();
        final int numTiles = SlidingTilesBoard.NUM_ROWS * SlidingTilesBoard.NUM_COLS;
        for (int tileNum = 0; tileNum != numTiles; tileNum++) {
            if (tileNum == numTiles - 1) {
                tiles.add(new SlidingTile(tileNum, tileNum));
            } else {
                tiles.add(new SlidingTile(tileNum));
            }
        }

        return tiles;
    }

    private void setUpCorrect() {
        board = new Board();
    }

    @Test
    public void testNumTiles() {
        setUpCorrect();
        assertEquals(0, board.numTiles());
    }

    @Test
    public void testToString() {
        setUpCorrect();
        String string = "Board{" + "tiles=" + Arrays.toString(board.getTiles()) + "}";
        assertEquals(string, board.toString());
    }

    @Test
    public void testGetDimensions() {
        setUpCorrect();
        assertEquals(0, board.getDimensions());
    }

    @Test
    public void testGetTile() {
        setUpCorrect();
    }
}

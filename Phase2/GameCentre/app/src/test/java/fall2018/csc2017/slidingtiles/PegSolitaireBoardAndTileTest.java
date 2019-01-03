package fall2018.csc2017.slidingtiles;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import fall2018.csc2017.slidingtiles.Games.PegSolitaire.PegSolitaireBoard;
import fall2018.csc2017.slidingtiles.Games.PegSolitaire.PegSolitaireTile;

import static org.junit.Assert.*;

public class PegSolitaireBoardAndTileTest {

    /**
     * The peg solitaire board for testing.
     */
    private PegSolitaireBoard pegSolitaireBoard;

    /**
     * Make a set of tiles.
     *
     * @return a set of tiles
     */
    private List<PegSolitaireTile> makeTiles() {
        List<PegSolitaireTile> tiles = new ArrayList<>();
        final int numTiles = PegSolitaireBoard.getNumRows() * PegSolitaireBoard.getNumCols();
        for (int tileNum = 0; tileNum != numTiles; tileNum++) {
            tiles.add(new PegSolitaireTile(2));
        }
        return tiles;
    }

    /**
     * Make a square peg solitaire board.
     */
    private void setUpSquare() {
        PegSolitaireBoard.setDimensions(6);
        pegSolitaireBoard = new PegSolitaireBoard(makeTiles());
    }

    /**
     * Make a cross peg solitaire board.
     */
    private void setUpCross() {
        PegSolitaireBoard.setDimensions(7);
        pegSolitaireBoard = new PegSolitaireBoard(makeTiles());
    }

    /**
     * Make a diamond peg solitaire board.
     */
    private void setUpDiamond() {
        PegSolitaireBoard.setDimensions(9);
        pegSolitaireBoard = new PegSolitaireBoard(makeTiles());
    }

    /**
     * Test numPieces works
     */
    @Test
    public void testNumPieces() {
        setUpCross();
        assertEquals(49, pegSolitaireBoard.numPieces());
    }

    /**
     * Test getTile works.
     */
    @Test
    public void testGetTile() {
        setUpDiamond();
        assertEquals(0, pegSolitaireBoard.getPegTile(1, 0).getId());
    }

    /**
     * Test getDimensions works.
     */
    @Test
    public void testGetDimensions() {
        setUpSquare();
        assertEquals(36, pegSolitaireBoard.getDimensions());
        PegSolitaireBoard.setDimensions(9);
        assertEquals(81, pegSolitaireBoard.getDimensions());
    }

    /**
     * Test moveGamepiece works
     */
    @Test
    public void testMoveGamepiece() {
        setUpCross();
        pegSolitaireBoard.moveGamepiece(1, 3, 3, 3);
        assertEquals(2, pegSolitaireBoard.getPegTile(3, 3).getId());
        assertEquals(1, pegSolitaireBoard.getPegTile(1, 3).getId());
        assertEquals(1, pegSolitaireBoard.getPegTile(2, 3).getId());
    }

    /**
     * Test addOrRemoveHighlight works
     */
    @Test
    public void testAddOrRemoveHighlight() {
        setUpSquare();
        pegSolitaireBoard.addOrRemoveHighlight(2, 1);
        assertEquals(R.drawable.tile_highlight, pegSolitaireBoard.getPegTile(2, 1).getBackground());
        pegSolitaireBoard.addOrRemoveHighlight(2, 3);
        assertEquals(R.drawable.tile_emptyhighlight, pegSolitaireBoard.getPegTile(2, 3).getBackground());
        pegSolitaireBoard.addOrRemoveHighlight(2, 3);
        assertEquals(R.drawable.tile_empty, pegSolitaireBoard.getPegTile(2, 3).getBackground());
        pegSolitaireBoard.addOrRemoveHighlight(2, 1);
        assertEquals(R.drawable.tile_full, pegSolitaireBoard.getPegTile(2, 1).getBackground());
    }

    /**
     * Test numRemainingPegs works
     */
    @Test
    public void testNumRemainingPegs() {
        setUpDiamond();
        pegSolitaireBoard.moveGamepiece(2, 4, 4, 4);
        pegSolitaireBoard.moveGamepiece(3, 6, 4, 3);
        //pegSolitaireBoard.moveGamepiece(3, 3, 5, 3);
        assertEquals(38, pegSolitaireBoard.numRemainingPegs());

    }

    /**
     * Test undoMove works
     */
    @Test
    public void testUndoMove() {
        setUpDiamond();
        pegSolitaireBoard.moveGamepiece(2, 4, 4, 4);
        pegSolitaireBoard.moveGamepiece(3, 6, 3, 4);
        pegSolitaireBoard.moveGamepiece(2, 2, 2, 4);
        pegSolitaireBoard.moveGamepiece(5, 5, 3, 5);
        pegSolitaireBoard.undoMove(3, 4, 3, 6);
        assertEquals(true, pegSolitaireBoard.getPegTile(3, 6).getId() == 2);
        assertEquals(true, pegSolitaireBoard.getPegTile(3, 5).getId() == 2);
        assertEquals(true, pegSolitaireBoard.getPegTile(3, 4).getId() == 1);
        pegSolitaireBoard.undoMove(4, 4, 2, 4);
        assertEquals(true, pegSolitaireBoard.getPegTile(4, 4).getId() == 1);
        assertEquals(true, pegSolitaireBoard.getPegTile(3, 4).getId() == 2);
        assertEquals(true, pegSolitaireBoard.getPegTile(2, 4).getId() == 2);
        pegSolitaireBoard.undoMove(2, 4, 2, 2);
        assertEquals(true, pegSolitaireBoard.getPegTile(2, 4).getId() == 1);
        assertEquals(true, pegSolitaireBoard.getPegTile(2, 3).getId() == 2);
        assertEquals(true, pegSolitaireBoard.getPegTile(2, 2).getId() == 2);
        pegSolitaireBoard.undoMove(3, 5, 5, 5);
        assertEquals(true, pegSolitaireBoard.getPegTile(3, 5).getId() == 1);
        assertEquals(true, pegSolitaireBoard.getPegTile(4, 5).getId() == 2);
        assertEquals(true, pegSolitaireBoard.getPegTile(5, 5).getId() == 2);
    }

    /**
     * Test whether compareTo works
     */
    @Test
    public void testCompareTo() {
        setUpDiamond();
        PegSolitaireTile comparingTile = pegSolitaireBoard.getOneTile(2,5);
        assertEquals(2, pegSolitaireBoard.getOneTile(0,0).compareTo(comparingTile));
    }

    /**
     * Test toString works
     */
    @Test
    public void testToString() {
        setUpDiamond();
        String string = "PegSolitaireBoard{" + "tiles=" + Arrays.toString(pegSolitaireBoard.getPegTiles()) + "}";
        assertEquals(string, pegSolitaireBoard.toString());
    }

}



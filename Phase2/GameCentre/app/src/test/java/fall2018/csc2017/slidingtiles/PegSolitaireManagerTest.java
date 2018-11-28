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
    private void setUpDiamondWin() {
        PegSolitaireBoard.setDimensions(9);
        PegSolitaireBoard board = new PegSolitaireBoard(makeTiles());
        for (PegSolitaireTile tileRow[]: board.getPegTiles()) {
            for (PegSolitaireTile tile:tileRow) {
                if (tile.getId() != 0 && tile.getId() != 1) {
                    tile.setId(1, false);
                }
            }
        }
        board.getPegTile(4, 2).setId(2, false);
        boardManager = new PegSolitaireManager(board);
    }

    /**
     * Make a solved diamond peg solitaire board.
     */
    private void setUpDiamondLoss() {
        PegSolitaireBoard.setDimensions(9);
        PegSolitaireBoard board = new PegSolitaireBoard(makeTiles());
        for (PegSolitaireTile tileRow[]: board.getPegTiles()) {
            for (PegSolitaireTile tile:tileRow) {
                if (tile.getId() != 0 && tile.getId() != 1) {
                    tile.setId(1, false);
                }
            }
        }
        board.getPegTile(4, 2).setId(2, false);
        board.getPegTile(2, 2).setId(2, false);
        boardManager = new PegSolitaireManager(board);
    }

    /**
     * Test whether there are no more possible moves.
     */
    @Test
    public void testIsOver() {
        setUpDiamondStart();
        assertEquals(false, boardManager.isOver());
        setUpDiamondLoss();
        assertEquals(true, boardManager.isOver());
        setUpDiamondWin();
        assertEquals(true, boardManager.isOver());
    }

    /**
     * Test whether a board is being set and returned properly.
     */
    @Test
    public void testGetAndSetBoard() {
        setUpDiamondStart();
        PegSolitaireBoard.setDimensions(9);
        List<PegSolitaireTile> tiles = makeTiles();
        PegSolitaireBoard board = new PegSolitaireBoard(tiles);
        boardManager.setBoard(board);
        assertEquals(board, boardManager.getBoard());
        PegSolitaireBoard.setDimensions(6);
        boardManager.setBoard(board);
        assertEquals(board, boardManager.getBoard());
    }

    /**
     * Test whether a certain move is possible to complete or not.
     */
    @Test
    public void testIsValidTap() {
        setUpDiamondStart();
        // position of dead tile
        assertEquals(false, boardManager.isValidTap(1));
        // position not adjacent to empty tile
        assertEquals(false, boardManager.isValidTap(6));
        // position in the same column, 2 rows away
        assertEquals(true, boardManager.isValidTap(38));
        // position in the same row, 2 columns away
        assertEquals(true, boardManager.isValidTap(22));
        setUpDiamondLoss();
        assertEquals(false, boardManager.isValidTap(38));
    }

    /**
     * Test whether the first move in order to complete the position works.
     */
    @Test
    public void testFirstMove() {
        setUpDiamondStart();
        boardManager.firstMove(38);
        assertEquals(true, boardManager.getBoard().getPegTile(4, 2).isHighlight());
        assertEquals(true, boardManager.getBoard().getPegTile(4, 4).isHighlight());

    }

    /**
     * Test whether the second tap is a tap that allows the move to be made.
     */
    @Test
    public void testIsValidSecondTap() {
        setUpDiamondStart();
        int firstMovePosition = 38;
        boardManager.firstMove(firstMovePosition);
        // position of invalid move
        assertEquals(false, boardManager.isValidSecondTap(firstMovePosition, 22));
        // position of invalid move
        assertEquals(false, boardManager.isValidSecondTap(firstMovePosition, 3));
        // position of valid move
        assertEquals(true, boardManager.isValidSecondTap(firstMovePosition, 40));


    }

    /**
     * Test whether touchMove works
     */
    @Test
    public void testTouchMove() {
        setUpDiamondStart();
        int firstMovePosition = 42;
        boardManager.firstMove(firstMovePosition);
        boardManager.touchMove(firstMovePosition, 40);
        assertEquals(true, boardManager.getBoard().getPegTile(4, 6).getId() == 1);
        assertEquals(true, boardManager.getBoard().getPegTile(4, 4).getId() == 2);
        assertEquals(false, boardManager.getBoard().getPegTile(4, 6).isHighlight());
        assertEquals(false, boardManager.getBoard().getPegTile(4, 4).isHighlight());
    }

    /**
     * Test whether the player has won or not.
     */
    @Test
    public void testHasWon() {
        setUpDiamondLoss();
        assertEquals(false, boardManager.hasWon());
        setUpDiamondWin();
        assertEquals(true, boardManager.hasWon());

    }
}

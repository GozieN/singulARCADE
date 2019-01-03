package fall2018.csc2017.slidingtiles;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import fall2018.csc2017.slidingtiles.Games.PegSolitaire.PegSolitaireBoard;
import fall2018.csc2017.slidingtiles.Games.PegSolitaire.PegSolitaireManager;
import fall2018.csc2017.slidingtiles.Games.PegSolitaire.PegSolitaireTile;

import static org.junit.Assert.assertEquals;

public class PegSolitaireManagerTest {

    /** The board manager for testing. */
    PegSolitaireManager boardManager;

    /**
     * Make a set of tiles.
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
     * Test whether all possible moves that can be made are highlighted.
     */
    @Test
    public void testSeePossibleMoves() {
        setUpDiamondStart();
        boardManager.seePossibleMoves(38);
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
        boardManager.seePossibleMoves(firstMovePosition);
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
        boardManager.touchMove(42, 40);
        boardManager.touchMove(59, 41);
        boardManager.touchMove(57, 59);
        int firstMovePosition = 40;
        boardManager.seePossibleMoves(firstMovePosition);
        boardManager.touchMove(firstMovePosition, 42);
        assertEquals(true, boardManager.getBoard().getPegTile(4, 6).getId() == 2);
        assertEquals(true, boardManager.getBoard().getPegTile(4, 4).getId() == 1);
        assertEquals(true, boardManager.getBoard().getPegTile(4, 5).getId() == 1);
        assertEquals(false, boardManager.getBoard().getPegTile(4, 6).isHighlight());
        assertEquals(false, boardManager.getBoard().getPegTile(4, 4).isHighlight());
        assertEquals(false, boardManager.getBoard().getPegTile(6, 4).isHighlight());
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

        //the game is not over
        setUpDiamondStart();
        assertEquals(false, boardManager.hasWon());

    }

    /**
     * Test whether getScore works
     */
    @Test
    public void testGetScore() {
        setUpDiamondWin();
        User user = new User("Dianna", "Dianna");
        GameLauncher.setCurrentUser(user);
        PegSolitaireManager pegSolitaireManager = (PegSolitaireManager) GameLauncher.getCurrentUser().getRecentManagerOfBoard(PegSolitaireManager.getGameName());

        //When no moves have been completed
        assertEquals(-1, pegSolitaireManager.getScore());

        //When you have completed some moves on a 4x4 board
        ArrayList arrayList = new ArrayList();
        arrayList.add(1);
        arrayList.add(1);
        arrayList.add(2);
        arrayList.add(3);
        user.pushGameStates(PegSolitaireManager.getGameName(), arrayList);
        user.pushGameStates(PegSolitaireManager.getGameName(), arrayList);
        user.pushGameStates(PegSolitaireManager.getGameName(), arrayList);
        assertEquals(10000, pegSolitaireManager.getScore());

        //When you have completed some moves on a 5x5 board
        PegSolitaireBoard.setDimensions(7);
        assertEquals(6667, pegSolitaireManager.getScore());

        //When you have completed some moves on a 3x3 board
        PegSolitaireBoard.setDimensions(6);
        assertEquals(3333, pegSolitaireManager.getScore());

    }

    /**
     * Test undo move works
     */
    @Test
    public void testUndoMove() {
        setUpDiamondStart();
        int firstMovePosition = 42;
        boardManager.seePossibleMoves(firstMovePosition);
        boardManager.touchMove(firstMovePosition, 40);

    }
}

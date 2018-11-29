package fall2018.csc2017.slidingtiles;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 * Unit tests for the BoardManager class.
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class SlidingTileManagerAndTileTest {

    /** The board manager for testing. */
    SlidingTilesManager boardManager;

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

    /**
     * Make a solved Board.
     */
    private void setUpCorrect() {
        SlidingTilesBoard.setDimensions(4);
        List<SlidingTile> tiles = makeTiles();
        SlidingTilesBoard board = new SlidingTilesBoard(tiles);
        boardManager = new SlidingTilesManager(board);
    }

    //These tests are for BoardManager class
    /**
     * Shuffle a few tiles.
     */
    private void swapFirstTwoTiles() {
        boardManager.getBoard().swapTiles(0, 0, 0, 1);
    }

    /**
     * Test whether swapping two tiles makes a solved board unsolved.
     */
    @Test
    public void testIsOver() {
        setUpCorrect();
        assertEquals(true, boardManager.isOver());
        swapFirstTwoTiles();
        assertEquals(false, boardManager.isOver());
    }

    /**
     * Test whether swapping the first two tiles works.
     */
    @Test
    public void testSwapFirstTwo() {
        setUpCorrect();
        assertEquals(1, boardManager.getBoard().getTile(0, 0).getId());
        assertEquals(2, boardManager.getBoard().getTile(0, 1).getId());
        boardManager.getBoard().swapTiles(0, 0, 0, 1);
        assertEquals(2, boardManager.getBoard().getTile(0, 0).getId());
        assertEquals(1, boardManager.getBoard().getTile(0, 1).getId());
    }

    /**
     * Test whether swapping the last two tiles works.
     */
    @Test
    public void testSwapLastTwo() {
        setUpCorrect();
        assertEquals(15, boardManager.getBoard().getTile(3, 2).getId());
        assertEquals(16, boardManager.getBoard().getTile(3, 3).getId());
        boardManager.getBoard().swapTiles(3, 3, 3, 2);
        assertEquals(16, boardManager.getBoard().getTile(3, 2).getId());
        assertEquals(15, boardManager.getBoard().getTile(3, 3).getId());
    }

    /**
     * Test whether isValidHelp works.
     */
    @Test
    public void testIsValidTap() {
        setUpCorrect();
        assertEquals(true, boardManager.isValidTap(11));
        assertEquals(true, boardManager.isValidTap(14));
        assertEquals(false, boardManager.isValidTap(10));
    }

    /**
     * Test whether a board is being set and returned properly (getter and setter of board works).
     */
    @Test
    public void testGetAndSetBoard() {
        setUpCorrect();
        SlidingTilesBoard.setDimensions(3);
        List<SlidingTile> tiles = makeTiles();
        SlidingTilesBoard board = new SlidingTilesBoard(tiles);
        SlidingTilesBoard.setDimensions(3);
        boardManager.setBoard(board);
        assertEquals(board, boardManager.getBoard());
        SlidingTilesBoard.setDimensions(5);
        boardManager.setBoard(board);
        assertEquals(board, boardManager.getBoard());
    }

    /**
     * Test whether getTilesInArrayList works.
     */
    @Test
    public void testGetTilesInArrayList() {
        setUpCorrect();
        ArrayList<Object> arrayList = new ArrayList();
        for (int i = 1; i < 16; i++) { arrayList.add(i);}
        ArrayList<Object> comparableArrayList = boardManager.getTilesInArrayList();
        assertEquals(arrayList.size(), comparableArrayList.size());
        for (int i = 0; i < arrayList.size(); i++) {
            assertEquals(arrayList.get(i), comparableArrayList.get(i));
        }
    }

    /**
     * Test whether positionOfBlankTile works
     */
    @Test
    public void testPositionOfBlankTile() {
        setUpCorrect();
        //if the blank tile is the very last tile
        assertEquals(4, boardManager.positionBlankTile());
        //if the blank tile is anywhere else but the last tile- this case, is the row above
        boardManager.getBoard().swapTiles(3,3, 2,3);
        assertEquals(3, boardManager.positionBlankTile());


    }

    /**
     * Test whether touchMove works
     */
    @Test
    public void testTouchMove() {
        setUpCorrect();
        User user = new User("Dianna", "Dianna");
        GameLauncher.setCurrentUser(user);
        GameLauncher.getCurrentUser().setRecentManagerOfBoard(SlidingTilesManager.GAME_NAME, boardManager);


        //nothing should be switched at this point bc the position is too far away
        ((SlidingTilesManager) GameLauncher.getCurrentUser().getRecentManagerOfBoard(SlidingTilesManager.GAME_NAME)).touchMove(1);
        assertEquals(0, GameLauncher.getCurrentUser().getStackOfGameStates(SlidingTilesManager.GAME_NAME).size());

        //switch with the tile above
        ((SlidingTilesManager) GameLauncher.getCurrentUser().getRecentManagerOfBoard(SlidingTilesManager.GAME_NAME)).touchMove(11);
        assertEquals(1, GameLauncher.getCurrentUser().getStackOfGameStates(SlidingTilesManager.GAME_NAME).size());

        //switch with the tile below
        ((SlidingTilesManager) GameLauncher.getCurrentUser().getRecentManagerOfBoard(SlidingTilesManager.GAME_NAME)).touchMove(15);
        assertEquals(2, GameLauncher.getCurrentUser().getStackOfGameStates(SlidingTilesManager.GAME_NAME).size());

        //switch with the tile to the left
        ((SlidingTilesManager) GameLauncher.getCurrentUser().getRecentManagerOfBoard(SlidingTilesManager.GAME_NAME)).touchMove(14);
        assertEquals(3, GameLauncher.getCurrentUser().getStackOfGameStates(SlidingTilesManager.GAME_NAME).size());

        //switch with the tile to the right
        ((SlidingTilesManager) GameLauncher.getCurrentUser().getRecentManagerOfBoard(SlidingTilesManager.GAME_NAME)).touchMove(15);
        assertEquals(4, GameLauncher.getCurrentUser().getStackOfGameStates(SlidingTilesManager.GAME_NAME).size());

    }

    /**
     * Test whether getScore works
     */
    @Test
    public void testGetScore() {
        setUpCorrect();
        User user = new User("Dianna", "Dianna");
        GameLauncher.setCurrentUser(user);
        SlidingTilesManager boardManager = (SlidingTilesManager) GameLauncher.getCurrentUser().getRecentManagerOfBoard(SlidingTilesManager.GAME_NAME);

        //When no moves have been completed
        assertEquals(-1, boardManager.getScore());

        //When you have completed some moves on a 4x4 board
        ArrayList arrayList = new ArrayList();
        arrayList.add(1);
        arrayList.add(1);
        arrayList.add(2);
        arrayList.add(3);
        user.pushGameStates(SlidingTilesManager.GAME_NAME, arrayList);
        user.pushGameStates(SlidingTilesManager.GAME_NAME, arrayList);
        user.pushGameStates(SlidingTilesManager.GAME_NAME, arrayList);
        assertEquals(6667, boardManager.getScore());

        //When you have completed some moves on a 5x5 board
        boardManager.getBoard().setDimensions(5);
        assertEquals(10000, boardManager.getScore());

        //When you have completed some moves on a 3x3 board
        boardManager.getBoard().setDimensions(3);
        assertEquals(3333, boardManager.getScore());

    }

    /**
     * Test isSolvable works
     */
    @Test
    public void testIsSolvable() {
        setUpCorrect();

        //solvable with an even-sized board
        assertTrue(boardManager.isSolvable());

        //unsolvable with an even-sized board
        boardManager.getBoard().swapTiles(0, 0, 2, 2);
        assertFalse(boardManager.isSolvable());

        //solvable with an odd-sized board
        List<SlidingTile> tiles = makeTiles();
        SlidingTilesBoard board = new SlidingTilesBoard(tiles);
        SlidingTilesBoard.setDimensions(3);
        boardManager.setBoard(board);
        assertTrue(boardManager.isSolvable());

        //unsolvable with an odd-sized board
        boardManager.getBoard().swapTiles(0, 0, 2, 2);
        assertFalse(boardManager.isSolvable());
    }

    //These tests are for Tile class:
    /**
     * Test whether the constructor of Tiles class works
     */
    @Test
    public void testSetUpTiles() {
        SlidingTilesBoard.setDimensions(5);
        List<SlidingTile> tiles = makeTiles();
        SlidingTilesBoard board = new SlidingTilesBoard(tiles);
        assertEquals(25, board.numTiles());
        SlidingTile slidingTile = new SlidingTile(25);
        assertEquals(R.drawable.tile_blank, slidingTile.getBackground());
    }


    /**
     * Test whether compareTo works
     */
    @Test
    public void testCompareTo() {
        setUpCorrect();
        SlidingTile comparingTile = boardManager.getBoard().getTile(1,1);
        assertEquals(5, boardManager.getBoard().getTile(0,0).compareTo(comparingTile));
    }
}


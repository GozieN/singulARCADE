package fall2018.csc2017.slidingtiles;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 * Unit tests for the User class.
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */


public class UserAndGameLauncherTest {
    /** The user for testing. */
    User user;

    /** the board for testing. */
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
     * Make a user.
     */
    private void setUpCorrect() {
        user = new User("TestingUsername", "TestingPassword");
        GameLauncher.setCurrentUser(user);
        Board.setDimensions(4);
        List<Tile> tiles = makeTiles();
        board = new Board(tiles);
        user.setRecentManagerOfBoard(BoardManager.GAME_NAME, new BoardManager(board));
    }

    //These tests are for User class
    /**
     * Test getUsername works
     */
    @Test
    public void testGetUsername() {
        setUpCorrect();
        assertEquals("TestingUsername", user.getUsername());
    }

    /**
     * Test getPassword works
     */
    @Test
    public void testGetPassword() {
        setUpCorrect();
        assertEquals("TestingPassword", user.getPassword());
    }

    /**
     * Test getStackOfGameState works
     */
    @Test
    public void testGetStackOfGameStates() {
        setUpCorrect();
        //The stack exists but is empty
        ((BoardManager) GameLauncher.getCurrentUser().getRecentManagerOfBoard(BoardManager.GAME_NAME)).touchMove(1);
        assertEquals(0, GameLauncher.getCurrentUser().getStackOfGameStates(BoardManager.GAME_NAME).size());

        //The stack exists and there is one item present
        ((BoardManager) GameLauncher.getCurrentUser().getRecentManagerOfBoard(BoardManager.GAME_NAME)).touchMove(11);
        assertEquals(1, GameLauncher.getCurrentUser().getStackOfGameStates(BoardManager.GAME_NAME).size());

        //The stack does not exist
        assertEquals(0, GameLauncher.getCurrentUser().getStackOfGameStates("NonExistentGame").size());
    }

    /**
     * Test getState works
     */
    @Test
    public void testGetState() {
        setUpCorrect();

        //The stack exists and there is a state present
        ((BoardManager) GameLauncher.getCurrentUser().getRecentManagerOfBoard(BoardManager.GAME_NAME)).touchMove(11);
        ArrayList arrayList = new ArrayList();
        arrayList.add(2);
        arrayList.add(3);
        arrayList.add(3);
        arrayList.add(3);
        assertEquals(arrayList, GameLauncher.getCurrentUser().getState(BoardManager.GAME_NAME));

        //The stack does not exist
        assertEquals(null, GameLauncher.getCurrentUser().getState("NonExistentGame"));
    }

    /**
     * Test pushGameStates works
     */
    @Test
    public void testPushGameStates() {
        setUpCorrect();
        ArrayList arrayList = new ArrayList();
        arrayList.add(2);
        arrayList.add(3);
        arrayList.add(3);
        arrayList.add(3);

        //before having any moves occur
        assertEquals(0, GameLauncher.getCurrentUser().getStackOfGameStates(BoardManager.GAME_NAME).size());

        //when one move has been completed
        GameLauncher.getCurrentUser().pushGameStates(BoardManager.GAME_NAME, arrayList);
        assertEquals(1, GameLauncher.getCurrentUser().getStackOfGameStates(BoardManager.GAME_NAME).size());

        //when the board manager does not exist
        GameLauncher.getCurrentUser().pushGameStates("NonExistentBoard", arrayList);
        assertEquals(1, GameLauncher.getCurrentUser().getStackOfGameStates("NonExistentBoard").size());
    }

    /**
     * Test getRecentManagerOfBoard and setRecentManagerOfBoard works
     */
    @Test
    public void testGetAndSetRecentManagerOfBoard() {
        setUpCorrect();
        //original board is set to 4x4
        assertEquals(4, ((BoardManager) GameLauncher.getCurrentUser().getRecentManagerOfBoard(BoardManager.GAME_NAME)).getBoard().getDimensions());
        BoardManager newBoardManager = new BoardManager();

        //board is now being set to 3x3
        newBoardManager.getBoard().setDimensions(3);
        assertEquals(3, ((BoardManager) GameLauncher.getCurrentUser().getRecentManagerOfBoard(BoardManager.GAME_NAME)).getBoard().getDimensions());

        //The manager does not exist
        assertEquals(null, GameLauncher.getCurrentUser().getRecentManagerOfBoard("NonExistentBoard"));

        //Now create a manager that previously did not exist
        Board.setDimensions(5);
        List<Tile> tiles = makeTiles();
        board = new Board(tiles);
        GameLauncher.getCurrentUser().setRecentManagerOfBoard("NonExistentBoard", board);
        assertEquals(board, GameLauncher.getCurrentUser().getRecentManagerOfBoard("NonExistentBoard"));
    }


    //These tests are for GameLauncher class
    /**
     * Test getCurrentUser and setCurrentUser.
     */
    @Test
    public void testSetAndGetCurrentUser() {
        setUpCorrect();
        User newUser = new User("Test2", "TestingPassword");
        GameLauncher.setCurrentUser(newUser);
        assertEquals(newUser, GameLauncher.getCurrentUser());
    }

}

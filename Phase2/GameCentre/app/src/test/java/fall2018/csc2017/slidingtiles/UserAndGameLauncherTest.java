package fall2018.csc2017.slidingtiles;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import fall2018.csc2017.slidingtiles.Games.SlidingTiles.SlidingTile;
import fall2018.csc2017.slidingtiles.Games.SlidingTiles.SlidingTilesBoard;
import fall2018.csc2017.slidingtiles.Games.SlidingTiles.SlidingTilesManager;

import static org.junit.Assert.assertEquals;

/**
 * Example local unit test, which will execute on the development machine (host).
 * Unit tests for the User class.
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */


public class UserAndGameLauncherTest {
    /** The user for testing. */
    User user;

    /** the board for testing. */
    SlidingTilesBoard board;

    /**
     * Make a set of tiles that are in order.
     * @return a set of tiles that are in order
     */
    private List<SlidingTile> makeTiles() {
        List<SlidingTile> tiles = new ArrayList<>();
        final int numTiles = SlidingTilesBoard.getNumRows() * SlidingTilesBoard.getNumCols();
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
     * Make a user.
     */
    private void setUpCorrect() {
        user = new User("TestingUsername", "TestingPassword");
        GameLauncher.setCurrentUser(user);
        SlidingTilesBoard.setDimensions(4);
        List<SlidingTile> tiles = makeTiles();
        board = new SlidingTilesBoard(tiles);
        user.setRecentManagerOfBoard(SlidingTilesManager.getGameName(), new SlidingTilesManager(board));
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
        ((SlidingTilesManager) GameLauncher.getCurrentUser().getRecentManagerOfBoard(SlidingTilesManager.getGameName())).touchMove(1);
        assertEquals(0, GameLauncher.getCurrentUser().getStackOfGameStates(SlidingTilesManager.getGameName()).size());

        //The stack exists and there is one item present
        ((SlidingTilesManager) GameLauncher.getCurrentUser().getRecentManagerOfBoard(SlidingTilesManager.getGameName())).touchMove(11);
        assertEquals(1, GameLauncher.getCurrentUser().getStackOfGameStates(SlidingTilesManager.getGameName()).size());

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
        ((SlidingTilesManager) GameLauncher.getCurrentUser().getRecentManagerOfBoard(SlidingTilesManager.getGameName())).touchMove(11);
        ArrayList arrayList = new ArrayList();
        arrayList.add(2);
        arrayList.add(3);
        arrayList.add(3);
        arrayList.add(3);
        assertEquals(arrayList, GameLauncher.getCurrentUser().getState(SlidingTilesManager.getGameName()));

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
        assertEquals(0, GameLauncher.getCurrentUser().getStackOfGameStates(SlidingTilesManager.getGameName()).size());

        //when one move has been completed
        GameLauncher.getCurrentUser().pushGameStates(SlidingTilesManager.getGameName(), arrayList);
        assertEquals(1, GameLauncher.getCurrentUser().getStackOfGameStates(SlidingTilesManager.getGameName()).size());

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
        assertEquals(4, ((SlidingTilesManager) GameLauncher.getCurrentUser().getRecentManagerOfBoard(SlidingTilesManager.getGameName())).getBoard().getDimensions());

        //board is now being set to 3x3
        SlidingTilesManager newBoardManager = new SlidingTilesManager();
        SlidingTilesBoard.setDimensions(3);
        assertEquals(3, ((SlidingTilesManager) GameLauncher.getCurrentUser().getRecentManagerOfBoard(SlidingTilesManager.getGameName())).getBoard().getDimensions());

        //The manager does not exist
        assertEquals(null, GameLauncher.getCurrentUser().getRecentManagerOfBoard("NonExistentBoard"));

        //Now create a manager that previously did not exist
        SlidingTilesBoard.setDimensions(5);
        List<SlidingTile> tiles = makeTiles();
        board = new SlidingTilesBoard(tiles);
        GameLauncher.getCurrentUser().setRecentManagerOfBoard("NonExistentBoard", board);
        assertEquals(board, GameLauncher.getCurrentUser().getRecentManagerOfBoard("NonExistentBoard"));
    }

    /**
     * Test setEmptyStackOfGameStates
     */
    @Test
    public void testSetEmptyStackOfGameStates() {
        setUpCorrect();
        GameLauncher.getCurrentUser().setEmptyStackOfGameStates(SlidingTilesManager.getGameName());
        assertEquals(0, GameLauncher.getCurrentUser().getStackOfGameStates(SlidingTilesManager.getGameName()).size());

        //The key is not already in the hashmap
        GameLauncher.getCurrentUser().setEmptyStackOfGameStates("New Game");
        assertEquals(0, GameLauncher.getCurrentUser().getStackOfGameStates("New Game").size());
    }

    /**
     * Test setNumOfUndos and getNumOfUndos works
     */
    @Test
    public void testSetAndGetNumOfUndos() {
        setUpCorrect();

        //the key exists in the hashmap
        GameLauncher.getCurrentUser().setNumOfUndos(SlidingTilesManager.getGameName(), 0);
        GameLauncher.getCurrentUser().setNumOfUndos(SlidingTilesManager.getGameName(), 3);
        assertEquals(3, GameLauncher.getCurrentUser().getNumOfUndos(SlidingTilesManager.getGameName()));

        //the key does not exist in the hashmap
        GameLauncher.getCurrentUser().setNumOfUndos("New Game", 8);
        assertEquals(8, GameLauncher.getCurrentUser().getNumOfUndos("New Game"));
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

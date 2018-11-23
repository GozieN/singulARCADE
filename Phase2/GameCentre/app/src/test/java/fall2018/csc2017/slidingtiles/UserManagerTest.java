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

public class UserManagerTest {
    /** The user manager for testing. */
    UserManager userManager;

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
     * Make a userManager
     */
    private void setUpCorrect() {
        userManager = new UserManager();
        userManager.signUp("TestingUsername", "TestingPassword");
    }

    /**
     * Test signUp works
     */
    @Test
    public void testSignUp() {
        setUpCorrect();
        assertEquals(1, userManager.getListOfUsers().size());

        //trying to make a new account when someone with this username already exists
        userManager.signUp("TestingUsername", "TestingPassword2");
        assertEquals(1, userManager.getListOfUsers().size());

        //make a new account
        userManager.signUp("TestingUsername2", "TestingPassword2");
        assertEquals(2, userManager.getListOfUsers().size());
    }

    /**
     * Test signIn works
     */
    @Test
    public void testSignIn() {
        setUpCorrect();
        //the user exists and the password is correct
        assertEquals(true, userManager.signIn("TestingUsername", "TestingPassword"));

        //the user exists, but the password is incorrect
        assertEquals(false, userManager.signIn("TestingUsername", "WrongPassword"));

        //the user does not exist
        assertEquals(false, userManager.signIn("NonExistentUser", "TestingPassword"));

    }

    /**
     * Test findUser works
     */
    @Test
    public void testFindUser() {
        setUpCorrect();
        //user exists
        assertEquals(userManager.getListOfUsers().get(0), userManager.findUser("TestingUsername"));

        //user does not exist
        assertEquals(null, userManager.findUser("NonExistentUser"));
    }

    /**
     * Test getListOfUsers works
     */
    @Test
    public void testGetListOfUsers() {
        setUpCorrect();
        assertEquals(1, userManager.getListOfUsers().size());
    }
}

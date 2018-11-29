package fall2018.csc2017.slidingtiles;

import org.junit.Test;

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
     * Make a userManager
     */
    private void setUpCorrect() {
        userManager = new UserManager();
        userManager.signUp("TestingUsername", "TestingPassword");
    }

    /**
     * Test signIn works
     */
    @Test
    public void testSignIn() {
        setUpCorrect();
        //there are no users present
        userManager.emptyListOfUsers();
        assertEquals(false, userManager.signIn("NoUsers", "NoPasswords"));

        setUpCorrect();

        //the user exists and the password is correct
        assertEquals(true, userManager.signIn("TestingUsername", "TestingPassword"));

        //the user exists, but the password is incorrect
        assertEquals(false, userManager.signIn("TestingUsername", "WrongPassword"));

        //the user does not exist
        assertEquals(false, userManager.signIn("NonExistentUser", "TestingPassword"));
    }

    /**
     * Test emptyListOfUsers
     */
    @Test
    public void testEmptyListOfUsers() {
        setUpCorrect();
        userManager.emptyListOfUsers();
        assertEquals(0, userManager.getListOfUsers().size());
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

    /**
     * Test lastAddedUser works
     */
    @Test
    public void testLastAddedUser() {
        setUpCorrect();
        assertEquals("TestingUsername", userManager.lastAddedUser().getUsername());
    }
}

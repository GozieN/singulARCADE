package fall2018.csc2017.slidingtiles;

import java.io.Serializable;

/**
 * The class that launches the game
 */
class GameLauncher implements Serializable {

    /**
     * Current user who is playing the game.
     */
    private static User currentUser;

    /**
     * Return the current user who is playing the game.
     *
     * @return current user who is playing the game.
     */
    static User getCurrentUser() {
        return currentUser;
    }

    /**
     * Set the current user who is going to play the game.
     *
     * @param newUser the current user that is on the device
     */
    static void setCurrentUser(User newUser) {
        currentUser = newUser;
    }

}

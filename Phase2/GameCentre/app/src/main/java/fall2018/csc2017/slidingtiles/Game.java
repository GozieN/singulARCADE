package fall2018.csc2017.slidingtiles;

/**
 * An interface to be implemented by various games.
 */

public interface Game {
    /**
     * Return true if the game is over.
     * @return true iff the game is over
     */
      boolean isOver();


    /**
     * Return true if the certain move chosen is possible to complete.
     * @return true iff the certain move chosen is possible to complete
     */
      boolean isValidTap(int obj);

    /**
     * REturn the score of the player once they have completed the game (once the game is over).
     * @return the score of the player once they have completed the game (once the game is over).
     *
     */
      int getScore();

}

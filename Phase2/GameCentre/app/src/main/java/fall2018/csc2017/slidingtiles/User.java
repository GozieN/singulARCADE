package fall2018.csc2017.slidingtiles;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Stack;
import java.util.List;


/**
 * An individual user.
 */
public class User implements Serializable{
    private static final long serialVersionUID = 1L;
    /**
     * The username of an individual user.
     */
    public String username;

    /**
     * The password of an individual user.
     */
    private String password;

    /**
     * The saved states of each game that the user plays.
     */
    private HashMap<String, Stack<List>> gameStates;

    /**
     * The user's ScoreBoard
     */
    public ScoreBoard userScoreBoard;

    /**
     * The most recent board of each game that the user plays.
     */
    private HashMap<String, Object> recentManagerOfBoard;

    /**
     * A new user with a unique username and password.
     *
     * @param username the username for the user
     * @param password the password for the user
     */
    User(String username, String password) {
        this.username = username;
        this.password = password;
        this.gameStates = new HashMap<>();
        gameStates.put(BoardManager.GAME_NAME, new Stack<List>());
        this.recentManagerOfBoard = new HashMap<>();
        recentManagerOfBoard.put(BoardManager.GAME_NAME, new BoardManager());
        this.userScoreBoard = new ScoreBoard();
    }

    /**
     * Return the user's username
     *
     * @return the user's username
     */
    String getUsername() {return this.username; }

    /**
     * Return the user's password.
     *
     * @return password
     */
    String getPassword() {
        return this.password;
    }

    /**
     * Return the current state of a particular game.
     *
     * @param game the game being played
     * @return the current state
     */
    List getState(String game) {
        if (gameStates.containsKey(game)) {
                Stack gameState = gameStates.get(game);
                return (List) gameState.pop();
        }
        return null;
    }

    /**
     *
     * @param game the game being played
     * @return the stack of all the game states of a particular game
     */
    Stack<List> getStackOfGameStates(String game) {
        if (gameStates.containsKey(game)) {
            return gameStates.get(game);
        }
        return new Stack<>();
    }

    /**
     * Modify the stack of current game states of a particular game
     * @param game the game being played
     * @param move the List of moves will always be of size 4, with it in orientation [row1, col1, ro2, col2] of the original move
     */
    void pushGameStates(String game, List move) {
        if (gameStates.containsKey(game)) {
            gameStates.get(game).push(move);
        }
        else {
            Stack<List> newStack = new Stack<>();
            newStack.push(move);
            gameStates.put(game, newStack);
        }
    }

    /**
     * Return the most recent manager of the board from the specified game.
     *
     * @param game the game being played
     * @return the most recent board from the specified game.
     */
    Object getRecentManagerOfBoard (String game) {
        if (recentManagerOfBoard.containsKey(game)) {
            return recentManagerOfBoard.get(game);
        }
        return null;
    }

    /**
     * Modify the most recent manager of the board from the specified game.
     *
     * @param game the game being played
     * @return the most recent board from the specified game.
     */
    void setRecentManagerOfBoard (String game, Object newManagerOfBoard) {
        if (recentManagerOfBoard.containsKey(game)) {
            recentManagerOfBoard.replace(game, newManagerOfBoard);
        }
        else {recentManagerOfBoard.put(game, newManagerOfBoard);}
    }
}


package fall2018.csc2017.slidingtiles.Controllers;

import android.content.Context;
import android.widget.Button;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;

import fall2018.csc2017.slidingtiles.GameLauncher;
import fall2018.csc2017.slidingtiles.Games.Board;
import fall2018.csc2017.slidingtiles.Games.SlidingTiles.SlidingTilesBoard;
import fall2018.csc2017.slidingtiles.Games.SlidingTiles.SlidingTilesManager;
import fall2018.csc2017.slidingtiles.Views.SetUpActivity;

/**
 * The Sliding Tiles Movement Controller
 */
public class PlaySlidingTilesController {

    /**
     * The board manager.
     */
    private SlidingTilesManager slidingTilesManager;

    /**
     * The buttons to display.
     */
    private ArrayList<Button> tileButtons;

    /**
     * The number of times a user has clicked the undo button.
     */
    private static int numberOfUndos = 0;
    /**
     * The number of times a user has made a move.
     */

    private static int numberOfMoves = 0;

    /**
     * Create the buttons for displaying the tiles.
     *
     * @param context the context
     */
    public void createTileButtons(Context context, SlidingTilesManager inputSlidingTilesManager) {
        slidingTilesManager = inputSlidingTilesManager;
        Board board = slidingTilesManager.getBoard();
        tileButtons = new ArrayList<>();
        for (int row = 0; row != SlidingTilesBoard.getNumRows(); row++) {
            for (int col = 0; col != SlidingTilesBoard.getNumCols(); col++) {
                Button tmp = new Button(context);
                tmp.setBackgroundResource(board.getTile(row, col).getBackground());
                this.tileButtons.add(tmp);
            }
        }
    }

    /**
     * Update the backgrounds on the buttons to match the tiles.
     */
    public ArrayList<Button> updateTileButtons() {
        SlidingTilesBoard board = slidingTilesManager.getBoard();
        int nextPos = 0;
        for (Button b : tileButtons) {
            int row = nextPos / SlidingTilesBoard.getNumRows();
            int col = nextPos % SlidingTilesBoard.getNumCols();
            b.setBackgroundResource(board.getTile(row, col).getBackground());
            nextPos++;
        }
        return tileButtons;
    }

    /**
     * Return a string that corresponds to if an undo can be used or that are available
     *
     * @return a string that corresponds to if an undo can be used or that are available
     */
    public String usedNumberOfUndos() {
        numberOfUndos = GameLauncher.getCurrentUser().getNumOfUndos(SlidingTilesManager.getGameName());
        if (numberOfUndos < SetUpActivity.getUndoLimit()) {
            Stack totalStates = GameLauncher.getCurrentUser().getStackOfGameStates(SlidingTilesManager.getGameName());
            if (totalStates.size() != 0) {
                List state = GameLauncher.getCurrentUser().getState(SlidingTilesManager.getGameName());
                int row1 = (Integer) state.get(2);
                int col1 = (Integer) state.get(3);
                int row2 = (Integer) state.get(0);
                int col2 = (Integer) state.get(1);
                slidingTilesManager.getBoard().swapTiles(row1, col1, row2, col2);
                numberOfUndos++;
                GameLauncher.getCurrentUser().setNumOfUndos(SlidingTilesManager.getGameName(), numberOfUndos);
                return "setNumberOfMovesText";
            } else {
                return "NoUndoText";
            }
        }
        return "UndoLimitText";
    }

    /**
     * At the end of the game, do these actions: get the score, and send score to game score board and user score board.
     */
    public List<Boolean> endOfGame(SlidingTilesManager slidingTilesManager) {
        Integer score = slidingTilesManager.getScore();
        boolean newGameScore = SlidingTilesManager.getGameScoreboard().takeNewScore(GameLauncher.getCurrentUser().getUsername(), score);
        boolean newUserScore = GameLauncher.getCurrentUser().userScoreBoard.takeNewScore(SlidingTilesManager.getGameName(), score);
        return Arrays.asList(newGameScore, newUserScore);
    }

    /**
     * Increase moves by 1 each time a move is made
     */
    public static void incrementNumberOfMoves() {
        numberOfMoves++;
    }

    /**
     * Return the number of moves made by the user.
     *
     * @return number of moves
     */
    public static int getNumberOfMoves() {
        return numberOfMoves;
    }

    public static void setNumberOfMoves(int moves) { numberOfMoves = moves; }

    /**
     * Return the number of undos made by the user.
     *
     * @return number of undos
     */
    public static int getNumberOfUndos() {
        return numberOfUndos;
    }

}

package fall2018.csc2017.slidingtiles.Controllers;

import android.content.Context;
import android.widget.Button;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;

import fall2018.csc2017.slidingtiles.GameLauncher;
import fall2018.csc2017.slidingtiles.Games.PegSolitaire.PegSolitaireBoard;
import fall2018.csc2017.slidingtiles.Games.PegSolitaire.PegSolitaireManager;
import fall2018.csc2017.slidingtiles.Views.SetUpActivity;

/**
 * The Movement controller for Peg Solitaire
 */
public class PlayPegSolitaireController {

    /**
     * The board manager.
     */
    private PegSolitaireManager pegSolitaireManager;

    /**
     * The buttons to display.
     */
    private ArrayList<Button> tileButtons;

    /**
     * The number of times a user has clicked the undo button.
     */
    private static int numberOfUndos = 0;
    /**
     * The number of moves a user has made
     */
    private static int numberOfMoves = 0;

    /**
     * Update the backgrounds on the buttons to match the tiles.
     */
    public ArrayList<Button> updateTileButtons() {
        PegSolitaireBoard board = pegSolitaireManager.getBoard();
        int nextPos = 0;
        for (Button b : tileButtons) {
            int row = nextPos / PegSolitaireBoard.getNumRows();
            int col = nextPos % PegSolitaireBoard.getNumCols();
            b.setBackgroundResource(board.getPegTile(row, col).getBackground());
            nextPos++;
        }
        return tileButtons;
    }

    /**
     * Create the buttons for displaying the tiles.
     *
     * @param context the context
     */
    public void createTileButtons(Context context, PegSolitaireManager inputPegSolitaireManager) {
        pegSolitaireManager = inputPegSolitaireManager;
        PegSolitaireBoard board = pegSolitaireManager.getBoard();
        tileButtons = new ArrayList<>();
        for (int row = 0; row != PegSolitaireBoard.getNumRows(); row++) {
            for (int col = 0; col != PegSolitaireBoard.getNumCols(); col++) {
                Button tmp = new Button(context);
                tmp.setBackgroundResource(board.getPegTile(row, col).getBackground());
                this.tileButtons.add(tmp);
            }
        }
    }

    /**
     * Return a string that corresponds to if an undo can be used or that are available
     *
     * @return a string that corresponds to if an undo can be used or that are available
     */
    public String usedNumberOfUndos() {
        numberOfUndos = GameLauncher.getCurrentUser().getNumOfUndos(PegSolitaireManager.getGameName());
        if (numberOfUndos < SetUpActivity.getUndoLimit()) {
            Stack totalStates = GameLauncher.getCurrentUser().getStackOfGameStates(PegSolitaireManager.getGameName());
            if (totalStates.size() != 0) {
                List state = GameLauncher.getCurrentUser().getState(PegSolitaireManager.getGameName());
                int row1 = (Integer) state.get(2);
                int col1 = (Integer) state.get(3);
                int row2 = (Integer) state.get(0);
                int col2 = (Integer) state.get(1);
                pegSolitaireManager.getBoard().undoMove(row1, col1, row2, col2);
                numberOfUndos++;
                GameLauncher.getCurrentUser().setNumOfUndos(PegSolitaireManager.getGameName(), numberOfUndos);
                return "setNumberOfMovesText";
            } else {
                return "NoUndoText";
            }
        } else {
            return "UndoLimitText";
        }
    }

    /**
     * At the end of the game, do these actions: get the score, and send score to game score board and user score board.
     */
    public List<Boolean> endOfGame(PegSolitaireManager pegSolitaireManager) {
        Integer score = pegSolitaireManager.getScore();
        boolean newGameScore = PegSolitaireManager.getGameScoreboard().takeNewScore(GameLauncher.getCurrentUser().getUsername(), score);
        boolean newUserScore = GameLauncher.getCurrentUser().userScoreBoard.takeNewScore(PegSolitaireManager.getGameName(), score);
        return Arrays.asList(newGameScore, newUserScore);
    }

    /**
     * Increment the number of moves
     */
    public static void incrementNumberOfMoves() {
        numberOfMoves++;
    }

    /**
     * The number of moves that have been made
     *
     * @return number of moves
     */
    public static int getNumberOfMoves() {
        return numberOfMoves;
    }

    public static void setNumberOfMoves(int moves) { numberOfMoves = moves; }
    /**
     * the Number of undos that have been made
     *
     * @return the number of undos
     */
    public int getNumberOfUndos() {
        return numberOfUndos;
    }

}

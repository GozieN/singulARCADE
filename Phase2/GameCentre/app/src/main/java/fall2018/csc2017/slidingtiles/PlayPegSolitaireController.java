package fall2018.csc2017.slidingtiles;

import android.content.Context;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

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
    static int numberOfUndos;

    PlayPegSolitaireController() {}

    /**
     * Update the backgrounds on the buttons to match the tiles.
     */
    public ArrayList<Button> updateTileButtons() {
        PegSolitaireBoard board = pegSolitaireManager.getBoard();
        int nextPos = 0;
        for (Button b : tileButtons) {
            int row = nextPos / PegSolitaireBoard.NUM_ROWS;
            int col = nextPos % PegSolitaireBoard.NUM_COLS;
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
        for (int row = 0; row != PegSolitaireBoard.NUM_ROWS; row++) {
            for (int col = 0; col != PegSolitaireBoard.NUM_COLS; col++) {
                Button tmp = new Button(context);
                tmp.setBackgroundResource(board.getPegTile(row, col).getBackground());
                this.tileButtons.add(tmp);
            }
        }
    }

    public String usedNumberOfUndos() {
        numberOfUndos = GameLauncher.getCurrentUser().getNumOfUndos(PegSolitaireManager.GAME_NAME);
        if (numberOfUndos < SetUpActivity.undoLimit) {
            Stack totalStates = GameLauncher.getCurrentUser().getStackOfGameStates(PegSolitaireManager.GAME_NAME);
            if (totalStates.size() != 0) {
                List state = GameLauncher.getCurrentUser().getState(PegSolitaireManager.GAME_NAME);
                int row1 = (Integer) state.get(2);
                int col1 = (Integer) state.get(3);
                int row2 = (Integer) state.get(0);
                int col2 = (Integer) state.get(1);
                pegSolitaireManager.getBoard().undoMove(row1, col1, row2, col2);
                numberOfUndos++;
                GameLauncher.getCurrentUser().setNumOfUndos(PegSolitaireManager.GAME_NAME, numberOfUndos);
                return "setNumberOfMovesText";
            }
            else {
                return "NoUndoText";
            }
        }
        else {
            return "UndoLimitText";
        }
    }

    /**
     * At the end of the game, do these actions: get the score, and send score to game score board and user score board.
     */
    public void endOfGame(PegSolitaireManager pegSolitaireManager) {
        Integer score = pegSolitaireManager.getScore();
        PegSolitaireManager.pegScoreBoard.takeNewScore(GameLauncher.getCurrentUser().getUsername(), score);
        GameLauncher.getCurrentUser().userScoreBoard.takeNewScore(PegSolitaireManager.GAME_NAME, score);
        //TODO: here maybe save the new stuff?? aka make sure updated score of user is put in and update on overall scoreboard for game
    }

    public int numOfMoves() {
        numberOfUndos = GameLauncher.getCurrentUser().getNumOfUndos(PegSolitaireManager.GAME_NAME);
        if (numberOfUndos > SetUpActivity.undoLimit) {
            numberOfUndos = SetUpActivity.undoLimit;
        }
        int numMoves = 1 + numberOfUndos + GameLauncher.getCurrentUser().getStackOfGameStates(PegSolitaireManager.GAME_NAME).size();
        return numMoves;
    }

    public int numOfUndos() {
        numberOfUndos = GameLauncher.getCurrentUser().getNumOfUndos(PegSolitaireManager.GAME_NAME);
        if (numberOfUndos > SetUpActivity.undoLimit) {
            numberOfUndos = SetUpActivity.undoLimit;
        }
        return numberOfUndos;
    }

}

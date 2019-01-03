package fall2018.csc2017.slidingtiles.Controllers;

import android.content.Context;
import android.widget.Button;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import fall2018.csc2017.slidingtiles.GameLauncher;
import fall2018.csc2017.slidingtiles.Games.MemoryPuzzle.MemoryBoardManager;
import fall2018.csc2017.slidingtiles.Games.MemoryPuzzle.MemoryGameBoard;

/**
 * The movement controller for the Memory Puzzle Game
 */
public class PlayMemoryPuzzleController {

    /**
     * The board manager.
     */
    private MemoryBoardManager memoryBoardManager;

    /**
     * The buttons to display.
     */
    private ArrayList<Button> tileButtons;

    /**
     * The number of times a user has made a move.
     */

    private static int numberOfMoves = 0;

    /**
     * Create the buttons for displaying the tiles.
     *
     * @param context the context
     */
    public void createTileButtons(Context context, MemoryBoardManager inputMemoryPuzzleManager) {
        memoryBoardManager = inputMemoryPuzzleManager;
        MemoryGameBoard board = memoryBoardManager.getBoard();
        tileButtons = new ArrayList<>();
        for (int row = 0; row != MemoryGameBoard.getNumRows(); row++) {
            for (int col = 0; col != MemoryGameBoard.getNumCols(); col++) {
                Button tmp = new Button(context);
                tmp.setBackgroundResource(board.getMemoryGameTile(row, col).getTopLayer());
                this.tileButtons.add(tmp);
            }
        }
    }

    /**
     * Update the backgrounds on the buttons to match the tiles.
     */
    public ArrayList<Button> updateTileButtons() {
        MemoryGameBoard board = memoryBoardManager.getBoard();
        int nextPos = 0;
        for (Button b : tileButtons) {
            int row = nextPos / MemoryGameBoard.getNumRows();
            int col = nextPos % MemoryGameBoard.getNumCols();
            b.setBackgroundResource(board.getMemoryGameTile(row, col).getTopLayer());
            nextPos++;
        }
        return tileButtons;
    }

    /**
     * At the end of the game, do these actions: get the score, and send score to game score board and user score board.
     */
    public List<Boolean> endOfGame(MemoryBoardManager memoryBoardManager) {
        Integer score = memoryBoardManager.getScore();
        boolean newGameScore = MemoryBoardManager.getGameScoreboard().takeNewScore(GameLauncher.getCurrentUser().getUsername(), score);
        boolean newUserScore = GameLauncher.getCurrentUser().userScoreBoard.takeNewScore(MemoryBoardManager.getGameName(), score);
        return Arrays.asList(newGameScore, newUserScore);
    }


    /**
     * Increase moves by 1 each time a move is made
     */

    static void incrementNumberOfMoves() {
        numberOfMoves++;
    }

    public static void setNumberOfMoves(int moves) { numberOfMoves = moves; }

    /**
     * Return the number of moves made by the user.
     *
     * @return int
     */
    public static int getNumberOfMoves() {
        return numberOfMoves;
    }

}

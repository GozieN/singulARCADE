package fall2018.csc2017.slidingtiles;

import android.content.Context;
import android.widget.Button;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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

    public static int numberOfMoves = 0;

    PlayMemoryPuzzleController() {
    }

    /**
     * Create the buttons for displaying the tiles.
     *
     * @param context the context
     */
    public void createTileButtons(Context context, MemoryBoardManager inputMemoryPuzzleManager) {
        memoryBoardManager = inputMemoryPuzzleManager;
        MemoryGameBoard board = memoryBoardManager.getBoard();
        tileButtons = new ArrayList<>();
        for (int row = 0; row != MemoryGameBoard.NUM_ROWS; row++) {
            for (int col = 0; col != MemoryGameBoard.NUM_COLS; col++) {
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
            int row = nextPos / MemoryGameBoard.NUM_ROWS;
            int col = nextPos % MemoryGameBoard.NUM_COLS;
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
        boolean newGameScore = MemoryBoardManager.gameScoreBoard.takeNewScore(GameLauncher.getCurrentUser().getUsername(), score);
        boolean newUserScore = GameLauncher.getCurrentUser().userScoreBoard.takeNewScore(MemoryBoardManager.GAME_NAME, score);
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
     * @return int
     */
    public int getNumberOfMoves() {
        return numberOfMoves;
    }

}

package fall2018.csc2017.slidingtiles.Games.PegSolitaire;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;

import fall2018.csc2017.slidingtiles.Games.Game;
import fall2018.csc2017.slidingtiles.GameLauncher;
import fall2018.csc2017.slidingtiles.ScoreBoard;

/**
 * The Peg Solitaire Manager
 */
public class PegSolitaireManager implements Serializable, Game {
    /**
     * The Peg Solitaire board being managed.
     */
    private final static String GAME_NAME = "PEG SOLITAIRE";
    /**
     * the Peg Solitaire Board that the peg solitaire maanger Manages
     */
    private PegSolitaireBoard pegBoard;
    /**
     * The Peg Solitaire Scoreboard
     */
    private static ScoreBoard pegScoreBoard = new ScoreBoard();

    /**
     * Manage a new starting board.
     */
    public PegSolitaireManager() {
        List<PegSolitaireTile> tiles = new ArrayList<>();
        final int numTiles = PegSolitaireBoard.NUM_ROWS * PegSolitaireBoard.NUM_COLS;
        for (int tileNum = 0; tileNum != numTiles; tileNum++) {
            tiles.add(new PegSolitaireTile(2));
        }
        this.pegBoard = new PegSolitaireBoard(tiles);
    }

    /**
     * Manage a board that has been pre-populated.
     *
     * @param pegSolitaireBoard the Peg Solitaire board
     */
    public PegSolitaireManager(PegSolitaireBoard pegSolitaireBoard) {
        this.pegBoard = pegSolitaireBoard;
    }

    /**
     * Return the current Peg Solitaire board.
     */
    public PegSolitaireBoard getBoard() {
        return pegBoard;
    }

    /**
     * Set a new board.
     */
    public void setBoard(PegSolitaireBoard board) {
        this.pegBoard = board;
        pegBoard.update();
    }

    /**
     * Return true if the game is over.
     *
     * @return true iff the game is over
     */
    public boolean isOver() {
        boolean solved = true;
        for (int x = 0; x < PegSolitaireBoard.NUM_ROWS * PegSolitaireBoard.NUM_COLS; x++) {
            if (!listOfValidMoves(x).isEmpty()) {
                solved = false;
            }
        }
        return solved;
    }

    /**
     * Return true if the player has won i.e. there is only one remaining peg on the board
     *
     * @return true iff the player has gotten rid of all pegs except one
     */
    public boolean hasWon() {
        if (isOver()) {
            return pegBoard.numRemainingPegs() == 1;
        }
        return false;
    }

    /**
     * Allow the user to see the possible moves that can be made based on the position.
     *
     * @param position position of the peg being moved
     */
    public void seePossibleMoves(int position) {
        int row = position / PegSolitaireBoard.NUM_ROWS;
        int col = position % PegSolitaireBoard.NUM_COLS;
        pegBoard.addOrRemoveHighlight(row, col);
        for (List<Integer> move : listOfValidMoves(position)) {
            pegBoard.addOrRemoveHighlight(move.get(0), move.get(1));
        }
        pegBoard.update();

    }

    /**
     * Modify the game state with the move chosen.
     *
     * @param position1 the position of the moving peg
     * @param position2 the position the peg is moving into, should be empty
     */
    public void touchMove(int position1, int position2) {
        int row1 = position1 / PegSolitaireBoard.NUM_ROWS;
        int col1 = position1 % PegSolitaireBoard.NUM_COLS;
        int row2 = position2 / PegSolitaireBoard.NUM_ROWS;
        int col2 = position2 % PegSolitaireBoard.NUM_COLS;
        for (List<Integer> move : listOfValidMoves(position1)) {
            if (move.get(0) != row2 || move.get(1) != col2) {
                pegBoard.addOrRemoveHighlight(move.get(0), move.get(1));
            }
        }

        GameLauncher.getCurrentUser().pushGameStates(GAME_NAME, pegBoard.moveGamepiece(row1, col1, row2, col2));
        pegBoard.update();

    }

    /**
     * Return true if the certain move chosen is possible to complete.
     *
     * @return true iff the certain move chosen is possible to complete
     */
    public boolean isValidTap(int position) {
        return !listOfValidMoves(position).isEmpty();
    }

    /**
     * Return true if the second tap is valid.
     *
     * @return true iff the second tap is valid.
     */
    public boolean isValidSecondTap(int previousMove, int position) {
        int row = position / PegSolitaireBoard.NUM_ROWS;
        int col = position % PegSolitaireBoard.NUM_COLS;

        for (List<Integer> move : listOfValidMoves(previousMove)) {
            if (row == move.get(0) && col == move.get(1)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Return a list of valid moves.
     *
     * @param position the position of the user's tap
     * @return the list of valid moves that can be made in the form of [row, column]
     */
    private List<List<Integer>> listOfValidMoves(int position) {
        int row = position / PegSolitaireBoard.NUM_ROWS;
        int col = position % PegSolitaireBoard.NUM_COLS;
        List<List<Integer>> validMoves = new ArrayList<>();

        //Set the adjacent tiles
        PegSolitaireTile twoAbove = pegBoard.getPegTile(row - 2, col);
        PegSolitaireTile oneAbove = pegBoard.getPegTile(row - 1, col);
        PegSolitaireTile twoBelow = pegBoard.getPegTile(row + 2, col);
        PegSolitaireTile oneBelow = pegBoard.getPegTile(row + 1, col);
        PegSolitaireTile twoLeft = pegBoard.getPegTile(row, col - 2);
        PegSolitaireTile oneLeft = pegBoard.getPegTile(row, col - 1);
        PegSolitaireTile twoRight = pegBoard.getPegTile(row, col + 2);
        PegSolitaireTile oneRight = pegBoard.getPegTile(row, col + 1);

        //Check if the adjacent tiles are null to construct a list of Valid moves.
        if (pegBoard.getPegTile(row, col).getId() == 2) {
            if (twoAbove != null && oneAbove != null && twoAbove.getId() == 1 && oneAbove.getId() == 2) {
                validMoves.add(Arrays.asList(row - 2, col));
            }
            if (twoBelow != null && oneBelow != null && twoBelow.getId() == 1 && oneBelow.getId() == 2) {
                validMoves.add(Arrays.asList(row + 2, col));
            }
            if (twoLeft != null && oneLeft != null && twoLeft.getId() == 1 && oneLeft.getId() == 2) {
                validMoves.add(Arrays.asList(row, col - 2));
            }
            if (twoRight != null && oneRight != null && twoRight.getId() == 1 && oneRight.getId() == 2) {
                validMoves.add(Arrays.asList(row, col + 2));
            }
        }

        return validMoves;
    }

    /**
     * Return the score of the current game
     *
     * @return the score of the current game
     */
    public int getScore() {
        Stack<List> stackOfMoves = GameLauncher.getCurrentUser().getStackOfGameStates(PegSolitaireManager.GAME_NAME);
        double tempScore = Math.pow((stackOfMoves.size() + 2 * GameLauncher.getCurrentUser().getNumOfUndos(PegSolitaireManager.GAME_NAME)), -1);
        //if 6, multiply by 10000
        if (PegSolitaireBoard.NUM_ROWS == 6) {
            return (int) Math.round(tempScore * 10000);
        }
        //if 7, multiply by 20000
        else if (PegSolitaireBoard.NUM_ROWS == 7) {
            return (int) Math.round(tempScore * 20000);
        }
        //if 9, multiply by 30000
        else {
            return (int) Math.round(tempScore * 30000);
        }
    }

    public static String getGameName() {
        return GAME_NAME;
    }

    public static ScoreBoard getGameScoreboard() {
        return pegScoreBoard;
    }
}

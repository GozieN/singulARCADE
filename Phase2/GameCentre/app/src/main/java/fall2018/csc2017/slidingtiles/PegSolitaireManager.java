package fall2018.csc2017.slidingtiles;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.IllegalFormatCodePointException;
import java.util.List;
import java.util.Observable;
import java.util.Stack;

public class PegSolitaireManager implements Serializable, Game {

    /**
     * The Peg Solitaire board being managed.
     */
    final static String GAME_NAME = "Peg Solitaire";
    private PegSolitaireBoard pegBoard;

    static ScoreBoard pegScoreBoard;

    /**
     * Manage a new starting board.
     */
    PegSolitaireManager() {
        List<PegSolitaireTile> tiles = new ArrayList<>();
        final int numTiles = PegSolitaireBoard.NUM_ROWS * PegSolitaireBoard.NUM_COLS;
        for (int tileNum = 0; tileNum != numTiles; tileNum++) {
            tiles.add(new PegSolitaireTile(2));
        }
        this.pegBoard = new PegSolitaireBoard(tiles);
        pegScoreBoard = new ScoreBoard();
    }

    /**
     * Manage a board that has been pre-populated.
     *
     * @param pegSolitaireBoard the Peg Solitaire board
     */
    PegSolitaireManager(PegSolitaireBoard pegSolitaireBoard) {
        this.pegBoard = pegSolitaireBoard;
        pegScoreBoard = new ScoreBoard();
    }

    /**
     * Return the current Peg Solitaire board.
     */
    PegSolitaireBoard getBoard() {
        return pegBoard;
    }

    /**
     * Set a new board.
     */
    void setBoard(Board board) {
        pegScoreBoard = new ScoreBoard();
        board.update();
    }

    /**
     * Return true if the game is over.
     * @return true iff the game is over
     */
    public boolean isOver() {
        boolean solved = false;
        for (int x = 0; x < PegSolitaireBoard.NUM_ROWS; x++) {
            for (int y = 0; y < PegSolitaireBoard.NUM_COLS; y++) {
                if (pegBoard.getPegTile(x, y).getId() != 0) {
                    int position = x * PegSolitaireBoard.NUM_ROWS;
                    if (!listOfValidMoves(position).isEmpty()) {
                        solved = true;
                    }
                }
            }
        }
        return solved;
    }

    /**
     *
     * @param position position of the peg being moved
     */
    public void firstMove(int position) {
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
     * @param position1 the position of the moving peg
     * @param position2 the position the peg is moving into, should be empty
     */
    public void touchMove(int position1, int position2) {
        int row1 = position1 / PegSolitaireBoard.NUM_ROWS;
        int col1 = position1 % PegSolitaireBoard.NUM_COLS;
        int row2 = position2 / PegSolitaireBoard.NUM_ROWS;
        int col2 = position2 % PegSolitaireBoard.NUM_COLS;
        pegBoard.moveGamepiece(row1, col1, row2, col2);

        for (List<Integer> move : listOfValidMoves(position1)) {
            if (move.get(0) != row2 && move.get(1) != col2) {
                pegBoard.addOrRemoveHighlight(move.get(0), move.get(1));
            }
        }
        pegBoard.update();

    }

    /**
     * Return true if the certain move chosen is possible to complete.
     * @return true iff the certain move chosen is possible to complete
     */
    public boolean isValidTap(int position) {
        return !listOfValidMoves(position).isEmpty();
    }

    /**
     * Return true if the second tap is valid.
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
     * Removes any highlights on tiles.
     *
     */
    void removeAllHighlights() {

    }


    private List<List<Integer>> listOfValidMoves(int position) {
        int row = position / PegSolitaireBoard.NUM_ROWS;
        int col = position % PegSolitaireBoard.NUM_COLS;
        List<List<Integer>> validMoves = new ArrayList<>();

        //Is there are valid jump?
        PegSolitaireTile twoAbove = pegBoard.getPegTile(row - 2, col);
        PegSolitaireTile oneAbove = pegBoard.getPegTile(row - 1, col);
        PegSolitaireTile twoBelow = pegBoard.getPegTile(row + 2, col);
        PegSolitaireTile oneBelow = pegBoard.getPegTile(row + 1, col);
        PegSolitaireTile twoLeft = pegBoard.getPegTile(row, col - 2);
        PegSolitaireTile oneLeft = pegBoard.getPegTile(row, col - 1);
        PegSolitaireTile twoRight = pegBoard.getPegTile(row, col + 2);
        PegSolitaireTile oneRight = pegBoard.getPegTile(row, col + 1);

        if (twoAbove != null && oneAbove != null && twoAbove.getId() == 1 && oneAbove.getId() == 2) {
            validMoves.add(Arrays.asList(row - 2, col));
        } if (twoBelow != null && oneBelow != null && twoBelow.getId() == 1 && oneBelow.getId() == 2) {
            validMoves.add(Arrays.asList(row + 2, col));
        } if (twoLeft != null && oneLeft != null && twoLeft.getId() == 1 && oneLeft.getId() == 2) {
            validMoves.add(Arrays.asList(row, col - 2));
        } if (twoRight != null && oneRight != null && twoRight.getId() == 1 && oneRight.getId() == 2) {
            validMoves.add(Arrays.asList(row, col + 2));
        }
        return validMoves;
    }

    /**
     * Return the score of the current game
     * @return the score of the current game
     */
    public int getScore() {
        Stack<List> stackOfMoves= GameLauncher.getCurrentUser().getStackOfGameStates("Peg Solitaire");
        double tempScore = Math.pow((stackOfMoves.size() + 2*PlaySlidingTilesActivity.numberOfUndos), -1);
        //if 6, multiply by 10000
        if (Board.NUM_ROWS == 6) {
            return (int) Math.round(tempScore * 10000);
        }
        //if 7, multiply by 20000
        else if (Board.NUM_ROWS == 7) {
            return (int) Math.round(tempScore * 20000);
        }
        //if 9, multiply by 30000
        else {
            return (int) Math.round(tempScore * 30000);
        }
    }

}

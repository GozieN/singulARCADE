package fall2018.csc2017.slidingtiles;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Observable;
import java.util.Stack;

public class PegSolitaireManager extends Observable implements Serializable, Game {

    /**
     * The Reversi board being managed.
     */
    final static String GAME_NAME = "Peg Solitaire";
    private PegSolitaireBoard pegBoard;

    ScoreBoard pegScoreBoard;

    /**
     * Manage a new starting board.
     */
    PegSolitaireManager() {
        this.pegBoard = new PegSolitaireBoard();
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
        this.pegScoreBoard = new ScoreBoard();
        board.update();
    }

    /**
     * Return true if the game is over.
     * @return true iff the game is over
     */
    public boolean isOver() {
        boolean solved = true;
        for (int x = 0; x < PegSolitaireBoard.NUM_ROWS; x++) {
            for (int y = 0; y < PegSolitaireBoard.NUM_COLS; y++) {
                if (pegBoard.getPegTile(x, y).getId() != 0) {
                    int position = x * PegSolitaireBoard.NUM_ROWS;
                    if (!listOfValidMoves(position).isEmpty()) {
                        solved = false;
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
        List validMoves = listOfValidMoves(position);
        for (List<Integer> move : listOfValidMoves(position)) {
            pegBoard.highlightTile(move.get(0), move.get(1));
        }

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
    }

    /**
     * Return true if the certain move chosen is possible to complete.
     * @return true iff the certain move chosen is possible to complete
     */
    public boolean isValidTap(int position) {
        return listOfValidMoves(position).isEmpty();
    }


    private List<List<Integer>> listOfValidMoves(int position) {
        int row = position / PegSolitaireBoard.NUM_ROWS;
        int col = position % PegSolitaireBoard.NUM_COLS;
        List<List<Integer>> validMoves = new ArrayList<>();

        //Is there are valid jump?
        PegSolitaireTile twoAbove = pegBoard.getPegTile(row, col + 2);
        PegSolitaireTile oneAbove = pegBoard.getPegTile(row, col + 1);
        PegSolitaireTile twoBelow = pegBoard.getPegTile(row, col - 2);
        PegSolitaireTile oneBelow = pegBoard.getPegTile(row, col - 1);
        PegSolitaireTile twoLeft = pegBoard.getPegTile(row - 2, col);
        PegSolitaireTile oneLeft = pegBoard.getPegTile(row - 1, col);
        PegSolitaireTile twoRight = pegBoard.getPegTile(row + 2, col);
        PegSolitaireTile oneRight = pegBoard.getPegTile(row + 1, col);

        if (twoAbove != null && oneAbove != null && twoAbove.getId() == 1 && oneAbove.getId() == 2) {
            validMoves.add(Arrays.asList(row, col + 2));
        } if (twoBelow != null && oneBelow != null && twoBelow.getId() == 1 && oneBelow.getId() == 2) {
            validMoves.add(Arrays.asList(row, col - 2));
        } if (twoLeft != null && oneLeft != null && twoLeft.getId() == 1 && oneLeft.getId() == 2) {
            validMoves.add(Arrays.asList(row - 2, col));
        } if (twoRight != null && oneRight != null && twoRight.getId() == 1 && oneRight.getId() == 2) {
            validMoves.add(Arrays.asList(row + 2, col));
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

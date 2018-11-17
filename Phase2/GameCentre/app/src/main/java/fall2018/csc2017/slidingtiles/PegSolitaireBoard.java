package fall2018.csc2017.slidingtiles;

import java.util.Observable;
import java.io.Serializable;

/**
 * The Peg Solitaire Board.
 */
public class PegSolitaireBoard extends Observable implements Serializable {
    /**
     * The number of rows.
     */
    static int NUM_ROWS;

    /**
     * The number of rows.
     */
    static int NUM_COLS;

    /**
     * The tiles on Reversi's board in row-major order.
     */
    private Integer[][] board = new Integer[NUM_ROWS][NUM_COLS];

    /**
     * A new Reversi board in row-major order.
     */
    PegSolitaireBoard() {
        for (int row = 0; row != PegSolitaireBoard.NUM_ROWS; row++) {
            for (int col = 0; col != PegSolitaireBoard.NUM_COLS; col++) {
                if (numPieces() == 36) {
                    setUpSquareBoard(row, col);
                } else if (numPieces() == 49) {
                    setUpCrossBoard(row, col);
                } else if (numPieces() == 64) {
                    setUpDiamondBoard(row, col);
                }
            }
        }
    }

    void PegSolitaireBoard(Integer[][] tiles) {
        board = tiles;
    }

    /**
     *
     */
    void setUpSquareBoard(int row, int col) {

    }

    /**
     *
     */
    void setUpCrossBoard(int row, int col) {

    }

    /**
     *
     */
    void setUpDiamondBoard(int row, int col) {

    }


    /**
     * Precondition: len(tiles) == NUM_ROWS * NUM_COLS
     *
     * @param board the board for Reversi
     */
    PegSolitaireBoard(Integer[][] board) {
        this.board = board;
    }

    int numPieces() {
        return NUM_ROWS*NUM_COLS;
    }

    /** Changes the dimension constants of the board and makes them n x n
     *
     * @param dimensions the n x n dimensions of Sliding Tiles Board
     */
    static void setDimensions(int dimensions) {
        NUM_COLS = dimensions;
        NUM_ROWS = dimensions;
    }

    /**
     * Make a move on the Peg Solitaire board, i.e. jump over a piece
     *
     * @param row the first tile row
     * @param col the first tile col
     */
    void moveGamepiece(int row, int col) {

    }


    void update() {
        setChanged();
        notifyObservers();
    }

}




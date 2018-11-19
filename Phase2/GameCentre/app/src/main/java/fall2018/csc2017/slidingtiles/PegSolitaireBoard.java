package fall2018.csc2017.slidingtiles;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
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
     * The tiles on Peg Solitaire's board in row-major order.
     */
    private PegSolitaireTile[][] tiles = new PegSolitaireTile[NUM_ROWS][NUM_COLS];

    /**
     * A new Peg Solitaire board in row-major order.
     */
    PegSolitaireBoard(List<PegSolitaireTile> tiles) {
        Iterator<PegSolitaireTile> iter = tiles.iterator();
        for (int row = 0; row != PegSolitaireBoard.NUM_ROWS; row++) {
            for (int col = 0; col != PegSolitaireBoard.NUM_COLS; col++) {
                this.tiles[row][col] = iter.next();
            }
        }
                if (numPieces() == 36) {
                    setUpSquareBoard();
                } else if (numPieces() == 49) {
                    setUpCrossBoard();
                } else if (numPieces() == 81) {
                    setUpDiamondBoard();
                }
    }

    PegSolitaireBoard(PegSolitaireTile[][] tiles) {
        this.tiles = tiles;
    }

    /**
     *
     */
    private void setUpSquareBoard() {
        this.tiles[2][3].setId(1);
        // else id of 2, everything should have a default id of 2 and be assigned a pic??
    }

    /**
     *
     */
    private void setUpCrossBoard() {
        for (int row = 0; row != this.tiles.length; row++) {
            for (int col = 0; col != this.tiles[0].length; col++) {
                if ((col < 2 || col > 4) & (row < 2 || row > 4)) {
                    this.tiles[row][col].setId(0);
                } else if (row == 3 && col == 3) {
                    this.tiles[row][col].setId(1);
                } else {
                    this.tiles[row][col].setId(2);
                }
            }
        }
    }

    /**
     *
     */
    private void setUpDiamondBoard() {
        int i = 4;
        int j = 4;

        for (int row = 0; row != this.tiles.length; row++) {
            fillUpDiamondRow(row, i, j, tiles[row]);
            if (row < 4) {
                i--;
                j++;
            } else {
                i++;
                j--;
            }
        }
    }

    private void fillUpDiamondRow(int row, int i, int j, PegSolitaireTile[] tileRow) {
       for (int k = 0; k != tileRow.length; k++) {
           if (k < i && k > j) {
               this.tiles[row][k].setId(0);
           }
           if (row == 4 && k == 4) {
               this.tiles[4][4].setId(1);
           }
       }
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
     * Return the tile at (row, col)
     *
     * @param row the tile row
     * @param col the tile column
     * @return the tile at (row, col)
     */
    PegSolitaireTile getTile(int row, int col) {
        return tiles[row][col];
    }

    /**
     * Make a move on the Peg Solitaire board, i.e. jump over a piece
     *
     * @param row1 the first tile row
     * @param col1 the first tile col
     * @param row2 the second tile row
     * @param col2 the second tile col
     */
    List moveGamepiece(int row1, int col1, int row2, int col2) {
        PegSolitaireTile temporaryTile = this.getTile(row1, col1);
        tiles[row1][col1] = tiles[row2][col2];
        tiles[row2][col2] = temporaryTile;
        update();
        return Arrays.asList(row1, col1, row2, col2);

        // not done
    }

    void update() {
        setChanged();
        notifyObservers();
    }

}




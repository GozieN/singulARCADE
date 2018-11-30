package fall2018.csc2017.slidingtiles;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * The Peg Solitaire Board.
 */
public class PegSolitaireBoard extends Board {
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

    /**
     * Set up a square peg solitaire board.
     */
    private void setUpSquareBoard() {
        this.tiles[2][3].setId(1, false);

    }

    /**
     * Set up a cross peg solitaire board.
     */
    private void setUpCrossBoard() {
        for (int row = 0; row != this.tiles.length; row++) {
            for (int col = 0; col != this.tiles[0].length; col++) {
                if ((col < 2 || col > 4) & (row < 2 || row > 4)) {
                    this.tiles[row][col].setId(0, false);
                } else if (row == 3 && col == 3) {
                    this.tiles[row][col].setId(1, false);
                }
            }
        }
    }

    /**
     * Set up a diamond peg solitaire board.
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

    /**
     * Set up a row in a diamond peg solitaire board.
     *
     * @param row     the index that indicates the row needing to be filled up
     * @param i       the index indicating the lower bound of the full pegs
     * @param j       the index indicating the upper bound of the full pegs
     * @param tileRow the row of PegSolitaireTiles that need to be set
     */
    private void fillUpDiamondRow(int row, int i, int j, PegSolitaireTile[] tileRow) {
        for (int k = 0; k != tileRow.length; k++) {
            if (k < i || k > j) {
                this.tiles[row][k].setId(0, false);
            } else if (row == 4 && k == 4) {
                this.tiles[4][4].setId(1, false);
            }
        }
    }

    /**
     * Return the number of pieces on the peg solitaire board.
     *
     * @return the number of pegs on the peg solitaire board.
     */
    int numPieces() {
        return NUM_ROWS * NUM_COLS;
    }

    /**
     * Return the tile at (row, col)
     *
     * @param row the tile row
     * @param col the tile column
     * @return the tile at (row, col)
     */
    PegSolitaireTile getOneTile(int row, int col) {
        return tiles[row][col];
    }

    /**
     * Changes the dimension constants of the board and makes them n x n
     *
     * @param dimensions the n x n dimensions of Sliding Tiles Board
     */
    static void setDimensions(int dimensions) {
        NUM_COLS = dimensions;
        NUM_ROWS = dimensions;
    }

    /**
     * Returns the number of columns multiplied by the number of rows.
     *
     * @return the dimensions of the board.
     */
    public int getDimensions() {
        return NUM_COLS * NUM_ROWS;
    }

    /**
     * Return all the tiles for the board
     *
     * @return all the tiles for the board
     */
    PegSolitaireTile[][] getPegTiles() {
        return tiles;
    }

    /**
     * Return the tile at (row, col)
     *
     * @param row the tile row
     * @param col the tile column
     * @return the tile at (row, col)
     */
    PegSolitaireTile getPegTile(int row, int col) {
        if (0 <= row && row < NUM_ROWS && 0 <= col && col < NUM_COLS) {
            return tiles[row][col];
        } else {
            return null;
        }
    }

    /**
     * Make a move on the Peg Solitaire board, i.e. jump over a piece
     *
     * @param row1 the first tile row
     * @param col1 the first tile col
     * @param row2 the second tile row
     * @param col2 the second tile col
     */
    List<Integer> moveGamepiece(int row1, int col1, int row2, int col2) {
        tiles[row1][col1].setId(1, false);
        tiles[row2][col2].setId(2, false);

        if (row2 - row1 == 2) {
            tiles[row1 + 1][col1].setId(1, false);
        } else if (row1 - row2 == 2) {
            tiles[row2 + 1][col1].setId(1, false);
        } else if (col2 - col1 == 2) {
            tiles[row1][col1 + 1].setId(1, false);
        } else if (col1 - col2 == 2) {
            tiles[row1][col2 + 1].setId(1, false);
        }
        update();
        return Arrays.asList(row1, col1, row2, col2);
    }

    /**
     * Undo moves given the appropriate row and columns.
     *
     * @param row1 the row of the first tile
     * @param col1 the col of hte first tile
     * @param row2 the row of the second tile
     * @param col2 the col of the second tile
     */
    void undoMove(int row1, int col1, int row2, int col2) {
        moveGamepiece(row1, col1, row2, col2);
        if (row1 - row2 == 2) {
            tiles[row1 - 1][col1].setId(2, false);
        } else if (row2 - row1 == 2) {
            tiles[row2 - 1][col1].setId(2, false);
        } else if (col2 - col1 == 2) {
            tiles[row1][col1 + 1].setId(2, false);
        } else if (col1 - col2 == 2) {
            tiles[row1][col2 + 1].setId(2, false);
        }
        update();
    }

    /**
     * Highlights/removes highlights for the tile at row, col.
     *
     * @param row the row of the position of the tile
     * @param col the col of the position of the tile
     */
    void addOrRemoveHighlight(int row, int col) {
        PegSolitaireTile tile = getPegTile(row, col);
        tile.setId(tile.getId(), !tile.isHighlight());
    }

    /**
     * Counts number of pegs remaining on Peg Solitaire Board
     *
     * @return number of pegs that are on the Peg Solitaire Board
     */
    int numRemainingPegs() {
        int counter = 0;
        for (PegSolitaireTile[] tileRow : tiles) {
            for (PegSolitaireTile tile : tileRow) {
                if (tile.getId() == 2) {
                    counter++;
                }
            }
        }
        return counter;
    }

    @Override
    public String toString() {
        return "PegSolitaireBoard{" +
                "tiles=" + Arrays.toString(tiles) +
                '}';
    }

    void update() {
        setChanged();
        notifyObservers();
    }

}

package fall2018.csc2017.slidingtiles;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;


class MemoryGameBoard extends Board implements Serializable {
    /**
     * The number of rows.
     */
    static int NUM_ROWS;

    /**
     * The number of rows.
     */
    static int NUM_COLS;

    /***
     * The tiles on the Memory Board in row-major order.
     */
    private MemoryPuzzleTile[][] tiles = new MemoryPuzzleTile[NUM_ROWS][NUM_COLS];


    /**
     * A new board of tiles in row-major order.
     * Precondition: len(tiles) == NUM_ROWS * NUM_COLS
     *
     * @param tiles the tiles for the board
     */
    MemoryGameBoard(List<MemoryPuzzleTile> tiles) {
        Iterator<MemoryPuzzleTile> iter = tiles.iterator();

        for (int row = 0; row != SlidingTilesBoard.NUM_ROWS; row++) {
            for (int col = 0; col != SlidingTilesBoard.NUM_COLS; col++) {
                this.tiles[row][col] = iter.next();
            }
        }
    }


    int numTiles() {
        return NUM_ROWS * NUM_COLS;
    }

    /**
     * Return the tile at (row, col)
     *
     * @param row the tile row
     * @param col the tile column
     * @return the tile at (row, col)
     */
    MemoryPuzzleTile getMemoryPuzzleTile(int row, int col) {
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
     * Return the tile at (row, col)
     *
     * @param row the tile row
     * @param col the tile column
     * @return the tile at (row, col)
     */
    MemoryPuzzleTile getMemoryGameTile(int row, int col) {
        return tiles[row][col];
    }

//    /**
//     * Make a move on the Memory game board, i.e. flip the tile chosen by the user to reveal the
//     * image underneath
//     *
//     * @param row the first tile row
//     * @param col the first tile col
//     */
//      void flipTile(int row, int col) {
//        MemoryPuzzleTile ImageTile = MemoryPuzzleTile.background.getID();
//        MemoryPuzzleTile MemoryPuzzleTile[row][col] = ImageTile;
//        update();
//    }

//    /**
//     * Highlights the tile at row, col to denote it as an available move to the user
//     *
//     * @param row
//     * @param col
//     */
//    void highlightTile(int row, int col) {
//
//    }

    void update() {
        setChanged();
        notifyObservers();
    }


    @Override
    public String toString() {
        return "MemoryBoard{" +
                "tiles=" + Arrays.toString(tiles) +
                '}';
    }
}

package fall2018.csc2017.slidingtiles;

import android.support.annotation.NonNull;

import java.util.Observable;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * The sliding tiles board.
 */
public class Board extends Observable implements Serializable, Iterable<Tile> {

    /**
     * The number of rows.
     */
    static int NUM_ROWS;

    /**
     * The number of rows.
     */
    static int NUM_COLS;

    /**
     * The tiles on the board in row-major order.
     */
    private Tile[][] tiles = new Tile[NUM_ROWS][NUM_COLS];

    /**
     * A new board of tiles in row-major order.
     * Precondition: len(tiles) == NUM_ROWS * NUM_COLS
     *
     * @param tiles the tiles for the board
     */
    Board(List<Tile> tiles) {
        Iterator<Tile> iter = tiles.iterator();

        for (int row = 0; row != Board.NUM_ROWS; row++) {
            for (int col = 0; col != Board.NUM_COLS; col++) {
                this.tiles[row][col] = iter.next();
            }
        }
    }

    /**
     * Return all the tiles for the board
     * @return all the tiles for the board
     */
    Tile[][] getTiles() {return tiles;}


    /**
     * Return the number of tiles on the board.
     *
     * @return the number of tiles on the board
     */
    int numTiles() {
        return NUM_COLS * NUM_ROWS;
    }

    /**
     * Return the tile at (row, col)
     *
     * @param row the tile row
     * @param col the tile column
     * @return the tile at (row, col)
     */
    Tile getTile(int row, int col) {
        return tiles[row][col];
    }

    /** Changes the dimension constants of the board and makes them n x n
     *
     * @param dimensions the n x n dimensions of Sliding Tiles Board
     */
    static void setDimensions(int dimensions) {
        NUM_COLS = dimensions;
        NUM_ROWS = dimensions;
    }

    static int getDimensions() {return NUM_COLS;}


    /**
     * Swap the tiles at (row1, col1) and (row2, col2)
     *
     * @param row1 the first tile row
     * @param col1 the first tile col
     * @param row2 the second tile row
     * @param col2 the second tile col
     */
    List swapTiles(int row1, int col1, int row2, int col2) {
        Tile temporaryTile = this.getTile(row1, col1);
        tiles[row1][col1] = tiles[row2][col2];
        tiles[row2][col2] = temporaryTile;
        update();
        return Arrays.asList(row1, col1, row2, col2);
    }

    void update() {
        setChanged();
        notifyObservers();
    }



    @Override
    public String toString() {
        return "Board{" +
                "tiles=" + Arrays.toString(tiles) +
                '}';
    }


    /** Return a new Iterator over the Tiles in the Board.
     *
     * @return a new Iterator over the Tiles in the Board.
     */
    @NonNull
    @Override
    public Iterator<Tile> iterator() {
        return new BoardIterator();
    }

    /**
     * An iterator for the tiles in the board.
     */
    private class BoardIterator implements Iterator<Tile> {

        /**The row that the next tile in the board is located in.*/
        int row = 0;
        /**The column that the next tile in the board is located in.*/
        int col = 0;

        /** Return whether the board has another tile.
         *
         * @return whether the board has another tile
         */
        @Override
        public boolean hasNext() {
            return col <= NUM_COLS - 1 && row <= NUM_ROWS - 1;
        }

        /** Return the next tile in the board.
         *
         * @return the next tile in the board
         */
        @Override
        public Tile next() {
            if (!hasNext()) {
                throw new NoSuchElementException("No more tiles");
            }

            Tile nextTile = getTile(row, col);
            if (col == NUM_COLS - 1) {
                row++;
                col = 0;
            } else {
                col++;
            }
            return nextTile;
        }
    }
}

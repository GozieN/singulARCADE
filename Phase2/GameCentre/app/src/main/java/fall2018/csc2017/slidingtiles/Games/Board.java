package fall2018.csc2017.slidingtiles.Games;

import java.util.Observable;
import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

/**
 * The sliding tiles board.
 */
public class Board extends Observable implements Serializable {

    /**
     * The number of rows.
     */
    private static int NUM_ROWS;

    /**
     * The number of rows.
     */
    private static int NUM_COLS;

    /***
     * The tiles on the board in row-major order.
     */
    private Tile[][] tiles = new Tile[NUM_ROWS][NUM_COLS];

    /**
     * The constructor for the superclass Board.
     */
    public Board() {
    }

    /**
     *
     */
    public Board(int rows, int cols) {
        NUM_ROWS = rows;
        NUM_COLS = cols;
    }

    /**
     * Return all the tiles for the board
     *
     * @return all the tiles for the board
     */
    public Tile[][] getTiles() {
        return tiles;
    }


    /**
     * Return the number of tiles on the board.
     *
     * @return the number of tiles on the board
     */
    public int numTiles() {
        return NUM_COLS * NUM_ROWS;
    }

    /**
     * Get the tile at (row, col)
     *
     * @param row the tile row
     * @param col the tile column
     * @return the tile at (row, col)
     */
    public Tile getTile(int row, int col) {
        return tiles[row][col];
    }

    /**
     * Get the dimensions of the board.
     *
     * @return the dimensions of the board.
     */
    public int getDimensions() {
        return NUM_COLS;
    }


    /**
     * Swap the tiles at (row1, col1) and (row2, col2)
     *
     * @param row1 the first tile row
     * @param col1 the first tile col
     * @param row2 the second tile row
     * @param col2 the second tile col
     */
    public List swapTiles(int row1, int col1, int row2, int col2) {
        Tile temporaryTile = this.getTile(row1, col1);
        tiles[row1][col1] = tiles[row2][col2];
        tiles[row2][col2] = temporaryTile;
        update();
        return Arrays.asList(row1, col1, row2, col2);
    }

    /**
     * Updates observers on tile positions.
     */
    private void update() {
        setChanged();
        notifyObservers();
    }


    @Override
    public String toString() {
        return "Board{" +
                "tiles=" + Arrays.toString(tiles) +
                '}';
    }
}

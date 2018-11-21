package fall2018.csc2017.slidingtiles;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Stack;

/**
 * Manage a board, including swapping tiles, checking for a win, and managing taps.
 */
class BoardManager implements Serializable, Game {

    /**
     * The board being managed.
     */
     final static String GAME_NAME = "Sliding Tiles";
     private Board board;

     ScoreBoard gameScoreBoard;
    /**
     * Manage a board that has been pre-populated.
     *
     * @param board the board
     */
    BoardManager(Board board) {
        this.board = board;
        gameScoreBoard = new ScoreBoard();
    }

    /**
     * Return the current board.
     */
    Board getBoard() {
        return board;
    }
    /**
     * Set a new board.
     */
    void setBoard(Board board) {
        this.board = board;
        board.update();
    }
    /**
     * Manage a new shuffled board.
     */
    BoardManager() {
        List<Tile> tiles = new ArrayList<>();
        final int numTiles = Board.NUM_ROWS * Board.NUM_COLS;
        for (int tileNum = 0; tileNum != numTiles; tileNum++) {
            if (tileNum == numTiles - 1) {
                tiles.add(new Tile(tileNum, tileNum));
            } else {
                tiles.add(new Tile(tileNum));
            }
        }

        Collections.shuffle(tiles);
        this.board = new Board(tiles);
        gameScoreBoard = new ScoreBoard();
    }


    /**
     * Return the tiles of the current board.
     * @return the tiles of the current board.
     */
    ArrayList<Object> getTilesInArrayList() {
        Iterator<Tile> boardIterator = board.iterator();
        ArrayList arrayList = new ArrayList();
        while (boardIterator.hasNext()) {
            Tile currentTile = boardIterator.next();
            arrayList.add(currentTile.getId());
        }
        return arrayList;
    }

    /**
     * Return whether the tiles are in row-major order.
     *
     * @return whether the tiles are in row-major order
     */
    public boolean isOver() {
        boolean solved = true;
        Iterator<Tile> boardIterator = board.iterator();
        int i = 0;
        while (boardIterator.hasNext() && solved) {
            Tile currentTile = boardIterator.next();
            if (i + 1 != currentTile.getId()) {
                solved = false;
            }
            i++;
        }

        return solved;
    }

    /**
     * Return the row of the board that the blank tile is currently in.
     * @return the row of the board that the blank tile is currently in.
     */
    public int positionBlankTile() {
        int blankId = board.numTiles();
        Iterator<Tile> boardIterator = board.iterator();
        int i = 0;
        while (boardIterator.hasNext()) {
            Tile currentTile = boardIterator.next();
            if (currentTile.getId() == blankId) {
                int returning = i/Board.NUM_COLS + 1;
                return returning;
            }
            i++;
        }
        return Board.NUM_COLS;
    }


    /**
     * Return whether any of the four surrounding tiles is the blank tile.
     *
     * @param position the tile to check
     * @return whether the tile at position is surrounded by a blank tile
     */
    public boolean isValidTap(int position) {

        int row = position / Board.NUM_COLS;
        int col = position % Board.NUM_COLS;
        int blankId = board.numTiles();
        // Are any of the 4 the blank tile?
        Tile above = row == 0 ? null : board.getTile(row - 1, col);
        Tile below = row == Board.NUM_ROWS - 1 ? null : board.getTile(row + 1, col);
        Tile left = col == 0 ? null : board.getTile(row, col - 1);
        Tile right = col == Board.NUM_COLS - 1 ? null : board.getTile(row, col + 1);
        return (below != null && below.getId() == blankId)
                || (above != null && above.getId() == blankId)
                || (left != null && left.getId() == blankId)
                || (right != null && right.getId() == blankId);
    }

    /**
     * Process a touch at position in the board, swapping tiles as appropriate.
     *
     * @param position the position
     */
    public void touchMove(int position) {

        int row = position / Board.NUM_ROWS;
        int col = position % Board.NUM_COLS;
        int blankId = board.numTiles();

        if (this.isValidTap(position)) {

            Tile above = row == 0 ? null : board.getTile(row - 1, col);
            Tile below = row == Board.NUM_ROWS - 1 ? null : board.getTile(row + 1, col);
            Tile left = col == 0 ? null : board.getTile(row, col - 1);
            Tile right = col == Board.NUM_COLS - 1 ? null : board.getTile(row, col + 1);

            if (above != null && above.getId() == blankId) {
                GameLauncher.getCurrentUser().pushGameStates(GAME_NAME, board.swapTiles(row, col, row - 1, col));
            } else if (below != null && below.getId() == blankId) {
                GameLauncher.getCurrentUser().pushGameStates(GAME_NAME, board.swapTiles(row, col, row + 1, col));
            } else if (left != null && left.getId() == blankId) {
                GameLauncher.getCurrentUser().pushGameStates(GAME_NAME, board.swapTiles(row, col, row, col - 1));
            } else if (right != null && right.getId() == blankId) {
                GameLauncher.getCurrentUser().pushGameStates(GAME_NAME, board.swapTiles(row, col, row, col + 1));
            }
        }
    }



    /**
     * Return the score of the current game
     * @return the score of the current game
     */
    public int getScore() {
        Stack<List> stackOfMoves= GameLauncher.getCurrentUser().getStackOfGameStates("Sliding Tiles");
        double tempScore = Math.pow((stackOfMoves.size() + 2*GameActivity.numberOfUndos), -1);
        //if 3, multiply by 10000
        if (Board.NUM_ROWS == 3) {
            return (int) Math.round(tempScore * 10000);
        }
        //if 4, multiply by 20000
        else if (Board.NUM_ROWS == 4) {
            return (int) Math.round(tempScore * 20000);
        }
        //if 5, multiply by 30000
        else {
            return (int) Math.round(tempScore * 30000);
        }
    }

}

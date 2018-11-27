package fall2018.csc2017.slidingtiles;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Stack;
import java.util.TreeMap;

class MemoryBoardManager implements Serializable, Game {
    /**
     * The board being managed.
     */
    final static String GAME_NAME = "Memory Puzzle";
    private MemoryGameBoard board;
    static ScoreBoard gameScoreBoard = new ScoreBoard();

    /**
     * Manage a board that has been pre-populated.
     *
     * @param board the board
     */
    MemoryBoardManager(MemoryGameBoard board) {
        this.board = board;
    }

    /**
     * Return the current board.
     */
    MemoryGameBoard getBoard() {
        return board;
    }

    /**
     * Set a new board.
     */
    void setBoard(MemoryGameBoard board) {
        this.board = board;
        board.update();
    }

    /**
     * Manage a new shuffled board.
     */
    MemoryBoardManager() {
        List<MemoryPuzzleTile> tiles = new ArrayList<>();
        final int numTiles = MemoryGameBoard.NUM_ROWS * MemoryGameBoard.NUM_COLS;
        for (int tileNum = 0; tileNum != numTiles; tileNum++) {
            tiles.add(new MemoryPuzzleTile(tileNum));
        }
        Collections.shuffle(tiles);
        this.board = new MemoryGameBoard(tiles);
        gameScoreBoard = new ScoreBoard();
    }

    /**
     * Return whether the all of the images have been flipped over and therefore matched.
     *
     * @return whether the entire board has been flipped
     * if wanna check IDs - need iterator, otherwise, each time one is matched check the value
     * of a variable
     */
    public boolean isOver() {
        boolean solved = true;
        Iterator<MemoryPuzzleTile> iterator = board.iterator();
        while (iterator.hasNext()) {
            MemoryPuzzleTile currentTile = iterator.next();
            System.out.println(currentTile.getId());
            if (currentTile.getTopLayer() != R.drawable.memory_tile_38) {
                solved = false;
            }
        }
        return solved;
    }


    /**
     * Return the number of MemoryPuzzleTile flipped on the board.
     *
     * @return the number of MemoryPuzzleTile flipped on the board.
     */
    public int numTileFlipped() {
        int flipped = 0;
        Iterator<MemoryPuzzleTile> boarditerator = board.iterator();
        while (boarditerator.hasNext()) {
            MemoryPuzzleTile currentTile = boarditerator.next();
            if (currentTile.getTopLayer() != R.drawable.memory_tile_38 ||
                    currentTile.getTopLayer() != R.drawable.memory_tile_37) {
                flipped++;
            }
        }
        return flipped;
    }

    /**
     * Return whether the user is trying to perform a move on a tile that has already been flipped.
     *
     * @param position the tile to check
     * @return whether the tile at position is greyed out.
     */
    public boolean isValidTap(int position) {
        int row = position / MemoryGameBoard.NUM_COLS;
        int col = position % MemoryGameBoard.NUM_COLS;
        int topLayer = board.getMemoryGameTile(row, col).getTopLayer();
        return numTileFlipped() < 2 && topLayer == R.drawable.memory_tile_37;
    }

    /**
     * Process a touch at position in the board, removing tiles as appropriate.
     *
     * @param position1, position2 the positions of the tiles
     */
    public void greyOut(int position1, int position2) {
        int row1 = position1 / MemoryGameBoard.NUM_ROWS;
        int col1 = position1 % MemoryGameBoard.NUM_COLS;
        int row2 = position2 / MemoryGameBoard.NUM_ROWS;
        int col2 = position2 % MemoryGameBoard.NUM_COLS;

        MemoryPuzzleTile t1 = board.getMemoryGameTile(row1, col1);
        MemoryPuzzleTile t2 = board.getMemoryGameTile(row2, col2);


        if (t1.compareTo(t2) == 0) {
            t1.setTopLayer(R.drawable.memory_tile_38);
            t2.setTopLayer(R.drawable.memory_tile_38);
        } else {
            t1.setTopLayer(R.drawable.memory_tile_37);
            t2.setTopLayer(R.drawable.memory_tile_37);
        }
    }


    /**
     * Make a move on the Memory game board, i.e. flip the tile chosen by the user to reveal the
     * image underneath
     *
     * @param position the position of the tile on the board
     */
    void flipTile(int position) {
        int row = position / MemoryGameBoard.NUM_ROWS;
        int col = position % MemoryGameBoard.NUM_COLS;
        MemoryPuzzleTile tile = board.getMemoryGameTile(row, col);
        tile.setTopLayer(tile.getBackground());
    }


    /**
     * Return the score of the current game
     *
     * @return the score of the current game
     */
    public int getScore() {
        Stack<List> stackOfMoves = GameLauncher.getCurrentUser().getStackOfGameStates("Memory Puzzle");
        double tempScore = Math.pow((stackOfMoves.size()), -1);
        //if 4, multiply by 10000
        if (MemoryGameBoard.NUM_ROWS == 4) {
            return (int) Math.round(tempScore * 10000);
        }
        //if 5, multiply by 20000
        else if (MemoryGameBoard.NUM_ROWS == 5) {
            return (int) Math.round(tempScore * 20000);
        }
        //if 6, multiply by 30000
        else {
            return (int) Math.round(tempScore * 30000);
        }
    }

}


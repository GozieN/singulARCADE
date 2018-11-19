package fall2018.csc2017.slidingtiles;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Stack;

 class MemoryBoardManager implements Serializable, Game{
    /**
     * The board being managed.
     */
    final static String GAME_NAME = "Memory Puzzle";
    private Board board;

    ScoreBoard gameScoreBoard;
    /**
     * Manage a board that has been pre-populated.
     *
     * @param board the board
     */
    MemoryBoardManager(Board board) {
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
    MemoryBoardManager() {
        List<Tile> tiles = new ArrayList<>();
        final int numTiles = Board.NUM_ROWS * Board.NUM_COLS;
        for (int tileNum = 0; tileNum != numTiles; tileNum++) {
            tiles.add(new Tile(tileNum));
            }
        Collections.shuffle(tiles);
        this.board = new Board(tiles);
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
        Iterator<Tile> boardIterator = board.iterator();
        while (boardIterator.hasNext()) {
            Tile currentTile = boardIterator.next();
            if (currentTile.getId() != 0) {
                solved = false;
                return solved;
            }
        }
        return solved;
    }

    /**
     * Return whether the user is trying to perform a move on a tile that has already been flipped.
     *
     * @param position the tile to check
     * @return whether the tile at position is greyed out.
     */
    public boolean isValidTap(int position) {
        Iterator<Tile> boardIterator = board.iterator();
        int place = 0;
        Tile currentTile = null;
        while (place != position && boardIterator.hasNext()){
            currentTile = boardIterator.next();
            place += 1;
        }
        return currentTile.getId() != 0;
    }

    /**
     * Process a touch at position in the board, removing tiles as appropriate.
     *
     * @param position1, position2 the positions of the tiles
     */
    public void touchMove(int position1, int position2) {
        Tile firstTouch = new Tile(position1);
        Tile secondTouch = new Tile(position2);
        int blankId = board.numTiles();

        if (firstTouch.getId()%2 == 0){
            if (firstTouch.getId() == secondTouch.getId() - 1){
                firstTouch.setId(0);
                secondTouch.setId(0);
                }
        else if (firstTouch.getId()%2 != 0){
            if (firstTouch.getId() == secondTouch.getId() + 1){
            firstTouch.setId(0);
            secondTouch.setId(0);
                }}
        else {
                firstTouch.setId(blankId);
                secondTouch.setId(blankId);
            }}
    }
    /**
     * Return the score of the current game
     * @return the score of the current game
     */
    public int getScore() {
        Stack<List> stackOfMoves= GameLauncher.getCurrentUser().getStackOfGameStates("Memory Puzzle");
        double tempScore = Math.pow((stackOfMoves.size() + 2*GameActivity.numberOfUndos), -1);
        //if 4, multiply by 10000
        if (Board.NUM_ROWS == 4) {
            return (int) Math.round(tempScore * 10000);
        }
        //if 5, multiply by 20000
        else if (Board.NUM_ROWS == 5) {
            return (int) Math.round(tempScore * 20000);
        }
        //if 6, multiply by 30000
        else {
            return (int) Math.round(tempScore * 30000);
        }
    }

}


package fall2018.csc2017.slidingtiles.Controllers;

import android.content.Context;
import android.widget.Toast;

import fall2018.csc2017.slidingtiles.Controllers.PlayMemoryPuzzleController;
import fall2018.csc2017.slidingtiles.Controllers.PlayPegSolitaireController;
import fall2018.csc2017.slidingtiles.Controllers.PlaySlidingTilesController;
import fall2018.csc2017.slidingtiles.Games.Game;
import fall2018.csc2017.slidingtiles.Games.MemoryPuzzle.MemoryBoardManager;
import fall2018.csc2017.slidingtiles.Games.MemoryPuzzle.MemoryGameBoard;
import fall2018.csc2017.slidingtiles.Games.MemoryPuzzle.MemoryPuzzleTile;
import fall2018.csc2017.slidingtiles.Games.PegSolitaire.PegSolitaireManager;
import fall2018.csc2017.slidingtiles.Games.SlidingTiles.SlidingTilesManager;
import fall2018.csc2017.slidingtiles.R;

/**
 * The Movement controller that processes taps from the Gesture Detect GridView and makes movements on the baord
 */
class MovementController {
    /**
     * The Game that is currently being played's boardManager
     */
    private Game boardManager = null;
    /**
     * Keeps track of whether it is the first move
     */
    private boolean firstMove = true;
    /**
     * Keeps track of the previous move
     */
    private int previousMove;
    /**
     * Keeps track of the first tap
     */
    private MemoryPuzzleTile firstTap;
    /**
     * Keeps track of the second tap
     */
    private MemoryPuzzleTile secondTap;

    /**
     * Sets the boardManager for the Movement Controller.
     *
     * @param boardManager The boardManager of the Game being played
     */
    void setBoardManager(Game boardManager) {
        this.boardManager = boardManager;
    }

    /**
     * Processes a tap and makes a move accordingly
     *
     * @param context  the context of the game
     * @param position the position of the tap
     */
    void processTapMovement(Context context, int position) {
        if (boardManager instanceof PegSolitaireManager) {
            processPegSolitaireTap(context, position);
        }
        if (boardManager instanceof SlidingTilesManager) {
            processSlidingTilesTap(context, position);
        }
        if (boardManager instanceof MemoryBoardManager) {
            processMemoryPuzzleTap(context, position);
        }
    }

    /**
     * Processes a tap for Sliding Tiles Game
     *
     * @param context  the context of the game
     * @param position the position of the tap
     */
    private void processSlidingTilesTap(Context context, int position) {
        SlidingTilesManager thisBoard = (SlidingTilesManager) boardManager;
        if (thisBoard.isValidTap(position)) {
            PlaySlidingTilesController.incrementNumberOfMoves();
            thisBoard.touchMove(position);
            if (thisBoard.isOver()) {
                makeYouWinToast(context);
            }
        } else {
            makeInvalidTapToast(context);
        }
    }

    /**
     * Processes a tap for Peg Solitaire Game
     *
     * @param context  the context of the game
     * @param position the position of the tap
     */
    private void processPegSolitaireTap(Context context, int position) {
        PegSolitaireManager thisBoard = (PegSolitaireManager) boardManager;
        System.out.println(thisBoard.getScore());

        //On the first tap of Peg Solitaire, simply highlight the possible moves for the user
        if (thisBoard.isValidTap(position) && firstMove) {
            thisBoard.seePossibleMoves(position);
            firstMove = false;
            previousMove = position;

            // On the second tap of Peg Solitaire, make the move given it was a valid position
        } else if (thisBoard.isValidSecondTap(previousMove, position) && !firstMove) {
            PlayPegSolitaireController.incrementNumberOfMoves();
            thisBoard.touchMove(previousMove, position);
            firstMove = true;
            if (thisBoard.isOver()) {
                if (thisBoard.hasWon()) {
                    makeYouWinToast(context);
                }
            }

            //If the User has changed their mind, they can tap the first peg they tapped and cancel their selection
        } else if (position == previousMove && !firstMove) {
            thisBoard.seePossibleMoves(position);
            firstMove = true;
        } else {
            makeInvalidTapToast(context);
        }
    }

    /**
     * Processes a tap for Memory Puzzle Game
     *
     * @param context  the context of the game
     * @param position the position of the tap
     */
    private void processMemoryPuzzleTap(Context context, int position) {
        MemoryBoardManager thisBoard = (MemoryBoardManager) boardManager;
        int row = position / MemoryGameBoard.getNumRows();
        int col = position % MemoryGameBoard.getNumCols();
        MemoryPuzzleTile tile = thisBoard.getBoard().getMemoryGameTile(row, col);

        // If the tap is valid, Memory Puzzle tiles will flip over
        if (thisBoard.isValidTap(position)) {
            PlayMemoryPuzzleController.incrementNumberOfMoves();
            if (thisBoard.numTileFlipped() == 0) {
                firstTap = tile;
                thisBoard.flipTile(position);
            } else {
                //If it is the second tap, they will disappear if the two chosen tiles matched
                secondTap = tile;
                thisBoard.flipTile(position);
                thisBoard.greyOut(firstTap, secondTap);
            }
        } else if (tile.getTopLayer() != R.drawable.memory_tile_38) {
            thisBoard.flipBack(firstTap, secondTap);
        } else {
            makeInvalidTapToast(context);
        }
        if (thisBoard.isOver()) {
            makeYouWinToast(context);

        }
    }

    /**
     * Makes a toast to let the User know they have won.
     *
     * @param context the context of the game
     */
    private void makeYouWinToast(Context context) {
        Toast.makeText(context, "You win!", Toast.LENGTH_SHORT).show();
    }

    /**
     * Makes a toast to let the User know that it is an invalid tap.
     *
     * @param context the context of the game
     */
    private void makeInvalidTapToast(Context context) {
        Toast.makeText(context, "Invalid Tap", Toast.LENGTH_SHORT).show();
    }
}

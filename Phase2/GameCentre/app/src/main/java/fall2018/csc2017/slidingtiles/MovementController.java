package fall2018.csc2017.slidingtiles;

import android.content.Context;
import android.widget.Toast;


class MovementController {

    private Game boardManager = null;
    private boolean firstMove = true;
    private int previousMove;
    private MemoryPuzzleTile firstTap;
    private MemoryPuzzleTile secondTap;

    MovementController() {
    }

    void setBoardManager(Game boardManager) {
        this.boardManager = boardManager;
    }

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

    private void processPegSolitaireTap(Context context, int position) {
        PegSolitaireManager thisBoard = (PegSolitaireManager) boardManager;
        if (thisBoard.isValidTap(position) && firstMove) {
            thisBoard.seePossibleMoves(position);
            firstMove = false;
            previousMove = position;
        } else if (thisBoard.isValidSecondTap(previousMove, position) && !firstMove) {
            PlayPegSolitaireController.incrementNumberOfMoves();
            thisBoard.touchMove(previousMove, position);
            firstMove = true;
            if (thisBoard.isOver()) {
                if (thisBoard.hasWon()) {
                    makeYouWinToast(context);
                }
            }

        } else if (position == previousMove && !firstMove) {
            thisBoard.seePossibleMoves(position);
            firstMove = true;
        } else {
            makeInvalidTapToast(context);
        }
    }

    private void processMemoryPuzzleTap(Context context, int position) {
        MemoryBoardManager thisBoard = (MemoryBoardManager) boardManager;
        int row = position / MemoryGameBoard.NUM_COLS;
        int col = position % MemoryGameBoard.NUM_COLS;
        MemoryPuzzleTile tile = thisBoard.getBoard().getMemoryGameTile(row, col);
        if (thisBoard.isValidTap(position)) {
            PlayMemoryPuzzleController.incrementNumberOfMoves();
            if (thisBoard.numTileFlipped() == 0) {
                firstTap = tile;
                thisBoard.flipTile(position);
            } else {
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


    private void makeYouWinToast(Context context) {
        Toast.makeText(context, "You win!", Toast.LENGTH_SHORT).show();
    }

    private void makeInvalidTapToast(Context context) {
        Toast.makeText(context, "Invalid Tap", Toast.LENGTH_SHORT).show();
    }
}

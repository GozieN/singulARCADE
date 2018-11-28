package fall2018.csc2017.slidingtiles;

import android.content.Context;
import android.widget.Toast;


class MovementController {

    public Game boardManager = null;
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
            PegSolitaireManager thisBoard = (PegSolitaireManager) boardManager;
            if (thisBoard.isValidTap(position) && firstMove) {
                thisBoard.firstMove(position);
                firstMove = false;
                previousMove = position;
            } else if (thisBoard.isValidSecondTap(previousMove, position) && !firstMove) {
                thisBoard.touchMove(previousMove, position);
                firstMove = true;
                if (thisBoard.isOver()) {
                    if (thisBoard.hasWon()) {
                        Toast.makeText(context, "YOU WIN!", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(context, "GAME OVER! TO WIN YOU MUST RID THE BOARD OF ALL PEGS BUT ONE! TRY AGAIN!", Toast.LENGTH_LONG).show();
                    }
                }

            } else {
                Toast.makeText(context, "Invalid Tap", Toast.LENGTH_SHORT).show();
            }
        }

        if (boardManager instanceof SlidingTilesManager) {
            SlidingTilesManager thisBoard = (SlidingTilesManager) boardManager;
            if (thisBoard.isValidTap(position)) {
                thisBoard.touchMove(position);
                if (thisBoard.isOver()) {
                    Toast.makeText(context, "YOU WIN!", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(context, "Invalid Tap", Toast.LENGTH_SHORT).show();
            }
        }

        if (boardManager instanceof MemoryBoardManager) {
            MemoryBoardManager thisBoard = (MemoryBoardManager) boardManager;
            int row = position / MemoryGameBoard.NUM_COLS;
            int col = position % MemoryGameBoard.NUM_COLS;
            if (thisBoard.isValidTap(position)) {
                if (thisBoard.numTileFlipped() == 0) {
                    firstTap = thisBoard.getBoard().getMemoryGameTile(row, col);
                    thisBoard.flipTile(position);
                } else {
                    secondTap = thisBoard.getBoard().getMemoryGameTile(row, col);
                    thisBoard.flipTile(position);
                    thisBoard.greyOut(firstTap, secondTap);
                }
                if (thisBoard.isOver()) {
                    Toast.makeText(context, "YOU WIN!", Toast.LENGTH_SHORT).show();
                }
            } else {
                thisBoard.flipBack(firstTap, secondTap);
                firstTap = thisBoard.getBoard().getMemoryGameTile(row, col);
                thisBoard.flipTile(position);
                //Toast.makeText(context, "Invalid Tap", Toast.LENGTH_SHORT).show();
            }
        }
    }
}

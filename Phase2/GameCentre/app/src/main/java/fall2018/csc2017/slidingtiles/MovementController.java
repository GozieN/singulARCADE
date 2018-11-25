package fall2018.csc2017.slidingtiles;

import android.content.Context;
import android.widget.Toast;


class MovementController {

    private Game boardManager = null;
    private boolean firstMove = true;

    MovementController() {
    }

    void setBoardManager(Game boardManager) {
        this.boardManager = boardManager;
    }

    void processTapMovement(Context context, int position) {
        if (boardManager instanceof PegSolitaireManager) {
            PegSolitaireManager thisBoard = (PegSolitaireManager) boardManager;
            firstMove = !firstMove;
            int position1 = 0;
            if (thisBoard.isValidTap(position) && firstMove) {
                thisBoard.firstMove(position);
                firstMove = false;
                position1 = position;
            } else if (thisBoard.isValidTap(position) && !firstMove) {
                thisBoard.touchMove(position1, position);
                if (thisBoard.isOver()) {
                    Toast.makeText(context, "YOU WIN!", Toast.LENGTH_SHORT).show();
                }
                firstMove = true;
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
            //TODO: fill in what happens in Memory Puzzle
        }
    }
}

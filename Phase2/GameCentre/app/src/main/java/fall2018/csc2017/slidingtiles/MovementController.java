package fall2018.csc2017.slidingtiles;

import android.content.Context;
import android.widget.Toast;


class MovementController {

    public Game boardManager = null;
    private boolean firstMove = true;
    private int previousMove;

    MovementController() {
    }

    void setBoardManager(Game boardManager) {
        System.out.println("MCONTROLLER SET BOARDMANAGER:");
        System.out.println(boardManager);
        this.boardManager = boardManager;
    }

    void processTapMovement(Context context, int position) {
        System.out.println(boardManager.getClass());
        if (boardManager instanceof PegSolitaireManager) {
            PegSolitaireManager thisBoard = (PegSolitaireManager) boardManager;
            if (thisBoard.isValidTap(position) && firstMove) {
                thisBoard.firstMove(position);
                firstMove = false;
                previousMove = position;
            } else if (thisBoard.isValidTap(position) && !firstMove) {
                thisBoard.touchMove(previousMove, position);
                firstMove = true;
                if (thisBoard.isOver()) {
                    Toast.makeText(context, "YOU WIN!", Toast.LENGTH_SHORT).show();
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
            //TODO: fill in what happens in Memory Puzzle
        }
    }
}

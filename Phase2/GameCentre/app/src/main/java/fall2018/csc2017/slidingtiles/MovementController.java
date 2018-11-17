package fall2018.csc2017.slidingtiles;

import android.content.Context;
import android.widget.Toast;


class MovementController {

    private BoardManager boardManager = null;

    MovementController() {
    }

    void setBoardManager(BoardManager boardManager) {
        this.boardManager = boardManager;

    }

    void processTapMovement(Context context, int position, boolean display) {
        if (boardManager.isValidTap(position)) {
            boardManager.touchMove(position);
            if (boardManager.isOver()) {
                Toast.makeText(context, "YOU WIN!", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(context, "Invalid Tap", Toast.LENGTH_SHORT).show();
        }
    }
}

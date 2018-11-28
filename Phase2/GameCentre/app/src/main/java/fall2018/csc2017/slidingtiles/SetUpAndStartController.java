package fall2018.csc2017.slidingtiles;

import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.content.Context;

public class SetUpAndStartController {

    Spinner spinnerUndo;
    Spinner spinnerBoardShape;

    /*
    The user's chosen board shape/dimensions from dropdown.
    **/
    private String boardSelection;

    /*
    The user's chosen board size.
    */
    private int shape;

    /*
    The user's chosen undo limit from dropdown.
    */
    private String undoSelection;

    SetUpAndStartController() {}

    public Object setGameManager(String game) {
        if (game.equals(SlidingTilesManager.GAME_NAME)) {
            return GameLauncher.getCurrentUser().getRecentManagerOfBoard(SlidingTilesManager.GAME_NAME);
//            gameManager = (SlidingTilesManager) GameLauncher.getCurrentUser().getRecentManagerOfBoard(SlidingTilesManager.GAME_NAME);
        }
        else {
            return GameLauncher.getCurrentUser().getRecentManagerOfBoard(PegSolitaireManager.GAME_NAME);
//            gameManager = (PegSolitaireManager) GameLauncher.getCurrentUser().getRecentManagerOfBoard(PegSolitaireManager.GAME_NAME);
        }
    }

    /**
     * Return a solvable SlidingTilesManager
     * @param shape the size of the board
     * @return a solvable SlidingTilesManager
     */
    public SlidingTilesManager setSolvableBoardManager(int shape) {
        SlidingTilesBoard.setDimensions(shape);
        SlidingTilesManager gameManager = new SlidingTilesManager();
        while (! gameManager.isSolvable()) {
            gameManager = new SlidingTilesManager();
        }
        return gameManager;
    }

    public int setBoardShape(String game, Spinner spinnerBoardShape) {
        if(spinnerBoardShape != null && spinnerBoardShape.getSelectedItem() !=null ) {
            if (game.equals(SlidingTilesManager.GAME_NAME)) {
                if(spinnerBoardShape != null && spinnerBoardShape.getSelectedItem() !=null ) {
                    String boardSelection = (String) spinnerBoardShape.getSelectedItem();
                    return Character.getNumericValue(boardSelection.charAt(0));
                }
            }
            else {
                if (spinnerBoardShape.getSelectedItem().equals("Square")) {
                    return 6;
                } if (spinnerBoardShape.getSelectedItem().equals("Cross")) {
                    return 7;
                } if (spinnerBoardShape.getSelectedItem().equals("Diamond")) {
                    return 9;
                }
            }
        }
        return 0;
    }

    public Object newGameManager(String game) {
        if (game.equals(SlidingTilesManager.GAME_NAME)) {
            return new SlidingTilesManager();
        }
        if (game.equals(PegSolitaireManager.GAME_NAME)) {
            return new PegSolitaireManager();
        }
        //TODO: this may have to change if memoryBoardManager is now included
        return null;
    }
}

package fall2018.csc2017.slidingtiles.Controllers;

import android.widget.Spinner;

import fall2018.csc2017.slidingtiles.GameLauncher;
import fall2018.csc2017.slidingtiles.Games.MemoryPuzzle.MemoryBoardManager;
import fall2018.csc2017.slidingtiles.Games.PegSolitaire.PegSolitaireManager;
import fall2018.csc2017.slidingtiles.Games.SlidingTiles.SlidingTilesBoard;
import fall2018.csc2017.slidingtiles.Games.SlidingTiles.SlidingTilesManager;

/**
 * The Setup Movement Controller
 */
public class SetUpAndStartController {

    /**
     * Set the Game manager
     * @param game the game identifier
     * @return the recent manager of the board
     */
    public Object setGameManager(String game) {
        if (game.equals(SlidingTilesManager.getGameName())) {
            return GameLauncher.getCurrentUser().getRecentManagerOfBoard(SlidingTilesManager.getGameName());
        } else {
            return GameLauncher.getCurrentUser().getRecentManagerOfBoard(PegSolitaireManager.getGameName());
        }
    }

    /**
     * Return a solvable SlidingTilesManager
     *
     * @param shape the size of the board
     * @return a solvable SlidingTilesManager
     */
    public SlidingTilesManager setSolvableBoardManager(int shape) {
        SlidingTilesBoard.setDimensions(shape);
        SlidingTilesManager gameManager = new SlidingTilesManager();
        while (!gameManager.isSolvable()) {
            gameManager = new SlidingTilesManager();
        }
        return gameManager;
    }

    /**
     * Set the board shape
     * @param game the game being played
     * @param spinnerBoardShape the spinner that sets the board shape
     * @return the shape of the board
     */
    public int setBoardShape(String game, Spinner spinnerBoardShape) {
        if (spinnerBoardShape != null && spinnerBoardShape.getSelectedItem() != null) {
            if (game.equals(SlidingTilesManager.getGameName()) || game.equals(MemoryBoardManager.getGameName())) {
                if (spinnerBoardShape != null && spinnerBoardShape.getSelectedItem() != null) {
                    String boardSelection = (String) spinnerBoardShape.getSelectedItem();
                    return Character.getNumericValue(boardSelection.charAt(0));
                }
            } else {
                if (spinnerBoardShape.getSelectedItem().equals("Square")) {
                    return 6;
                }
                if (spinnerBoardShape.getSelectedItem().equals("Cross")) {
                    return 7;
                }
                if (spinnerBoardShape.getSelectedItem().equals("Diamond")) {
                    return 9;
                }
            }
        }
        return 0;
    }

    /**
     * Return the new userManager depending on the game being played
     *
     * @param game the name of the game being played
     * @return the new userManager depending on the game being played
     */
    public Object newGameManager(String game) {
        if (game.equals(SlidingTilesManager.getGameName())) {
            return new SlidingTilesManager();
        } else if (game.equals(PegSolitaireManager.getGameName())) {
            return new PegSolitaireManager();
        } else { //game.equals(MemoryBoardManager.GAME_NAME)
            return new MemoryBoardManager();
        }
    }
}

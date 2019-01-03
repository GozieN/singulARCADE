package fall2018.csc2017.slidingtiles;

import android.widget.Button;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import fall2018.csc2017.slidingtiles.Controllers.PlayPegSolitaireController;
import fall2018.csc2017.slidingtiles.Games.PegSolitaire.PegSolitaireBoard;
import fall2018.csc2017.slidingtiles.Games.PegSolitaire.PegSolitaireManager;
import fall2018.csc2017.slidingtiles.Games.PegSolitaire.PegSolitaireTile;
import fall2018.csc2017.slidingtiles.Views.SetUpActivity;

import static org.junit.Assert.assertEquals;

public class PlayPegSolitaireControllerTest {

    private PegSolitaireManager pegSolitaireManager;
    User user;
    ArrayList<Button> tileButtons;

    private PlayPegSolitaireController playPegSolitaireController = new PlayPegSolitaireController();

    /**
     * Make a set of tiles.
     * @return a set of tiles
     */
    private List<PegSolitaireTile> makeTiles() {
        List<PegSolitaireTile> tiles = new ArrayList<>();
        final int numTiles = PegSolitaireBoard.getNumRows() * PegSolitaireBoard.getNumCols();
        for (int tileNum = 0; tileNum != numTiles; tileNum++) {
            tiles.add(new PegSolitaireTile(2));
        }
        return tiles;
    }

    /**
     * Make a solved diamond peg solitaire board.
     */
    private void setUpDiamondWin() {
        PegSolitaireBoard.setDimensions(9);
        PegSolitaireBoard board = new PegSolitaireBoard(makeTiles());
        for (PegSolitaireTile tileRow[]: board.getPegTiles()) {
            for (PegSolitaireTile tile:tileRow) {
                if (tile.getId() != 0 && tile.getId() != 1) {
                    tile.setId(1, false);
                }
            }
        }
        board.getPegTile(4, 2).setId(2, false);
        pegSolitaireManager = new PegSolitaireManager(board);
    }


    private void setUpCorrect() {
        user = new User("Test", "Test");

        PegSolitaireBoard.setDimensions(4);
        List<PegSolitaireTile> tiles = makeTiles();
        PegSolitaireBoard newBoard = new PegSolitaireBoard(tiles);
        pegSolitaireManager = new PegSolitaireManager();
        pegSolitaireManager.setBoard(newBoard);
        SetUpActivity.setUndoLimit(3);
        user.setRecentManagerOfBoard(PegSolitaireManager.getGameName(), pegSolitaireManager);

        GameLauncher.setCurrentUser(user);


    }

    @Test
    public void TestCreateAndUpdateTileButtons() {
        setUpCorrect();
    }

    @Test
    public void TestUsedNumberOfUndos() {
        setUpCorrect();

        //no moves have been completed so no undos can be performed
        assertEquals("NoUndoText", playPegSolitaireController.usedNumberOfUndos());

        //max amount of undos have been performed
        GameLauncher.getCurrentUser().setNumOfUndos(PegSolitaireManager.getGameName(), 5);
        assertEquals("UndoLimitText", playPegSolitaireController.usedNumberOfUndos());

        //has done a move and not reached limit of undos so can perform undo
//        slidingTilesManager.touchMove(14);
//        System.out.println(user.getStackOfGameStates(SlidingTilesManager.GAME_NAME));
//        assertEquals("NoUndoText", playSlidingTilesController.usedNumberOfUndos());
    }

    @Test
    public void TestEndOfGame() {
        setUpCorrect();
        ArrayList arrayList = new ArrayList();
        arrayList.add(true);
        arrayList.add(true);
        assertEquals(arrayList, playPegSolitaireController.endOfGame(pegSolitaireManager));
    }

    @Test
    public void TestIncrementAndGetNumberOfMoves() {
        setUpCorrect();
        PlayPegSolitaireController.incrementNumberOfMoves();
        assertEquals(1, playPegSolitaireController.getNumberOfMoves());
    }

    @Test
    public void TestGetNumberOfUndos() {
        setUpCorrect();
        assertEquals(5, playPegSolitaireController.getNumberOfUndos());
    }
}

package fall2018.csc2017.slidingtiles;

import org.junit.Test;

import android.content.Context;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.Assert.*;

public class SetUpAndStartControllerTest {

    SetUpAndStartController setUpAndStartController = new SetUpAndStartController();

    User user;

    private void setUpCorrect() {
        user = new User("Test", "Test");
        GameLauncher.setCurrentUser(user);
        user.setRecentManagerOfBoard(SlidingTilesManager.GAME_NAME, new SlidingTilesManager());
        user.setRecentManagerOfBoard(PegSolitaireManager.GAME_NAME, new PegSolitaireManager());
        user.setRecentManagerOfBoard(MemoryBoardManager.GAME_NAME, new MemoryBoardManager());

    }

    @Test
    public void testSetGameManager() {
        setUpCorrect();
        //if it is SlidingTilesManager
        assertEquals(user.getRecentManagerOfBoard(SlidingTilesManager.GAME_NAME),
                setUpAndStartController.setGameManager(SlidingTilesManager.GAME_NAME));

        //if it is PegSolitaireManager
        assertEquals(user.getRecentManagerOfBoard(PegSolitaireManager.GAME_NAME),
                setUpAndStartController.setGameManager(PegSolitaireManager.GAME_NAME));
    }

    @Test
    public void testSetSolvableBoardManager() {
        setUpCorrect();
        //even sized board
        SlidingTilesManager evenSolvableManager = setUpAndStartController.setSolvableBoardManager(4);
        assertTrue(evenSolvableManager.isSolvable());

        //odd sized board
        SlidingTilesManager oddSolvableManager = setUpAndStartController.setSolvableBoardManager(5);
        assertTrue(oddSolvableManager.isSolvable());
    }

    @Test
    public void testNewGameManager() {
        setUpCorrect();
        //SlidingTilesManager
        SlidingTilesManager newSlidingTilesManager = (SlidingTilesManager)
                setUpAndStartController.newGameManager(SlidingTilesManager.GAME_NAME);
        assertEquals(0, newSlidingTilesManager.getBoard().getDimensions());

        //PegSolitaireManager
        PegSolitaireManager originalPegSolitaireManager = (PegSolitaireManager)
                GameLauncher.getCurrentUser().getRecentManagerOfBoard(PegSolitaireManager.GAME_NAME);
        assertEquals(81, originalPegSolitaireManager.getBoard().getDimensions());
        PegSolitaireBoard.setDimensions(6);
        //if new board is created, it should now have the new dimensions
        PegSolitaireManager newPegSolitaireManager = (PegSolitaireManager)
                setUpAndStartController.newGameManager(PegSolitaireManager.GAME_NAME);
        assertEquals(36, newPegSolitaireManager.getBoard().getDimensions());
    }

    @Test
    public void testSetBoardShape() {

    }
}

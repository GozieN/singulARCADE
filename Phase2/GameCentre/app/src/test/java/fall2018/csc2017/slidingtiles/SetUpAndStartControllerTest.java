package fall2018.csc2017.slidingtiles;

import org.junit.Test;

import fall2018.csc2017.slidingtiles.Controllers.SetUpAndStartController;
import fall2018.csc2017.slidingtiles.Games.MemoryPuzzle.MemoryBoardManager;
import fall2018.csc2017.slidingtiles.Games.PegSolitaire.PegSolitaireManager;
import fall2018.csc2017.slidingtiles.Games.SlidingTiles.SlidingTilesManager;

import static org.junit.Assert.*;

public class SetUpAndStartControllerTest {

    private SetUpAndStartController setUpAndStartController = new SetUpAndStartController();

    User user;

    private void setUpCorrect() {
        user = new User("Test", "Test");
        GameLauncher.setCurrentUser(user);
        user.setRecentManagerOfBoard(SlidingTilesManager.getGameName(), new SlidingTilesManager());
        user.setRecentManagerOfBoard(PegSolitaireManager.getGameName(), new PegSolitaireManager());
        user.setRecentManagerOfBoard(MemoryBoardManager.getGameName(), new MemoryBoardManager());
    }

    @Test
    public void testSetGameManager() {
        setUpCorrect();
        //if it is SlidingTilesManager
        assertEquals(user.getRecentManagerOfBoard(SlidingTilesManager.getGameName()),
                setUpAndStartController.setGameManager(SlidingTilesManager.getGameName()));

        //if it is PegSolitaireManager
        assertEquals(user.getRecentManagerOfBoard(PegSolitaireManager.getGameName()),
                setUpAndStartController.setGameManager(PegSolitaireManager.getGameName()));
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
                setUpAndStartController.newGameManager(SlidingTilesManager.getGameName());
        assertEquals(4, newSlidingTilesManager.getBoard().getDimensions());

        //PegSolitaireManager
        PegSolitaireManager newPegSolitaireManager = (PegSolitaireManager)
                setUpAndStartController.newGameManager(PegSolitaireManager.getGameName());
        assertEquals(16, newPegSolitaireManager.getBoard().getDimensions());

        //MemoryBoardManager
        MemoryBoardManager newMemoryBoardManager = (MemoryBoardManager)
                setUpAndStartController.newGameManager(MemoryBoardManager.getGameName());
        System.out.println(newMemoryBoardManager.getBoard().numTiles());
        assertEquals(4, newMemoryBoardManager.getBoard().getDimensions());
    }

    @Test
    public void testSetBoardShape() {

    }
}

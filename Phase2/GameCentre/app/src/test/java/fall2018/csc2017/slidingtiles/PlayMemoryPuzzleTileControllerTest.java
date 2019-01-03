package fall2018.csc2017.slidingtiles;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import fall2018.csc2017.slidingtiles.Controllers.PlayMemoryPuzzleController;
import fall2018.csc2017.slidingtiles.Games.MemoryPuzzle.MemoryBoardManager;
import fall2018.csc2017.slidingtiles.Games.MemoryPuzzle.MemoryGameBoard;
import fall2018.csc2017.slidingtiles.Games.MemoryPuzzle.MemoryPuzzleTile;
import fall2018.csc2017.slidingtiles.Views.SetUpActivity;

import static org.junit.Assert.*;

public class PlayMemoryPuzzleTileControllerTest {

    /**
     * The memory board manager used in the tests.
     */
    MemoryBoardManager memoryBoardManager;

    /**
     * The user used in the tests.
     */
    User user;

    /**
     * The playMemoryPuzzleController used in the tests.
     */
    PlayMemoryPuzzleController playMemoryPuzzleController = new PlayMemoryPuzzleController();

    /**
     * Make a set of tiles.
     * @return a set of tiles
     */
    private List<MemoryPuzzleTile> makeTiles() {
        List<MemoryPuzzleTile> tiles = new ArrayList<>();
        final int numTiles = MemoryGameBoard.getNumRows() * MemoryGameBoard.getNumCols();
        for (int tileNum = 0; tileNum != numTiles; tileNum++) {
            tiles.add(new MemoryPuzzleTile(2));
        }
        return tiles;
    }


    /**
     * Make a solved board and have it set to a boardManager inside of the current user.
     */
    private void setUpCorrect() {
        user = new User("Test", "Test");

        MemoryGameBoard.setDimensions(4);
        List<MemoryPuzzleTile> tiles = makeTiles();
        MemoryGameBoard newBoard = new MemoryGameBoard(tiles);
        memoryBoardManager = new MemoryBoardManager();
        memoryBoardManager.setBoard(newBoard);
        SetUpActivity.setUndoLimit(3);
        user.setRecentManagerOfBoard(MemoryBoardManager.getGameName(), memoryBoardManager);

        GameLauncher.setCurrentUser(user);


    }

    /**
     * Test endOfGame works.
     */
    @Test
    public void TestEndOfGame() {
        setUpCorrect();
        ArrayList arrayList = new ArrayList();
        arrayList.add(true);
        arrayList.add(true);
        assertEquals(arrayList, playMemoryPuzzleController.endOfGame(memoryBoardManager));
    }
}

package fall2018.csc2017.slidingtiles;
import android.widget.Button;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class PlayMemoryPuzzleTileControllerTest {

    MemoryBoardManager memoryBoardManager;
    User user;
    ArrayList<Button> tileButtons;

    PlayMemoryPuzzleController playMemoryPuzzleController = new PlayMemoryPuzzleController();

    /**
     * Make a set of tiles.
     * @return a set of tiles
     */
    private List<MemoryPuzzleTile> makeTiles() {
        List<MemoryPuzzleTile> tiles = new ArrayList<>();
        final int numTiles = MemoryGameBoard.NUM_ROWS * MemoryGameBoard.NUM_COLS;
        for (int tileNum = 0; tileNum != numTiles; tileNum++) {
            tiles.add(new MemoryPuzzleTile(2));
        }
        return tiles;
    }


    private void setUpCorrect() {
        user = new User("Test", "Test");

        MemoryGameBoard.setDimensions(4);
        List<MemoryPuzzleTile> tiles = makeTiles();
        MemoryGameBoard newBoard = new MemoryGameBoard(tiles);
        memoryBoardManager = new MemoryBoardManager();
        memoryBoardManager.setBoard(newBoard);
        SetUpActivity.undoLimit = 3;
        user.setRecentManagerOfBoard(MemoryBoardManager.GAME_NAME, memoryBoardManager);

        GameLauncher.setCurrentUser(user);


    }

    @Test
    public void TestCreateAndUpdateTileButtons() {
        setUpCorrect();
    }

    @Test
    public void TestEndOfGame() {
        setUpCorrect();
        ArrayList arrayList = new ArrayList();
        arrayList.add(true);
        arrayList.add(true);
        assertEquals(arrayList, playMemoryPuzzleController.endOfGame(memoryBoardManager));
    }
}

package fall2018.csc2017.slidingtiles;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * The options to decide between when setting up the board for the game sliding tiles.
 */
public class SlidingTilesSetUpActivity extends AppCompatActivity {
    /*
    The user's chosen board size from dropdown.
     */
    private String boardSelection;
    /*
    The user's chosen undo limit from dropdown.
     */
    private String undoSelection;
    /*
    The dropdown menu for board size.
     */
    private Spinner spinnerBoardSize;
    /*
    The dropdown menu for undo.
     */
    private Spinner spinnerUndo;
    /*
    The user's chosen board size.
     */
    private int size;
    /*
    The user's chosen undo limit.
     */
    static int undoLimit;

    private UserManager userManager;
    private ScoreBoard scoreBoard;
    private SlidingTilesManager slidingTilesManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sliding_tiles_set_up);
        addPlayButtonListener();
        loadFromFile(LoginActivity.SAVE_FILENAME);

        //adapted from https://developer.android.com/guide/topics/ui/controls/spinner#java
        spinnerBoardSize = findViewById(R.id.ChooseBoardSpinner);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapterBoardSize = ArrayAdapter.createFromResource(this,
                R.array.slidingTilesboard_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapterBoardSize.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinnerBoardSize.setAdapter(adapterBoardSize);

        spinnerUndo = findViewById(R.id.ChooseUndoSpinner);
        ArrayAdapter<CharSequence> adapterUndo = ArrayAdapter.createFromResource(this,
                R.array.undo_array, android.R.layout.simple_spinner_item);
        adapterUndo.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerUndo.setAdapter(adapterUndo);
    }

    /**
     * Activate the play button.
     */
    private void addPlayButtonListener() {
        Button playButton = findViewById(R.id.PlayButton);
        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // adapted from https://stackoverflow.com/questions/29891237/checking-if-spinner-is-selected-and-having-null-value-in-android
                if(spinnerBoardSize != null && spinnerBoardSize.getSelectedItem() !=null ) {
                    boardSelection = (String) spinnerBoardSize.getSelectedItem();
                }
                if(spinnerUndo != null && spinnerUndo.getSelectedItem() !=null ) {
                    undoSelection = (String) spinnerUndo.getSelectedItem();
                }

                size = Character.getNumericValue(boardSelection.charAt(0));
                undoLimit = Integer.valueOf(undoSelection);
                switchToGame();
            }
        });
    }

    /**
     * Switch to game screen.
     */
    private void switchToGame() {
        Intent tmp = new Intent(this, PlaySlidingTilesActivity.class);
        tmp.putExtra("size", size);
        slidingTilesManager = new SlidingTilesManager(makeBoard());
        while (! isSolvable(slidingTilesManager)) {
            slidingTilesManager = new SlidingTilesManager(makeBoard());
        }
        GameLauncher.getCurrentUser().setRecentManagerOfBoard(SlidingTilesManager.GAME_NAME, slidingTilesManager);
        saveToFile(LoginActivity.SAVE_FILENAME);
        startActivity(tmp);
    }

    /**
     * Return a Board.
     * @return a Board
     */
    private SlidingTilesBoard makeBoard () {
        SlidingTilesBoard board;

        SlidingTilesBoard.setDimensions(size);

        List<Tile> tiles = new ArrayList<>();
        final int numTiles = SlidingTilesBoard.NUM_ROWS * SlidingTilesBoard.NUM_COLS;
        for (int tileNum = 0; tileNum != numTiles; tileNum++) {
            if (tileNum == numTiles - 1) {
                tiles.add(new Tile(tileNum, tileNum));
            } else {
                tiles.add(new Tile(tileNum));
            }
        }
        Collections.shuffle(tiles);
        board = new SlidingTilesBoard(tiles);
        return board;
    }

    /**
     *
     * @param slidingTilesManager the slidingTilesManager that is being used in the game about to be played.
     * @return true iff the sliding tiles game will be solvable.
     */
    private boolean isSolvable(SlidingTilesManager slidingTilesManager) {
        //adapted from https://puzzling.stackexchange.com/questions/25563/do-i-have-an-unsolvable-15-puzzle
        ArrayList tileOrder = slidingTilesManager.getTilesInArrayList();
        int blankId = slidingTilesManager.positionBlankTile();
        //check the amount of inversions
        //adapted from https://math.stackexchange.com/questions/293527/how-to-check-if-a-8-puzzle-is-solvable
        int inversions = 0;
        for (int i=0; i<tileOrder.size(); i++) {
            for (int j=i + 1; j<tileOrder.size(); j++) {
                if ((int) tileOrder.get(j) < (int) tileOrder.get(i)) {
                    inversions++;
                }
            }
        }
        //if it's odd size and has an even number of inversions, the board is solvable-> return true
        if (size%2 != 0) {
            if (inversions%2 == 0){
                return true;
            }
            return false;
        }
        //otherwise it is even size and the rows the blank tile is on is odd, then the board is solvable
        if (blankId % 2 == 0 && inversions % 2 == 0) {return true;}
        if (blankId % 2 != 0 && inversions %2 != 0) {return true;}
        return false;
    }

    /**
     * Load the user manager and scoreboard from fileName.
     *
     * @param fileName the name of the file
     */
    public void loadFromFile(String fileName) {

        try {
            InputStream inputStream = this.openFileInput(fileName);
            if (inputStream == null) {
                saveToFile(fileName);
            }
            else {
                ObjectInputStream input = new ObjectInputStream(inputStream);
                userManager = (UserManager) input.readObject();
                slidingTilesManager = (SlidingTilesManager) GameLauncher.getCurrentUser().getRecentManagerOfBoard(SlidingTilesManager.GAME_NAME);
                inputStream.close();
            }
        } catch (FileNotFoundException e) {
            Log.e("login activity", "File not found: " + fileName);
        } catch (IOException e) {
            Log.e("login activity", "Can not read file: " + e.toString());
        } catch (ClassNotFoundException e) {
            Log.e("login activity", "File contained unexpected data type: " + e.toString());
        }
    }

    /**
     * Save the user manager and scoreboard to fileName.
     *
     * @param fileName the name of the file
     */
    public void saveToFile(String fileName) {
        try {
            ObjectOutputStream outputStream = new ObjectOutputStream(
                    this.openFileOutput(fileName, MODE_PRIVATE));
            outputStream.writeObject(userManager);
            outputStream.close();
        } catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }
}
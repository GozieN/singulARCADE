package fall2018.csc2017.slidingtiles;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.support.v7.app.AppCompatActivity;


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
public class MemoryGameSetUpActivity extends AppCompatActivity {
    /*
    The user's chosen board size from dropdown.
     */
    private String boardSelection;
    /*
    The dropdown menu for board size.
     */
    private Spinner spinnerBoardSize;

    /*
    The user's chosen board size.
     */
    private int size;

    private UserManager userManager;
    private ScoreBoard scoreBoard;
    private MemoryBoardManager boardManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memory_game_set_up);
        addPlayButtonListener();
        loadFromFile(LoginActivity.SAVE_FILENAME);

        //adapted from https://developer.android.com/guide/topics/ui/controls/spinner#java
        spinnerBoardSize = findViewById(R.id.ChooseBoardSpinner);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapterBoardSize = ArrayAdapter.createFromResource(this,
                R.array.board_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapterBoardSize.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinnerBoardSize.setAdapter(adapterBoardSize);
        
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

                size = Character.getNumericValue(boardSelection.charAt(0));
                switchToGame();
            }
        });
    }

    /**
     * Switch to game screen.
     */
    private void switchToGame() {
        Intent tmp = new Intent(this, GameActivity.class);
        tmp.putExtra("size", size);
        System.out.println("size of board is " + size);
        System.out.println("size of the board after calling makeBoard(): " + new BoardManager(makeBoard()).getBoard().NUM_COLS);
        boardManager = new MemoryBoardManager(makeBoard());
        System.out.println("get the boardmaranger's set num of columns: " + boardManager.getBoard().NUM_COLS);
        GameLauncher.getCurrentUser().setRecentManagerOfBoard(BoardManager.GAME_NAME, boardManager);
        saveToFile(LoginActivity.SAVE_FILENAME);
        startActivity(tmp);
    }

    /**
     * Return a Board.
     * @return a Board
     */
    private Board makeBoard () {
        Board board;

        //int size = getIntent().getIntExtra("size", 4);
        Board.setDimensions(size);

        List<Tile> tiles = new ArrayList<>();
        final int numTiles = Board.NUM_ROWS * Board.NUM_COLS;
        for (int tileNum = 0; tileNum != numTiles; tileNum++) {
            if (tileNum == numTiles - 1) {
                tiles.add(new Tile(tileNum, tileNum));
            } else {
                tiles.add(new Tile(tileNum));
            }
        }

        Collections.shuffle(tiles);
        board = new Board(tiles);
        return board;
    }

    /**
     * Load the user manager and scoreboard from fileName.
     *
     * @param fileName the name of the file
     */
    private void loadFromFile(String fileName) {

        try {
            InputStream inputStream = this.openFileInput(fileName);
            if (inputStream == null) {
                saveToFile(fileName);
            }
            else {
                ObjectInputStream input = new ObjectInputStream(inputStream);
                ArrayList arrayList = (ArrayList) input.readObject();
                userManager = (UserManager) arrayList.get(0);
                scoreBoard = (ScoreBoard) arrayList.get(1);
                boardManager = (MemoryBoardManager) GameLauncher.getCurrentUser().getRecentManagerOfBoard(BoardManager.GAME_NAME);
//                userManager = (UserManager) input.readObject();
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

        ArrayList arrayList= new ArrayList();
        arrayList.add(userManager);
        arrayList.add(scoreBoard);
        try {
            ObjectOutputStream outputStream = new ObjectOutputStream(
                    this.openFileOutput(fileName, MODE_PRIVATE));
            outputStream.writeObject(arrayList);
            outputStream.close();
        } catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }
}
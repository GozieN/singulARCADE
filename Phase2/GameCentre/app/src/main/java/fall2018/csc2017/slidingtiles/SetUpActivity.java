package fall2018.csc2017.slidingtiles;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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

public class SetUpActivity extends AppCompatActivity {
    /*
    The user's chosen board shape/dimensions from dropdown.
     **/
    private String boardSelection;
    /*
    The user's chosen undo limit from dropdown.
     */
    private String undoSelection;
    /*
    The dropdown menu for board shape.
     */
    private Spinner spinnerBoardShape;
    /*
    The dropdown menu for undo.
     */
    private Spinner spinnerUndo;
    /*
    The user's chosen board size.
     */
    private int shape;
    /*
    The user's chosen undo limit.
     */
    static int undoLimit;

    private UserManager userManager;
    private Game gameManager;
    private String game;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        game = getIntent().getStringExtra("game");

        //adapted from https://developer.android.com/guide/topics/ui/controls/spinner#java

        if (game.equals("SLIDING TILES")) {
            setContentView(R.layout.activity_sliding_tiles_set_up);

            spinnerBoardShape = findViewById(R.id.ChooseSlidingTilesSpinner);
            ArrayAdapter<CharSequence> adapterBoardSize = ArrayAdapter.createFromResource(this,
                    R.array.slidingTilesboard_array, android.R.layout.simple_spinner_item);

            // Specify the layout to use when the list of choices appears
            adapterBoardSize.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

            // Apply the adapter to the spinner
            spinnerBoardShape.setAdapter(adapterBoardSize);
        } else if (game.equals("PEG SOLITAIRE")) {
            setContentView(R.layout.activity_peg_solitaire_set_up);

            spinnerBoardShape = findViewById(R.id.ChoosePegSolitaireSpinner);
            ArrayAdapter<CharSequence> adapterBoardSize = ArrayAdapter.createFromResource(this,
                    R.array.pegSolitaireBoard_array, android.R.layout.simple_spinner_item);

            // Specify the layout to use when the list of choices appears
            adapterBoardSize.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

            // Apply the adapter to the spinner
            spinnerBoardShape.setAdapter(adapterBoardSize);
        } else { //game.equals("MEMORY PUZZLE")
            setContentView(R.layout.activity_memory_game_set_up);

        }
        addPlayButtonListener();
        loadFromFile(LoginActivity.SAVE_FILENAME);

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
                if(spinnerBoardShape != null && spinnerBoardShape.getSelectedItem() !=null ) {
                    if (game.equals("SLIDING TILES")) {
                        if(spinnerBoardShape != null && spinnerBoardShape.getSelectedItem() !=null ) {
                            boardSelection = (String) spinnerBoardShape.getSelectedItem();
                            shape = Character.getNumericValue(boardSelection.charAt(0));
                        }
                    } else {
                        if (spinnerBoardShape.getSelectedItem().equals("Square")) {
                            shape = 6;
                        } if (spinnerBoardShape.getSelectedItem().equals("Cross")) {
                            shape = 7;
                        } if (spinnerBoardShape.getSelectedItem().equals("Diamond")) {
                            shape = 9;
                        }
                     }
                }
                if(spinnerUndo != null && spinnerUndo.getSelectedItem() !=null ) {
                    undoSelection = (String) spinnerUndo.getSelectedItem();
                }

                undoLimit = Integer.valueOf(undoSelection);
                switchToGame();
            }
        });
    }


    /**
     * Switch to game screen.
     */
    private void switchToGame() {
        Intent tmp;
        if (game.equals("SLIDING TILES")) {
            tmp = new Intent(this, PlaySlidingTilesActivity.class);
            SlidingTilesBoard.setDimensions(shape);
            gameManager = new SlidingTilesManager();
            while (! ((SlidingTilesManager)gameManager).isSolvable()) {
                gameManager = new SlidingTilesManager();
            }
            GameLauncher.getCurrentUser().setRecentManagerOfBoard(SlidingTilesManager.GAME_NAME, gameManager);
        } else { //game.equals("PEG SOLITAIRE")
            tmp = new Intent(this, PlayPegSolitaireActivity.class);
            GameLauncher.getCurrentUser().setRecentManagerOfBoard(PegSolitaireManager.GAME_NAME, gameManager);
        }
        tmp.putExtra("shape", shape);
        tmp.putExtra("game", PegSolitaireManager.GAME_NAME);
        saveToFile(LoginActivity.SAVE_FILENAME);
        startActivity(tmp);
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
                if (game.equals("SLIDING TILES")) {
                    gameManager = (SlidingTilesManager) GameLauncher.getCurrentUser().getRecentManagerOfBoard(SlidingTilesManager.GAME_NAME);
                } else {
                    gameManager = (PegSolitaireManager) GameLauncher.getCurrentUser().getRecentManagerOfBoard(PegSolitaireManager.GAME_NAME);
                }
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

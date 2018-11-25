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

public class PegSolitaireSetUpActivity extends AppCompatActivity {
    /*
    The user's chosen board shape from dropdown.
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
    private ScoreBoard scoreBoard;
    private PegSolitaireManager pegSolitaireManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sliding_tiles_set_up);
        addPlayButtonListener();
        loadFromFile(LoginActivity.SAVE_FILENAME);

        //adapted from https://developer.android.com/guide/topics/ui/controls/spinner#java
        spinnerBoardShape = findViewById(R.id.ChooseBoardSpinner);

        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapterBoardSize = ArrayAdapter.createFromResource(this,
                R.array.pegSolitaireBoard_array, android.R.layout.simple_spinner_item);

        // Specify the layout to use when the list of choices appears
        adapterBoardSize.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Apply the adapter to the spinner
        spinnerBoardShape.setAdapter(adapterBoardSize);

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
                    if (spinnerBoardShape.getSelectedItem().equals("Square")) {
                        shape = 6;
                        System.out.println("SIX");
                    } if (spinnerBoardShape.getSelectedItem().equals("Cross")) {
                        shape = 7;
                        System.out.println("SEVEN");
                    } if (spinnerBoardShape.getSelectedItem().equals("Diamond")) {
                        shape = 9;
                        System.out.println("NINE");
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
        Intent tmp = new Intent(this, PlayPegSolitaireActivity.class);
        tmp.putExtra("shape", shape);
        tmp.putExtra("game", PegSolitaireManager.GAME_NAME);
        GameLauncher.getCurrentUser().setRecentManagerOfBoard(PegSolitaireManager.GAME_NAME, pegSolitaireManager);
        saveToFile(LoginActivity.SAVE_FILENAME);
        startActivity(tmp);
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
                pegSolitaireManager = (PegSolitaireManager) GameLauncher.getCurrentUser().getRecentManagerOfBoard(SlidingTilesManager.GAME_NAME);
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

package fall2018.csc2017.slidingtiles;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.Stack;


public class PlayPegSolitaireActivity extends AppCompatActivity implements Observer {

    /**
     *
     */
    private PegSolitaireManager pegSolitaireManager;

    /**
     *
     */
    private ArrayList<Button> tileButtons;

    // Grid View and calculated column height and width based on device size
    private GestureDetectGridView gridView;
    private static int columnWidth, columnHeight;


    PlayPegSolitaireController playPegSolitaireController;

    PlayPegSolitaireActivity() {
        playPegSolitaireController = new PlayPegSolitaireController();
    }

    /**
     * Set up the background image for each button based on the master list
     * of positions, and then call the adapter to set the view.
     */
    // Display
    public void display() {
        setNumberOfMovesText();
        setNumberOfUndosText();
        tileButtons = playPegSolitaireController.updateTileButtons();
        gridView.setAdapter(new CustomAdapter(tileButtons, columnWidth, columnHeight));
        if (pegSolitaireManager.isOver()) {
            if (pegSolitaireManager.hasWon()) {
                switchToScoreBoard();
            } else {
                displayNoMoreMovesBanner();
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SaveAndLoad.loadFromFile(PlayPegSolitaireActivity.this, LoginActivity.SAVE_FILENAME);
        pegSolitaireManager = (PegSolitaireManager) GameLauncher.getCurrentUser().getRecentManagerOfBoard(PegSolitaireManager.GAME_NAME);
        playPegSolitaireController.createTileButtons(this, pegSolitaireManager);

        SaveAndLoad.saveToFile(PlayPegSolitaireActivity.this, LoginActivity.SAVE_FILENAME);
        setContentView(R.layout.activity_main);
        addUndoButtonListener();
        addSaveButtonListener();

        addView();
    }

    private void addView() {
        gridView = findViewById(R.id.grid);
        gridView.setNumColumns(PegSolitaireBoard.NUM_COLS);
        gridView.setManager(pegSolitaireManager);
        pegSolitaireManager.getBoard().addObserver(this);

        // Observer sets up desired dimensions as well as calls our display function
        gridView.getViewTreeObserver().addOnGlobalLayoutListener(
                new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        gridView.getViewTreeObserver().removeOnGlobalLayoutListener(
                                this);
                        int displayWidth = gridView.getMeasuredWidth();
                        int displayHeight = gridView.getMeasuredHeight();

                        columnWidth = displayWidth / PegSolitaireBoard.NUM_COLS;
                        columnHeight = displayHeight / PegSolitaireBoard.NUM_ROWS;

                        display();
                    }
                });
    }

    /**
     * Activate the save button.
     */
    private void addSaveButtonListener() {
        Button saveButton = findViewById(R.id.SaveButton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ((!GameLauncher.getCurrentUser().getStackOfGameStates(PegSolitaireManager.GAME_NAME).isEmpty())
                        || (GameLauncher.getCurrentUser().getNumOfUndos(PegSolitaireManager.GAME_NAME) != 0)) {
                    makeToastSavedText();
                    switchToGameCentre();
                }
                else {makeToastNoSaveAndQuit();
                }
            }
        });
    }

    private void switchToGameCentre() {
        Intent tmp = new Intent(this, GameCentreActivity.class);
        startActivity(tmp);
    }

    private void switchToSetUp() {
        Intent tmp = new Intent(this, SetUpActivity.class);
        tmp.putExtra("game", PegSolitaireManager.GAME_NAME);
        startActivity(tmp);
    }

    private void switchToScoreBoard() {
        Intent tmp = new Intent(this, ScoreBoardActivity.class);
        List<Boolean> takenScores = playPegSolitaireController.endOfGame(pegSolitaireManager);
        ScoreBoardActivity.newGameScore = takenScores.get(0);
        ScoreBoardActivity.newUserScore = takenScores.get(1);
        tmp.putExtra("scores", PegSolitaireManager.pegScoreBoard.toString());
        tmp.putExtra("game", PegSolitaireManager.GAME_NAME);
        startActivity(tmp);
    }

    /**
     * Activate the undo button.
     */
    private void addUndoButtonListener() {
        Button undoButton = findViewById(R.id.UndoButton);
        undoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String undoText = playPegSolitaireController.usedNumberOfUndos();
                if (undoText.equals("setNumberOfMovesText")) {
                    setNumberOfUndosText();
                    SaveAndLoad.saveToFile(PlayPegSolitaireActivity.this, LoginActivity.SAVE_FILENAME);
                }
                else if (undoText.equals("NoUndoText")) {
                    makeToastNoUndoText();
                }
                else { //got "UndoLimitText";
                    makeToastUndoLimitText();
                }
            }
        });
    }

    /**
     * Display that a game was saved successfully.
     */
    private void makeToastSavedText() {
        Toast.makeText(this, "Game Saved", Toast.LENGTH_SHORT).show();
    }

    /**
     * Motify user when undo limit is reached.
     */
    private void makeToastUndoLimitText() {
        Toast.makeText(this, "Undo Limit Reached", Toast.LENGTH_SHORT).show();
    }

    private void makeToastNoUndoText() {
        Toast.makeText(this, "Undo Not Possible", Toast.LENGTH_SHORT).show();
    }


    /**
     * Set and modify the text showing the number of moves the user completed after each move the user makes.
     * TODO: if a new xml file is made then change the id of the TextView box
     */
    public void setNumberOfMovesText() {
        int numMoves = this.playPegSolitaireController.getNumberOfMoves();
        TextView moves = findViewById(R.id.changingNumberOfMoves);
        moves.setText(Integer.toString(numMoves));
    }

    /**
     * Set and modify the text showing the number of undos the user completed after each undo the user makes.
     * TODO: if a new xml file is made then change the id of the TextView box
     */
    public void setNumberOfUndosText() {
        int numberOfUndos = this.playPegSolitaireController.getNumberOfUndos();
        TextView undos = findViewById(R.id.changingNumberOfUndos);
        undos.setText(Integer.toString(numberOfUndos));
    }

    /**
     * Notify user when they have made no moves, so are not allowed to undo
     */
    private void makeToastNoSaveAndQuit() {
        Toast.makeText(this, "Must make a move before saving", Toast.LENGTH_SHORT).show();
    }

    private void displayNoMoreMovesBanner() {
        //Adapted from https://www.tutorialspoint.com/android/android_alert_dialoges.htm

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setMessage("Game over: There are no more moves. To win you must clear the board of all tiles except one. Better luck next time!");
        alertDialog.setPositiveButton("New Game", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switchToSetUp();
            }
        });
        alertDialog.setNegativeButton("Main Menu", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switchToGameCentre();
            }
        });

        alertDialog.show();
    }

    @Override
    public void update(Observable o, Object arg) {
        display();
    }
}

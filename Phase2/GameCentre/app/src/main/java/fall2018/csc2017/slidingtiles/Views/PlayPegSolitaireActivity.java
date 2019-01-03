package fall2018.csc2017.slidingtiles.Views;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import fall2018.csc2017.slidingtiles.Controllers.PlayPegSolitaireController;
import fall2018.csc2017.slidingtiles.Controllers.CustomAdapter;
import fall2018.csc2017.slidingtiles.GameLauncher;
import fall2018.csc2017.slidingtiles.Games.PegSolitaire.PegSolitaireBoard;
import fall2018.csc2017.slidingtiles.Games.PegSolitaire.PegSolitaireManager;
import fall2018.csc2017.slidingtiles.Controllers.GestureDetectGridView;
import fall2018.csc2017.slidingtiles.R;
import fall2018.csc2017.slidingtiles.SaveAndLoad;

/**
 * The Activity where the user plays Peg Solitaire Activity
 */
public class PlayPegSolitaireActivity extends AppCompatActivity implements Observer {

    /**
     * the pegSolitaireManager of this game
     */
    private PegSolitaireManager pegSolitaireManager;

    /**
     * Grid View and calculated column height and width based on device size
     */
    private GestureDetectGridView gridView;

    /**
     * The column width and height for the Gesture Grid Detector
     */
    private static int columnWidth, columnHeight;

    /**
     * The Movement controller for Peg Solitaire
     */
    PlayPegSolitaireController playPegSolitaireController = new PlayPegSolitaireController();

    /**
     * Set up the background image for each button based on the master list
     * of positions, and then call the adapter to set the view.
     */
    // Display
    public void display() {
        setNumberOfMovesText();
        setNumberOfUndosText();
        ArrayList<Button> tileButtons = playPegSolitaireController.updateTileButtons();
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
        pegSolitaireManager = (PegSolitaireManager) GameLauncher.getCurrentUser().getRecentManagerOfBoard(PegSolitaireManager.getGameName());
        Integer size = getIntent().getIntExtra("shape", 6);
        PegSolitaireBoard.setDimensions(size);
        pegSolitaireManager = (PegSolitaireManager) GameLauncher.getCurrentUser().getRecentManagerOfBoard(PegSolitaireManager.getGameName());
        playPegSolitaireController.createTileButtons(this, pegSolitaireManager);

        SaveAndLoad.saveToFile(PlayPegSolitaireActivity.this, LoginActivity.SAVE_FILENAME);
        setContentView(R.layout.activity_main);
        addUndoButtonListener();
        addSaveButtonListener();

        addView();
    }

    /**
     * Create the view for the display when originally setting up this screen
     */
    private void addView() {
        gridView = findViewById(R.id.grid);
        gridView.setNumColumns(PegSolitaireBoard.getNumCols());
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

                        columnWidth = displayWidth / PegSolitaireBoard.getNumCols();
                        columnHeight = displayHeight / PegSolitaireBoard.getNumRows();

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
                if ((!GameLauncher.getCurrentUser().getStackOfGameStates(PegSolitaireManager.getGameName()).isEmpty())
                        || (GameLauncher.getCurrentUser().getNumOfUndos(PegSolitaireManager.getGameName()) != 0)) {
                    makeToastSavedText();
                    switchToGameCentre();
                } else {
                    makeToastNoSaveAndQuit();
                }
            }
        });
    }

    /**
     * Dispatch onPause() to fragments.
     */
    @Override
    protected void onPause() {
        super.onPause();
        SaveAndLoad.saveToFile(PlayPegSolitaireActivity.this, LoginActivity.SAVE_FILENAME);
    }

    /**
     * Move to GameCentreActivity
     */
    public void switchToGameCentre() {
        Intent tmp = new Intent(this, GameCentreActivity.class);
        startActivity(tmp);
    }

    /**
     * Move to ScoreBoardActivity
     */
    private void switchToSetUp() {
        Intent tmp = new Intent(this, SetUpActivity.class);
        tmp.putExtra("game", PegSolitaireManager.getGameName());
        startActivity(tmp);
    }

    /**
     * Move to ScoreBoardActivity
     */
    private void switchToScoreBoard() {
        Intent tmp = new Intent(this, ScoreBoardActivity.class);
        List<Boolean> takenScores = playPegSolitaireController.endOfGame(pegSolitaireManager);
        ScoreBoardActivity.newGameScore = takenScores.get(0);
        ScoreBoardActivity.newUserScore = takenScores.get(1);
        tmp.putExtra("scores", PegSolitaireManager.getGameScoreboard().toString());
        tmp.putExtra("game", PegSolitaireManager.getGameName());
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
                } else if (undoText.equals("NoUndoText")) {
                    makeToastNoUndoText();
                } else { //got "UndoLimitText";
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
     */
    public void setNumberOfMovesText() {
        int numMoves = this.playPegSolitaireController.getNumberOfMoves();
        TextView moves = findViewById(R.id.changingNumberOfMoves);
        moves.setText(Integer.toString(numMoves));
    }

    /**
     * Set and modify the text showing the number of undos the user completed after each undo the user makes.
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

    /**
     * Display a banner to tell the user there are no more possible moves
     */
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

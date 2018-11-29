package fall2018.csc2017.slidingtiles;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

/**
 * The game activity.
 */
public class PlaySlidingTilesActivity extends AppCompatActivity implements Observer {

    /**
     * The board manager.
     */
    private SlidingTilesManager slidingTilesManager;

    /**
     * The buttons to display.
     */
    private ArrayList<Button> tileButtons;

    // Grid View and calculated column height and width based on device size
    private GestureDetectGridView gridView;
    private static int columnWidth, columnHeight;

    /**
     * Set up the background image for each button based on the master list
     * of positions, and then call the adapter to set the view.
     */

    PlaySlidingTilesController playSlidingTilesController;

    PlaySlidingTilesActivity() {
        this.playSlidingTilesController  = new PlaySlidingTilesController();
    }

    // Display
    public void display() {
        tileButtons = playSlidingTilesController.updateTileButtons();

        gridView.setAdapter(new CustomAdapter(tileButtons, columnWidth, columnHeight));
        if (slidingTilesManager.isOver()) {
            playSlidingTilesController.endOfGame(slidingTilesManager);
            switchToScoreBoard();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SaveAndLoad.loadFromFile(PlaySlidingTilesActivity.this, LoginActivity.SAVE_FILENAME);
        slidingTilesManager = (SlidingTilesManager) GameLauncher.getCurrentUser().getRecentManagerOfBoard(SlidingTilesManager.GAME_NAME);
        playSlidingTilesController.createTileButtons(this, slidingTilesManager);

        SaveAndLoad.saveToFile(PlaySlidingTilesActivity.this, LoginActivity.SAVE_FILENAME);
        setContentView(R.layout.activity_main);
        addUndoButtonListener();
        addSaveButtonListener();

        addView();

    }

    /**
     * Add View to this Activity
     */
    private void addView() {
        gridView = findViewById(R.id.grid);
        gridView.setNumColumns(SlidingTilesBoard.NUM_COLS);
        gridView.setManager(slidingTilesManager);
        slidingTilesManager.getBoard().addObserver(this);

        // Observer sets up desired dimensions as well as calls our display function
        gridView.getViewTreeObserver().addOnGlobalLayoutListener(
                new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        gridView.getViewTreeObserver().removeOnGlobalLayoutListener(
                                this);
                        int displayWidth = gridView.getMeasuredWidth();
                        int displayHeight = gridView.getMeasuredHeight();

                        columnWidth = displayWidth / SlidingTilesBoard.NUM_COLS;
                        columnHeight = displayHeight / SlidingTilesBoard.NUM_ROWS;

                        display();
                    }
                });
    }

    /**
     * Dispatch onPause() to fragments.
     */
    @Override
    protected void onPause() {
        super.onPause();
        SaveAndLoad.saveToFile(PlaySlidingTilesActivity.this, LoginActivity.SAVE_FILENAME);
    }


    /**
     * Activate the undo button.
     */
    private void addUndoButtonListener() {
        Button undoButton = findViewById(R.id.UndoButton);
        undoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String undoText = playSlidingTilesController.usedNumberOfUndos();
                if (undoText.equals("setNumberOfMovesText")) {
                    setNumberOfUndosText();
                    SaveAndLoad.saveToFile(PlaySlidingTilesActivity.this, LoginActivity.SAVE_FILENAME);
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
     * Notify user when undo limit is reached.
     */
    private void makeToastUndoLimitText() {
        Toast.makeText(this, "Undo Limit Reached", Toast.LENGTH_SHORT).show();
    }

    /**
     * Notify user when they have made no moves, so are not allowed to undo
     */
    private void makeToastNoUndoText() {
        Toast.makeText(this, "Undo Not Possible", Toast.LENGTH_SHORT).show();
    }

    /**
     * Notify user when they have made no moves, so are not allowed to undo
     */
    private void makeToastNoSaveAndQuit() {
        Toast.makeText(this, "Must make a move before saving", Toast.LENGTH_SHORT).show();
    }

    /**
     * Activate the save button.
     */
    private void addSaveButtonListener() {
        Button saveButton = findViewById(R.id.SaveButton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ((!GameLauncher.getCurrentUser().getStackOfGameStates(SlidingTilesManager.GAME_NAME).isEmpty())
                    || (GameLauncher.getCurrentUser().getNumOfUndos(SlidingTilesManager.GAME_NAME) != 0)) {
                    makeToastSavedText();
                    switchToGameCentre();
                }
                else {makeToastNoSaveAndQuit();
                }
            }
        });
    }

    public void switchToGameCentre() {
        Intent tmp = new Intent(this, GameCentreActivity.class);
        startActivity(tmp);
    }

    public void switchToScoreBoard() {
        Intent tmp = new Intent(this, ScoreBoardActivity.class);
        tmp.putExtra("scores", SlidingTilesManager.gameScoreBoard.toString());
        startActivity(tmp);
    }

    /**
     * Display that a game was saved successfully.
     */
    private void makeToastSavedText() {
        Toast.makeText(this, "Game Saved", Toast.LENGTH_SHORT).show();
    }

    /**
     * Set and modify the text showing the number of moves the user completed after each move the user makes.
     */
    public void setNumberOfMovesText() {
        int numMoves = this.playSlidingTilesController.numOfMoves();
        TextView moves = findViewById(R.id.changingNumberOfMoves);
        moves.setText(Integer.toString(numMoves));
    }
    /**
     * Set and modify the text showing the number of undos the user completed after each undo the user makes.
     */
    public void setNumberOfUndosText() {
        int numberOfUndos = this.playSlidingTilesController.numOfUndos();
        TextView undos = findViewById(R.id.changingNumberOfUndos);
        undos.setText(Integer.toString(numberOfUndos));
    }

    @Override
    public void update(Observable o, Object arg) {
        setNumberOfMovesText();
        setNumberOfUndosText();
        display();
    }
}

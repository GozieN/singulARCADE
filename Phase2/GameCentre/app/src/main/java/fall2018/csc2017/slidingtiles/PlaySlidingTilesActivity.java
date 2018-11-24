package fall2018.csc2017.slidingtiles;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.Stack;

/**
 * The game activity.
 */
public class PlaySlidingTilesActivity extends AppCompatActivity implements Observer {

    /**
     * The board manager.
     */
    private SlidingTilesManager slidingTilesManager;

    /**
     * The user manager.
     */
    private UserManager userManager;

    /**
     * The buttons to display.
     */
    private ArrayList<Button> tileButtons;

    // Grid View and calculated column height and width based on device size
    private GestureDetectGridView gridView;
    private static int columnWidth, columnHeight;

    /**
     * The number of times a user has clicked the undo button.
     */
    static int numberOfUndos = 0;

    /**
     * Set up the background image for each button based on the master list
     * of positions, and then call the adapter to set the view.
     */
    // Display
    public void display() {
        updateTileButtons();
        gridView.setAdapter(new CustomAdapter(tileButtons, columnWidth, columnHeight));
        if (slidingTilesManager.isOver()) {
            endOfGame();
            switchToScoreBoard();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadFromFile(LoginActivity.SAVE_FILENAME);

        createTileButtons(this);
        setContentView(R.layout.activity_main);
        addUndoButtonListener();
        addSaveButtonListener();

        // Add View to activity
        if (slidingTilesManager instanceof SlidingTilesManager) {
            gridView = findViewById(R.id.grid);
            //SlidingTilesManager slidingTilesManager = (SlidingTilesManager) slidingTilesManager;
        }
//        if (slidingTilesManager instanceof PegSolitaireManager) {
//            //PegSolitaireManager slidingTilesManager = (PegSolitaireManager) slidingTilesManager;
//            gridView = findViewById(R.id.squareGrid);
//        }
        gridView.setNumColumns(SlidingTilesBoard.NUM_COLS);
        gridView.setSlidingTilesManager(slidingTilesManager);
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
     * Create the buttons for displaying the tiles.
     *
     * @param context the context
     */
    private void createTileButtons(Context context) {
        SlidingTilesBoard board = slidingTilesManager.getBoard();
        tileButtons = new ArrayList<>();
        for (int row = 0; row != SlidingTilesBoard.NUM_ROWS; row++) {
            for (int col = 0; col != SlidingTilesBoard.NUM_COLS; col++) {
                Button tmp = new Button(context);
                tmp.setBackgroundResource(board.getTile(row, col).getBackground());
                this.tileButtons.add(tmp);
            }
        }
    }

    /**
     * Update the backgrounds on the buttons to match the tiles.
     */
    private void updateTileButtons() {
        SlidingTilesBoard board = slidingTilesManager.getBoard();
        int nextPos = 0;
        for (Button b : tileButtons) {
            int row = nextPos / SlidingTilesBoard.NUM_ROWS;
            int col = nextPos % SlidingTilesBoard.NUM_COLS;
            b.setBackgroundResource(board.getTile(row, col).getBackground());
            nextPos++;
        }
        saveToFile(LoginActivity.SAVE_FILENAME);
    }

    /**
     * Dispatch onPause() to fragments.
     */
    @Override
    protected void onPause() {
        super.onPause();
        saveToFile(LoginActivity.SAVE_FILENAME);
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

    /**
     * Activate the undo button.
     */
    private void addUndoButtonListener() {
        Button undoButton = findViewById(R.id.UndoButton);
        undoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (numberOfUndos < SlidingTilesSetUpActivity.undoLimit) {
                    Stack totalStates = GameLauncher.getCurrentUser().getStackOfGameStates(SlidingTilesManager.GAME_NAME);
                    if(totalStates.size() != 0) {
                        List state = GameLauncher.getCurrentUser().getState(SlidingTilesManager.GAME_NAME);
                        int row1 = (Integer) state.get(2);
                        int col1 = (Integer) state.get(3);
                        int row2 = (Integer) state.get(0);
                        int col2 = (Integer) state.get(1);
                        slidingTilesManager.getBoard().swapTiles(row1, col1, row2, col2);
                        numberOfUndos++;
                    } else {
                        makeToastNoUndoText();
                    }

                } else {
                    makeToastUndoLimitText();
                }
            }
        });
    }

    /**
     * At the end of the game, do these actions: get the score, and send score to game score board and user score board.
     */
    private void endOfGame() {
        Integer score = slidingTilesManager.getScore();
        SlidingTilesManager.gameScoreBoard.takeNewScore(GameLauncher.getCurrentUser().getUsername(), score);
        GameLauncher.getCurrentUser().userScoreBoard.takeNewScore(SlidingTilesManager.GAME_NAME, score);
        saveToFile(LoginActivity.SAVE_FILENAME);
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
                if (GameLauncher.getCurrentUser().getStackOfGameStates(SlidingTilesManager.GAME_NAME).size() > 0) {
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

    private void switchToScoreBoard() {
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

    @Override
    public void update(Observable o, Object arg) {
        display();
    }
}

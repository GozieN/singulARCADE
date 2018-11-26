package fall2018.csc2017.slidingtiles;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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
import java.util.Observable;
import java.util.Observer;

public class PlayMemoryPuzzleActivity extends AppCompatActivity implements Observer {

    /**
     * The Memory board manager.
     */
    private MemoryBoardManager memoryBoardManager;

    private UserManager userManager;

    private ScoreBoard scoreBoard;

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
    // Display
    public void display() {
        updateTileButtons();
        gridView.setAdapter(new CustomAdapter(tileButtons, columnWidth, columnHeight));
        if (memoryBoardManager.isOver()) {
            endOfGame();
            switchToScoreBoard();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadFromFile(LoginActivity.SAVE_FILENAME);

        createTileButtons(this);
        setContentView(R.layout.activity_memory_game);
        addSaveButtonListener();

        // Add View to activity
        gridView = findViewById(R.id.grid);
        gridView.setNumColumns(MemoryGameBoard.NUM_COLS);
        gridView.setManager(memoryBoardManager);
        memoryBoardManager.getBoard().addObserver(this);
        // Observer sets up desired dimensions as well as calls our display function
        gridView.getViewTreeObserver().addOnGlobalLayoutListener(
                new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        gridView.getViewTreeObserver().removeOnGlobalLayoutListener(
                                this);
                        int displayWidth = gridView.getMeasuredWidth();
                        int displayHeight = gridView.getMeasuredHeight();

                        columnWidth = displayWidth / MemoryGameBoard.NUM_COLS;
                        columnHeight = displayHeight / MemoryGameBoard.NUM_ROWS;

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
        MemoryGameBoard board = memoryBoardManager.getBoard();
        tileButtons = new ArrayList<>();
        for (int row = 0; row != MemoryGameBoard.NUM_ROWS; row++) {
            for (int col = 0; col != MemoryGameBoard.NUM_COLS; col++) {
                Button tmp = new Button(context);
                System.out.println("later in function createTileButtons " + board.getMemoryGameTile(row, col));
                tmp.setBackgroundResource(board.getMemoryGameTile(row, col).getBackground());
                this.tileButtons.add(tmp);
            }
        }
    }

    /**
     * Update the backgrounds on the buttons to match the tiles.
     */
    private void updateTileButtons() {
        MemoryGameBoard board = memoryBoardManager.getBoard();
        int nextPos = 0;
        for (Button b : tileButtons) {
            int row = nextPos / MemoryGameBoard.NUM_ROWS;
            int col = nextPos % MemoryGameBoard.NUM_COLS;
            b.setBackgroundResource(board.getMemoryGameTile(row, col).getBackground());
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
//        saveToFile(StartingActivity.TEMP_SAVE_FILENAME);
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
            } else {
                ObjectInputStream input = new ObjectInputStream(inputStream);
                userManager = (UserManager) input.readObject();
                memoryBoardManager = (MemoryBoardManager) GameLauncher.getCurrentUser().getRecentManagerOfBoard(MemoryBoardManager.GAME_NAME);
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
     * At the end of the game, do these actions: get the score, and send score to game score board and user score board.
     */
    private void endOfGame() {
        Integer score = memoryBoardManager.getScore();
        memoryBoardManager.gameScoreBoard.takeNewScore(GameLauncher.getCurrentUser().getUsername(), score);
        GameLauncher.getCurrentUser().userScoreBoard.takeNewScore(MemoryBoardManager.GAME_NAME, score);
    }

    /**
     * Activate the save button.
     */
    private void addSaveButtonListener() {
        Button saveButton = findViewById(R.id.SaveButton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makeToastSavedText();
                switchToGameCentre();
            }
        });
    }

    private void switchToGameCentre() {
        Intent tmp = new Intent(this, GameCentreActivity.class);
        startActivity(tmp);
    }

    private void switchToScoreBoard() {
        Intent tmp = new Intent(this, ScoreBoardActivity.class);
        tmp.putExtra("scores", memoryBoardManager.gameScoreBoard.toString());
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



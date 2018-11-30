package fall2018.csc2017.slidingtiles;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import java.util.List;

public class PlayMemoryPuzzleActivity extends AppCompatActivity implements Observer {

    /**
     * The Memory board manager.
     */
    private MemoryBoardManager memoryBoardManager;

    /**
     * The buttons to display.
     */
    private ArrayList<Button> tileButtons;

    // Grid View and calculated column height and width based on device size
    private GestureDetectGridView gridView;
    private static int columnWidth, columnHeight;

    PlayMemoryPuzzleController playMemoryPuzzleController;

    PlayMemoryPuzzleActivity() {
        playMemoryPuzzleController = new PlayMemoryPuzzleController();
    }

    /**
     * Set up the background image for each button based on the master list
     * of positions, and then call the adapter to set the view.
     */
    // Display
    public void display() {
        tileButtons = playMemoryPuzzleController.updateTileButtons();
        gridView.setAdapter(new CustomAdapter(tileButtons, columnWidth, columnHeight));
        if (memoryBoardManager.isOver()) {
            switchToScoreBoard();
            // setSnackBar(playMemoryPuzzleController.endOfGame(memoryBoardManager));
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SaveAndLoad.loadFromFile(PlayMemoryPuzzleActivity.this, LoginActivity.SAVE_FILENAME);
        memoryBoardManager = (MemoryBoardManager) GameLauncher.getCurrentUser().getRecentManagerOfBoard(MemoryBoardManager.GAME_NAME);
        playMemoryPuzzleController.createTileButtons(this, memoryBoardManager);
        setContentView(R.layout.activity_memory_game);
        addSaveButtonListener();
        addView();
    }

    /**
     * Add View to this Activity
     */
    private void addView() {

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
     * Dispatch onPause() to fragments.
     */
    @Override
    protected void onPause() {
        super.onPause();
        SaveAndLoad.saveToFile(PlayMemoryPuzzleActivity.this, LoginActivity.SAVE_FILENAME);
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
        List<Boolean> takenScores = playMemoryPuzzleController.endOfGame(memoryBoardManager);
        ScoreBoardActivity.newGameScore = takenScores.get(0);
        ScoreBoardActivity.newUserScore = takenScores.get(1);
        tmp.putExtra("scores", MemoryBoardManager.gameScoreBoard.toString());
        tmp.putExtra("game", MemoryBoardManager.GAME_NAME);
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



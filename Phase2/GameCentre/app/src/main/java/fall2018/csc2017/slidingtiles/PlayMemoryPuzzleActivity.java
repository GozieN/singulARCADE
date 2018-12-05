package fall2018.csc2017.slidingtiles;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import java.util.List;

/**
 * The Activity to play Memory Puzzle
 */
public class PlayMemoryPuzzleActivity extends AppCompatActivity implements Observer {

    /**
     * The Memory board manager.
     */
    private MemoryBoardManager memoryBoardManager;

    /**
     * Grid View and calculated column height and width based on device size
     */
    private GestureDetectGridView gridView;

    /**
     * The column width and height of the Memory Puzzle Activity
     */
    private static int columnWidth, columnHeight;

    /**
     * The MemoryPuzzle Controller
     */
    PlayMemoryPuzzleController playMemoryPuzzleController = new PlayMemoryPuzzleController();

    /**
     * Set up the background image for each button based on the master list
     * of positions, and then call the adapter to set the view.
     */
    // Display
    public void display() {
        ArrayList<Button> tileButtons = playMemoryPuzzleController.updateTileButtons();
        setNumberOfMovesText();
        gridView.setAdapter(new CustomAdapter(tileButtons, columnWidth, columnHeight));
        if (memoryBoardManager.isOver()) {
            switchToScoreBoard();
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
                if (!GameLauncher.getCurrentUser().getStackOfGameStates(MemoryBoardManager.GAME_NAME).isEmpty()
                        && (memoryBoardManager.numTileFlipped() == 2 || memoryBoardManager.numberOfMatches() != 0)) {
                    memoryBoardManager.resetToWhite();
                    makeToastSavedText();
                    switchToGameCentre();
                } else {
                    makeToastNoSaveAndQuit();
                }
            }
        });
    }

    /**
     * Notify user when they have made no moves, so are not allowed to undo
     */
    private void makeToastNoSaveAndQuit() {
        Toast.makeText(this, "Must make a move before saving", Toast.LENGTH_SHORT).show();
    }

    /**
     * Switch Activity to Game Centre
     */
    private void switchToGameCentre() {
        Intent tmp = new Intent(this, GameCentreActivity.class);
        startActivity(tmp);
    }

    /**
     * Switch Activity to the ScoreBoard when game is won
     */
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

    /**
     * Set and modify the text showing the number of moves the user completed after each move the user makes.
     */
    public void setNumberOfMovesText() {
        int numMoves = this.playMemoryPuzzleController.getNumberOfMoves();
        TextView moves = findViewById(R.id.memChangingNumberOfMoves);
        moves.setText(Integer.toString(numMoves));
    }

    @Override
    public void update(Observable o, Object arg) {
        display();
    }
}



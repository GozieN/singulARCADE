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

    private UserManager userManager;
    /**
     *
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
        if (pegSolitaireManager.isOver()) {
            endOfGame();
            switchToScoreBoard();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadFromFile(LoginActivity.SAVE_FILENAME);

        pegSolitaireManager = new PegSolitaireManager(makeBoard());

        createTileButtons(this);
        setContentView(R.layout.activity_main);
        addUndoButtonListener();
        addSaveButtonListener();

        addView();
    }

    private void addView() {
        gridView = findViewById(R.id.squareGrid);
        gridView.setNumColumns(PegSolitaireBoard.NUM_COLS);
        System.out.println("ADD VIEW: ");
        System.out.println(pegSolitaireManager);
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
     * Update the backgrounds on the buttons to match the tiles.
     */
    private void updateTileButtons() {
        PegSolitaireBoard board = pegSolitaireManager.getBoard();
        int nextPos = 0;
        for (Button b : tileButtons) {
            int row = nextPos / PegSolitaireBoard.NUM_ROWS;
            int col = nextPos % PegSolitaireBoard.NUM_COLS;
            b.setBackgroundResource(board.getPegTile(row, col).getBackground());
            nextPos++;
        }
        saveToFile(LoginActivity.SAVE_FILENAME);
    }

    /**
     * Return a Peg Solitaire Board.
     * @return a PegSolitaireBoard
     */
    private PegSolitaireBoard makeBoard() {
        PegSolitaireBoard board;

        Integer size = getIntent().getIntExtra("shape", 6);
        PegSolitaireBoard.setDimensions(size);

        List<PegSolitaireTile> tiles = new ArrayList<>();
        final int numTiles = PegSolitaireBoard.NUM_ROWS * PegSolitaireBoard.NUM_COLS;
        for (int tileNum = 0; tileNum != numTiles; tileNum++) {
            tiles.add(new PegSolitaireTile(tileNum));
        }

        board = new PegSolitaireBoard(tiles);
        return board;
    }

    /**
     * Create the buttons for displaying the tiles.
     *
     * @param context the context
     */
    private void createTileButtons(Context context) {
        PegSolitaireBoard board = pegSolitaireManager.getBoard();
        tileButtons = new ArrayList<>();
        for (int row = 0; row != PegSolitaireBoard.NUM_ROWS; row++) {
            for (int col = 0; col != PegSolitaireBoard.NUM_COLS; col++) {
                Button tmp = new Button(context);
                tmp.setBackgroundResource(board.getPegTile(row, col).getBackground());
                this.tileButtons.add(tmp);
            }
        }
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
        tmp.putExtra("scores", PegSolitaireManager.pegScoreBoard.toString());
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
                if (numberOfUndos < SetUpActivity.undoLimit) {
                    Stack totalStates = GameLauncher.getCurrentUser().getStackOfGameStates(PegSolitaireManager.GAME_NAME);
                    if(totalStates.size() != 0) {
                        List state = GameLauncher.getCurrentUser().getState(PegSolitaireManager.GAME_NAME);
                        int row1 = (Integer) state.get(2);
                        int col1 = (Integer) state.get(3);
                        int row2 = (Integer) state.get(0);
                        int col2 = (Integer) state.get(1);
                        pegSolitaireManager.getBoard().moveGamepiece(row1, col1, row2, col2);
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
                pegSolitaireManager = (PegSolitaireManager) GameLauncher.getCurrentUser().getRecentManagerOfBoard(PegSolitaireManager.GAME_NAME);
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
        Integer score = pegSolitaireManager.getScore();
        PegSolitaireManager.pegScoreBoard.takeNewScore(GameLauncher.getCurrentUser().getUsername(), score);
        GameLauncher.getCurrentUser().userScoreBoard.takeNewScore(PegSolitaireManager.GAME_NAME, score);
        //TODO: here maybe save the new stuff?? aka make sure updated score of user is put in and update on overall scoreboard for game
        saveToFile(LoginActivity.SAVE_FILENAME); //this will save the user w the new score... but need to fix it for other scoreboards
    }

    @Override
    public void update(Observable o, Object arg) {
        display();
    }
}

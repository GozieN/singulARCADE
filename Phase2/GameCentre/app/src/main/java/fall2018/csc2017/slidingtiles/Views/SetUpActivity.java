package fall2018.csc2017.slidingtiles.Views;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import fall2018.csc2017.slidingtiles.Controllers.PlayMemoryPuzzleController;
import fall2018.csc2017.slidingtiles.Controllers.PlayPegSolitaireController;
import fall2018.csc2017.slidingtiles.Controllers.PlaySlidingTilesController;
import fall2018.csc2017.slidingtiles.Controllers.SetUpAndStartController;
import fall2018.csc2017.slidingtiles.GameLauncher;
import fall2018.csc2017.slidingtiles.Games.Game;
import fall2018.csc2017.slidingtiles.Games.MemoryPuzzle.MemoryBoardManager;
import fall2018.csc2017.slidingtiles.Games.MemoryPuzzle.MemoryGameBoard;
import fall2018.csc2017.slidingtiles.Games.PegSolitaire.PegSolitaireBoard;
import fall2018.csc2017.slidingtiles.Games.PegSolitaire.PegSolitaireManager;
import fall2018.csc2017.slidingtiles.Games.SlidingTiles.SlidingTilesManager;
import fall2018.csc2017.slidingtiles.R;
import fall2018.csc2017.slidingtiles.SaveAndLoad;

/**
 * The Activity to set up the settings of each game
 */
public class SetUpActivity extends AppCompatActivity {

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
    private static int undoLimit;
    /**
     * The Gmae being played
     */
    private Game gameManager;
    /**
     * The name identifier of the game being played
     */
    private String game;
    /**
     * The Movement Controller for the Set Up Activity.
     */
    SetUpAndStartController setUpController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setUpController = new SetUpAndStartController();
        game = getIntent().getStringExtra("game");
        setSpinnerOnCreate();
        addPlayButtonListener();
        SaveAndLoad.loadFromFile(SetUpActivity.this, LoginActivity.SAVE_FILENAME);
        gameManager = (Game) setUpController.setGameManager(game);
        if (game.equals(SlidingTilesManager.getGameName()) || game.equals(PegSolitaireManager.getGameName())) {
            setSpinnerUndoOnCreate();
        }
    }

    /**
     * Set up spinners accordingly to what game is being played
     */
    public void setSpinnerOnCreate() {
        ArrayAdapter<CharSequence> adapterBoardSize;
        if (game.equals(SlidingTilesManager.getGameName())) {
            setContentView(R.layout.activity_sliding_tiles_set_up);

            spinnerBoardShape = findViewById(R.id.ChooseSlidingTilesSpinner);
            adapterBoardSize = ArrayAdapter.createFromResource(this,
                    R.array.slidingTilesboard_array, android.R.layout.simple_spinner_item);

        } else if (game.equals(PegSolitaireManager.getGameName())) {
            setContentView(R.layout.activity_peg_solitaire_set_up);

            spinnerBoardShape = findViewById(R.id.ChoosePegSolitaireSpinner);
            adapterBoardSize = ArrayAdapter.createFromResource(this,
                    R.array.pegSolitaireBoard_array, android.R.layout.simple_spinner_item);

        } else { //game.equals("MEMORY PUZZLE")
            setContentView(R.layout.activity_memory_game_set_up);
            spinnerBoardShape = findViewById(R.id.ChooseMemoryPuzzleSpinner);
            adapterBoardSize = ArrayAdapter.createFromResource(this,
                    R.array.memoryPuzzle_array, android.R.layout.simple_spinner_item);

        }
        // Specify the layout to use when the list of choices appears
        adapterBoardSize.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Apply the adapter to the spinner
        spinnerBoardShape.setAdapter(adapterBoardSize);
    }

    /**
     * Set the Undo Spinner if the game implements the Undo feature
     */
    public void setSpinnerUndoOnCreate() {
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
                shape = setUpController.setBoardShape(game, spinnerBoardShape);
                if (spinnerUndo != null && spinnerUndo.getSelectedItem() != null) {
                    undoSelection = (String) spinnerUndo.getSelectedItem();
                }
                if (game.equals(SlidingTilesManager.getGameName()) || game.equals(PegSolitaireManager.getGameName())) {
                    undoLimit = Integer.valueOf(undoSelection);
                }
                switchToGame();
            }
        });
    }


    /**
     * Switch to game screen.
     */
    private void switchToGame() {
        Intent tmp;
        if (game.equals(SlidingTilesManager.getGameName())) {
            tmp = new Intent(this, PlaySlidingTilesActivity.class);
            gameManager = setUpController.setSolvableBoardManager(shape);
            PlaySlidingTilesController.setNumberOfMoves(0);
        } else if (game.equals(PegSolitaireManager.getGameName())) { //game.equals("PEG SOLITAIRE")
            tmp = new Intent(this, PlayPegSolitaireActivity.class);
            PegSolitaireBoard.setDimensions(shape);
            gameManager = (PegSolitaireManager) setUpController.newGameManager(PegSolitaireManager.getGameName());
            PlayPegSolitaireController.setNumberOfMoves(0);
        } else {
            tmp = new Intent(this, PlayMemoryPuzzleActivity.class);
            MemoryGameBoard.setDimensions(shape);
            gameManager = (MemoryBoardManager) setUpController.newGameManager(MemoryBoardManager.getGameName());
            PlayMemoryPuzzleController.setNumberOfMoves(0);
        }
        GameLauncher.getCurrentUser().setNumOfUndos(game, 0);
        GameLauncher.getCurrentUser().setRecentManagerOfBoard(game, gameManager);
        GameLauncher.getCurrentUser().setEmptyStackOfGameStates(game);
        tmp.putExtra("shape", shape);
        tmp.putExtra("game", PegSolitaireManager.getGameName());
        SaveAndLoad.saveToFile(SetUpActivity.this, LoginActivity.SAVE_FILENAME);
        startActivity(tmp);
    }

    public static int getUndoLimit() {
        return undoLimit;
    }

    public static void setUndoLimit(int undo) {
        undoLimit = undo;
    }
}

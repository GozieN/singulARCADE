
package fall2018.csc2017.slidingtiles;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

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
    static int undoLimit;

    /**
     * The manager of the game being played
     */
    private Game gameManager;

    /**
     * The name of the game
     */
    private String game;

    SetUpAndStartController setUpController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setUpController = new SetUpAndStartController();
        game = getIntent().getStringExtra("game");
        setGame();
        addPlayButtonListener();
        SaveAndLoad.loadFromFile(SetUpActivity.this, LoginActivity.SAVE_FILENAME);
        gameManager = (Game) setUpController.setGameManager(game);
        //undoSpinner();
    }

    /**
     * Set the shape / dimensions of the game and if applicable, the amount of undos
     */
    private void setGame() {
        ArrayAdapter<CharSequence> adapterBoardSize;

        //adapted from https://developer.android.com/guide/topics/ui/controls/spinner#java
        if (game.equals(SlidingTilesManager.GAME_NAME)) {
            setContentView(R.layout.activity_sliding_tiles_set_up);

            spinnerBoardShape = findViewById(R.id.ChooseSlidingTilesSpinner);
            adapterBoardSize = ArrayAdapter.createFromResource(this,
                    R.array.slidingTilesboard_array, android.R.layout.simple_spinner_item);

        } else if (game.equals(PegSolitaireManager.GAME_NAME)) {
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
     * Add the undo spinner
     */
    private void undoSpinner() {
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
                if (game.equals(SlidingTilesManager.GAME_NAME) || (game.equals(PegSolitaireManager.GAME_NAME))) {
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
        if (game.equals(SlidingTilesManager.GAME_NAME)) {
            PlaySlidingTilesController.numberOfUndos = 0;
            PlaySlidingTilesController.numberOfMoves = 0;
            tmp = new Intent(this, PlaySlidingTilesActivity.class);
            gameManager = setUpController.setSolvableBoardManager(shape);
            PlaySlidingTilesController.numberOfMoves = 0;
            PlaySlidingTilesController.numberOfUndos = 0;
        } else if (game.equals(PegSolitaireManager.GAME_NAME)) {
            PlayPegSolitaireController.numberOfUndos = 0;
            PlayPegSolitaireController.numberOfMoves = 0;
            tmp = new Intent(this, PlayPegSolitaireActivity.class);
            PegSolitaireBoard.setDimensions(shape);
            gameManager = (PegSolitaireManager) setUpController.newGameManager(PegSolitaireManager.GAME_NAME);
            PlayPegSolitaireController.numberOfMoves = 0;
            PlayPegSolitaireController.numberOfUndos = 0;
        } else { //game.equals("MEMORY PUZZLE")
            tmp = new Intent(this, PlayMemoryPuzzleActivity.class);
            MemoryGameBoard.setDimensions(shape);
            gameManager = (MemoryBoardManager) setUpController.newGameManager(MemoryBoardManager.GAME_NAME);
            PlayMemoryPuzzleController.numberOfMoves = 0;
        }
        GameLauncher.getCurrentUser().setNumOfUndos(game, 0);
        GameLauncher.getCurrentUser().setRecentManagerOfBoard(game, gameManager);
        GameLauncher.getCurrentUser().setEmptyStackOfGameStates(game);
        tmp.putExtra("shape", shape);
        tmp.putExtra("game", PegSolitaireManager.GAME_NAME);
        SaveAndLoad.saveToFile(SetUpActivity.this, LoginActivity.SAVE_FILENAME);
        startActivity(tmp);
    }
}

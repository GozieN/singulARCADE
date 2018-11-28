package fall2018.csc2017.slidingtiles;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

/**
 * The initial activity for the sliding puzzle tile game.
 */
public class StartingActivity extends AppCompatActivity {

    /**
     * The board manager.
     */
    Game gameManager;

    /**
     * The name of the current game being played.
     */
    String game;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        game = getIntent().getStringExtra("welcomeText");
        if (game.equals(SlidingTilesManager.GAME_NAME)) {
            gameManager = new SlidingTilesManager();
        }
        if (game.equals(PegSolitaireManager.GAME_NAME)) {
            gameManager = new PegSolitaireManager();
        }
        if (game.equals(MemoryBoardManager.GAME_NAME)) {
            gameManager = new MemoryBoardManager();
        }

        setContentView(R.layout.activity_starting_);
        addStartButtonListener();
        addLoadButtonListener();

        setVariableText();
    }

    /**
     * Set the text dependent on the user's choice of game.
     */
    private void setVariableText() {
        String welcome = "WELCOME TO " + getIntent().getStringExtra("welcomeText");
        String instructions = getIntent().getStringExtra("instructionsText");
        TextView welcomeText = findViewById(R.id.welcomeText);
        TextView instructionsText = findViewById(R.id.instructionsText);
        welcomeText.setText(welcome);
        instructionsText.setText(instructions);
    }


    /**
     * Activate the start button.
     */
    private void addStartButtonListener() {
        Button startButton = findViewById(R.id.StartButton);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchToSetUp();
            }
        });
    }

    /**
     * Activate the load button.
     */
    private void addLoadButtonListener() {
        Button loadButton = findViewById(R.id.LoadButton);
        loadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SaveAndLoad.loadFromFile(StartingActivity.this, LoginActivity.SAVE_FILENAME);
                //loadFromFile(LoginActivity.SAVE_FILENAME);
                makeToastLoadedText();
                switchToGame();
            }
        });
    }

    /**
     * Display that a game was loaded successfully.
     */
    private void makeToastLoadedText() {
        Toast.makeText(this, "Loaded Game", Toast.LENGTH_SHORT).show();
    }

    /**
     * Display that there are no games to be loaded.
     */
    private void makeToastNoGameToLoadText() {
        Toast.makeText(this, "You have no saved games", Toast.LENGTH_SHORT).show();
    }

    /**
     * Read the temporary board from disk.
     */
    @Override
    protected void onResume() {
        super.onResume();
    }

    /**
     * Switch to the GameActivity view to play the game.
     */
    private void switchToGame() {
        Intent tmp;
        if (game.equals(SlidingTilesManager.GAME_NAME)) {
            tmp = new Intent(this, PlaySlidingTilesActivity.class);
        } else if (game.equals(PegSolitaireManager.GAME_NAME)) {
            tmp = new Intent(this, PlayPegSolitaireActivity.class);
        } else {
            tmp = new Intent(this, PlayMemoryPuzzleActivity.class);
        }
        SaveAndLoad.loadFromFile(StartingActivity.this, LoginActivity.SAVE_FILENAME);
        //loadFromFile(LoginActivity.SAVE_FILENAME);
        if (isGameStackEmpty()) {
            makeToastNoGameToLoadText();
        } else {
            startActivity(tmp);
        }
    }

        public boolean isGameStackEmpty() {
            if (game.equals(SlidingTilesManager.GAME_NAME)) {
                if (GameLauncher.getCurrentUser().getStackOfGameStates(SlidingTilesManager.GAME_NAME).isEmpty()) {
                    return true;
                }
            }
            else if (game.equals(PegSolitaireManager.GAME_NAME)) {
                if (GameLauncher.getCurrentUser().getStackOfGameStates(PegSolitaireManager.GAME_NAME).isEmpty()) {
                    return true;
                }
            } else {
                if (GameLauncher.getCurrentUser().getStackOfGameStates(MemoryBoardManager.GAME_NAME).isEmpty()) {
                    makeToastNoGameToLoadText();
                    return true;
                }
            }
            return false;
        }


        /**
         * Switch to the SetUp view to play the game.
         */
        private void switchToSetUp () {
            Intent tmp = new Intent(this, SetUpActivity.class);
            tmp.putExtra("game", game);
            startActivity(tmp);
        }

//        /**
//         * Load the board manager from fileName.
//         *
//         * @param fileName the name of the file
//         */
//        public void loadFromFile (String fileName){
//
//            try {
//                InputStream inputStream = this.openFileInput(fileName);
//                if (inputStream == null) {
//                    saveToFile(fileName);
//                } else {
//                    ObjectInputStream input = new ObjectInputStream(inputStream);
//                    userManager = (UserManager) input.readObject();
//                    inputStream.close();
//                }
//            } catch (FileNotFoundException e) {
//                Log.e("login activity", "File not found: " + fileName);
//            } catch (IOException e) {
//                Log.e("login activity", "Can not read file: " + e.toString());
//            } catch (ClassNotFoundException e) {
//                Log.e("login activity", "File contained unexpected data type: " + e.toString());
//            }
//        }

//        /**
//         * Save the user manager and scoreboard to fileName.
//         *
//         * @param fileName the name of the file
//         */
//        public void saveToFile (String fileName){
//            try {
//                ObjectOutputStream outputStream = new ObjectOutputStream(
//                        this.openFileOutput(fileName, MODE_PRIVATE));
//                outputStream.writeObject(userManager);
//                outputStream.close();
//            } catch (IOException e) {
//                Log.e("Exception", "File write failed: " + e.toString());
//            }
//        }
    }

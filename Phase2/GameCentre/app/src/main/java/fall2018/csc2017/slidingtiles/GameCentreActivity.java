package fall2018.csc2017.slidingtiles;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.content.Intent;


/**
 * The Game Centre activity where the user chooses what game to play.
 */
public class GameCentreActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_centre);
        addMyAccountButtonListener();
        addBuyMoreGameButton();
        addSignOutButtonListener();

        addPegSolitaireButtonListener();
        addSlidingTilesButtonListener();
        addMemoryPuzzleButtonListener();
    }

    /**
     * Activate the buyMoreGameButton
     */
    private void addBuyMoreGameButton() {
        Button googlePlay = findViewById(R.id.googlePlayButton);
        googlePlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String googlePlay = "https://play.google.com/store";
                Uri webaddress = Uri.parse(googlePlay);
                Intent goToGooglePlay = new Intent(Intent.ACTION_VIEW, webaddress);
                if (goToGooglePlay.resolveActivity(getPackageManager()) != null){
                    startActivity(goToGooglePlay);
                }
            }
        });
    }

    private void addPegSolitaireButtonListener() {
        Button addPegSolitaireButton = findViewById(R.id.pegSolitaire);
        addPegSolitaireButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchToPegSolitaireActivity();
            }
        });
    }

    private void addSlidingTilesButtonListener() {
        Button slidingTilesButton = findViewById(R.id.slidingTiles);
        slidingTilesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchToSlidingTilesActivity();
            }
        });
    }

    private void addMemoryPuzzleButtonListener() {
        Button memoryPuzzleButton = findViewById(R.id.memoryPuzzle);
        memoryPuzzleButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                switchToMemoryPuzzleActivity();
            }
        });
    }


    /**
     * Activate the button to switch screens to UserManagerActivity.
     */
    private void addMyAccountButtonListener() {
        Button addAccountButton = findViewById(R.id.button3);
        addAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchToMyAccountActivity();
            }
        });
    }

    /**
     * Switch the user's screen from the GameCentreActivity to the UserProfileActivity.
     */
    private void switchToMyAccountActivity() {
        Intent tmp = new Intent(this, UserProfileActivity.class);
        startActivity(tmp);
    }

    /**
     * Switch the user's screen from the GameCentreActivitiy to the Sliding Tiles Game
     */
    private void switchToSlidingTilesActivity() {
        Intent slidingTilesIntent = new Intent(getApplicationContext(), StartingActivity.class);
        slidingTilesIntent.putExtra("welcomeText", "SLIDING TILES");
        slidingTilesIntent.putExtra("instructionsText", "HOW TO PLAY: \n " +
                "Slide the tiles around until you have the correct configuation of numbers/image");
        startActivity(slidingTilesIntent);
    }

    /**
     * Switch the user's screen from the GameCentreActivity to the Peg Solitaire Game
     */
    private void switchToPegSolitaireActivity() {
        Intent pegSolitaireIntent = new Intent(getApplicationContext(), StartingActivity.class);
        pegSolitaireIntent.putExtra("welcomeText", "PEG SOLITAIRE");
        pegSolitaireIntent.putExtra("instructionsText", "HOW TO PLAY: \n " +
                "Your goal is to clear all of the pegs but one. \n " +
                "To clear a peg, jump over it into an empty space. \n " +
                "Click a peg to select it, and then click an empty space to make a jump.");
        startActivity(pegSolitaireIntent);
    }

    /**
     * Switch the user's screen from the GameCentreActivity to the Memory Puzzle Game
     */
    private void switchToMemoryPuzzleActivity() {
        Intent memoryPuzzleIntent = new Intent(getApplicationContext(), StartingActivity.class);
        memoryPuzzleIntent.putExtra("welcomeText", "MEMORY PUZZLE");

        //TODO: FILL IN THE INSTRUCTIONS FOR MEMORY PUZZLE
        memoryPuzzleIntent.putExtra("instructionsText", "HOW TO PLAY: ");
        startActivity(memoryPuzzleIntent);
    }

    /**
     * Activate sign out Button
     */

    private void addSignOutButtonListener() {
        Button signOutButton = findViewById(R.id.logout);
        signOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchToSignIn();
            }
        });
    }

    /**
     * Lead to sign in activity
     */
    private void switchToSignIn() {
        Intent tmp = new Intent(this, LoginActivity.class);
        startActivity(tmp);
    }
}

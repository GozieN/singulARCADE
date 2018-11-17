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

         //Activate the buyMoreGameBtn Button.
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


         //Activate Sliding Tile Button.
        Button SlidingTilesBtn = findViewById(R.id.SlidingTiles);
        SlidingTilesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent startIntent = new Intent(getApplicationContext(), StartingActivity.class);
                startActivity(startIntent);
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
}

package fall2018.csc2017.slidingtiles.Views;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import fall2018.csc2017.slidingtiles.GameLauncher;
import fall2018.csc2017.slidingtiles.R;

/**
 * The activity for the user's personal profile.
 */
public class UserProfileActivity extends AppCompatActivity {

    /**
     * The username of the current user.
     */
    TextView userName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        addScoreBoardButtonListener();
        addToGameOptionsButtonListener();
        addSignOutButtonListener();
        userName = findViewById(R.id.userName);
        userName.setText(GameLauncher.getCurrentUser().getUsername());

    }

    /**
     * Activate Scoreboard Button
     */
    private void addScoreBoardButtonListener() {
        Button userScoreBoardButton = findViewById(R.id.userScoreBoard);
        userScoreBoardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchToScoreBoard();
            }
        });
    }

    /**
     * Leads to score board activity.
     */

    private void switchToScoreBoard() {
        Intent tmp = new Intent(this, ScoreBoardActivity.class);
        tmp.putExtra("scores", GameLauncher.getCurrentUser().userScoreBoard.toString());
        startActivity(tmp);
    }

    /**
     * Activate game option Button
     */

    private void addToGameOptionsButtonListener() {
        Button toGameOptionsButton = findViewById(R.id.toGameOptions);
        toGameOptionsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchToGameCentre();
            }
        });
    }

    /**
     * Lead to game centre.
     */

    private void switchToGameCentre() {
        Intent tmp = new Intent(this, GameCentreActivity.class);
        startActivity(tmp);
    }

    /**
     * Activate sign out Button
     */

    private void addSignOutButtonListener() {
        Button signOutButton = findViewById(R.id.signOut);
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

package fall2018.csc2017.slidingtiles;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.view.View;
import android.widget.TextView;

/**
 * The ScoreBoard activity.
 */
public class ScoreBoardActivity extends AppCompatActivity {
    /**
     * The ScoreBoard class ScoreBoardActivity will display
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score_board);

        //Add Listeners
        addMainMenuButtonListener();
        addXButtonListener();

        //Set Tet in Scores TextView to String Variable scores
        String scrs = getIntent().getStringExtra("scores");
        TextView scores = findViewById(R.id.scores);
        scores.setText(scrs);
    }

    /**
     * Takes the user back to the main menu / GameCentreActivity.
     */
    public void switchToMainMenu() {
        Intent mainMenu = new Intent (this, GameCentreActivity.class);
        startActivity(mainMenu);
    }

    /**
     * Activate X Button
     */
    private void addXButtonListener() {
        FloatingActionButton xButton = findViewById(R.id.xButton);
        xButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Clicking xButton finishes this actuvity- it closes the scoreboard
                ScoreBoardActivity.this.finish();
            }
        });
    }
    /**
     * Activate main menu Button
     */
    private void addMainMenuButtonListener() {
        Button main_menu = findViewById(R.id.main_menu);
        main_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchToMainMenu();
            }
        });
    }
}

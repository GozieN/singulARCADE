package fall2018.csc2017.slidingtiles;

import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.view.View;
import android.widget.TextView;

import java.util.List;

/**
 * The ScoreBoard popup activity.
 */
public class ScoreBoardActivity extends AppCompatActivity {
    /**
     * The ScoreBoard class ScoreBoardActivity will display
     */
    static boolean newGameScore = false;
    static boolean newUserScore = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score_board);

        //Add Listeners
        addMainMenuButtonListener();
        addXButtonListener();
        setSnackBar(newGameScore, newUserScore);

        //Set Tet in Scores TextView to String Variable scores
        String scrs = getIntent().getStringExtra("scores");
        TextView scores = findViewById(R.id.scores);
        scores.setText(scrs);
    }

    /**
     * Takes the user back to the main menu / GameCentreActivity.
     */
    public void switchToMainMenu() {
        Intent mainMenu = new Intent(this, GameCentreActivity.class);
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
                //Clicking xButton finishes the scoreBoard activity
                ScoreBoardActivity.this.finish();
                finish();
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

    private void setSnackBar(boolean newGameScore, boolean newUserScore) {
        final Snackbar snackbar;
        ConstraintLayout constraintLayout = findViewById(R.id.constraintLayout);
        System.out.println(newGameScore);
        System.out.println(newUserScore);
        if (newGameScore && newUserScore) {
            snackbar = Snackbar.make(constraintLayout, "New game and personal high score!", Snackbar.LENGTH_INDEFINITE);
            showSnackbar(snackbar);
        } else if (newGameScore) {
            snackbar = Snackbar.make(constraintLayout, "New game high score!", Snackbar.LENGTH_INDEFINITE);
            showSnackbar(snackbar);
        } else if (newUserScore) {
            snackbar = Snackbar.make(constraintLayout, "New personal high score!", Snackbar.LENGTH_INDEFINITE);
            showSnackbar(snackbar);
        }
    }

    private void showSnackbar(final Snackbar snackbar) {
        snackbar.setAction("DISMISS", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                snackbar.dismiss();
                newGameScore = false;
                newUserScore = false;
            }
        });
        snackbar.show();
    }
}

package fall2018.csc2017.slidingtiles;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.view.View;
import android.content.Intent;

/**
 * The activity to first appear when the application is first opened.
 * Code citation: StartingActivity file provided from CSC207 2018
 */

public class WelcomeActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        addNextButtonListener();
    }


    /**
     * Activate the next button.
     */
    private void addNextButtonListener() {
        Button startButton = findViewById(R.id.NextButton);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchToLogin();
            }
        });
    }

    /**
     * Switch to the LoginActivity view in order for the user to Login to their account.
     */
    private void switchToLogin() {
        Intent tmp = new Intent(this, LoginActivity.class);
        startActivity(tmp);
    }
}
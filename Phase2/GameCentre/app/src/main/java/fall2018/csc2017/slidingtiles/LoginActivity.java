package fall2018.csc2017.slidingtiles;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Button;
import android.widget.Toast;
import android.content.Intent;

/**
 * The login activity for users to interact with.
 * Code citation (for login implementation): https://www.youtube.com/watch?v=qpNnfq9ZPDU
 */
public class LoginActivity extends AppCompatActivity {
    /**
     * The User's username input
     */
    EditText UsernameInput;
    /**
     * The User's password input
     */
    EditText PasswordInput;
    /**
     * The Log in button
     */
    Button LoginButton;
    /**
     * The Sign up button
     */
    Button SignUpButton;


    /**
     * The main save file.
     */
    public static final String SAVE_FILENAME = "saving_file.ser";

    /**
     * The user manager.
     */
    private UserManager userManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        userManager = new UserManager();

        SaveAndLoad.loadFromFile(LoginActivity.this, SAVE_FILENAME);

        setContentView(R.layout.activity_login2);
        addSignUpButtonListener();

        //Find all interactive features and set them
        UsernameInput = findViewById(R.id.Username);
        PasswordInput = findViewById(R.id.Password);
        LoginButton = findViewById(R.id.LoginButton);
        SignUpButton = findViewById(R.id.SignUpButton);

        addLogInButtonListener();
    }

    /**
     * Activate the Log in button
     */
    private void addLogInButtonListener() {
        LoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = UsernameInput.getText().toString().trim();
                String password = PasswordInput.getText().toString().trim();
                if (username.isEmpty() || password.isEmpty()) {
                    makeToastNoInputProvidedText();
                } else if (userManager.signIn(username, password)) {
                    GameLauncher.setCurrentUser(userManager.findUser(username));
                    switchToGameCentreActivity();
                    makeToastLoginSuccessful();
                } else {
                    makeToastLoginUnsuccessful();
                }
            }
        });
    }


    /**
     * Display that the user did not input information for a username and password to sign in.
     */
    private void makeToastNoInputProvidedText() {
        Toast.makeText(LoginActivity.this, "No input provided", Toast.LENGTH_SHORT).show();
    }

    /**
     * Display that the login was successful.
     */
    private void makeToastLoginSuccessful() {
        Toast.makeText(LoginActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
    }

    /**
     * Display that the login was unsuccessful as the user put in a non-existent username or wrong password.
     */
    private void makeToastLoginUnsuccessful() {
        Toast.makeText(LoginActivity.this, "Login Unsuccessful, try again.", Toast.LENGTH_SHORT).show();
    }

    /**
     * Activate the next button.
     */
    private void addSignUpButtonListener() {
        Button signupButton = findViewById(R.id.SignUpButton);
        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchToSignUp();
            }
        });
    }

    /**
     * Switch to the LoginActivity view in order for the user to Login to their account.
     */
    private void switchToSignUp() {
        Intent tmp = new Intent(this, SignUpActivity.class);
        startActivity(tmp);
    }

    /**
     * Switch to GameCentreActivity view.
     */
    private void switchToGameCentreActivity() {
        Intent tmp = new Intent(this, GameCentreActivity.class);
        startActivity(tmp);
    }

    /**
     * Read the temporary board from disk.
     */
    @Override
    protected void onResume() {
        super.onResume();
        SaveAndLoad.loadFromFile(LoginActivity.this, SAVE_FILENAME);
    }
}
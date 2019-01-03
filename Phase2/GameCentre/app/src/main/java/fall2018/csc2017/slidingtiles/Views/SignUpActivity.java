package fall2018.csc2017.slidingtiles.Views;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Button;
import android.widget.Toast;
import android.content.Intent;

import fall2018.csc2017.slidingtiles.GameLauncher;
import fall2018.csc2017.slidingtiles.R;
import fall2018.csc2017.slidingtiles.SaveAndLoad;
import fall2018.csc2017.slidingtiles.UserManager;

/**
 * The SignUp activity for users to create an account with.
 * Code citation (for Sign Up implementation): https://www.youtube.com/watch?v=qpNnfq9ZPDU
 */
public class SignUpActivity extends AppCompatActivity {

    /**
     * The username Input
     */
    EditText newUsernameInput;
    /**
     * The password input
     */
    EditText newPasswordInput;
    /**
     * The button to create account
     */
    Button CreateAccountButton;

    /**
     * The user manager.
     */
    private UserManager userManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        userManager = new UserManager();
        SaveAndLoad.loadFromFile(SignUpActivity.this, LoginActivity.SAVE_FILENAME);

        setContentView(R.layout.activity_sign_up);
        addCreateAccountButtonListener();

        newUsernameInput = findViewById(R.id.newUsername);
        newPasswordInput = findViewById(R.id.newPassword);
        CreateAccountButton = findViewById(R.id.CreateAccountButton);


        CreateAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = newUsernameInput.getText().toString().trim();
                String password = newPasswordInput.getText().toString().trim();


                if (username.isEmpty() || password.isEmpty()) {
                    makeToastNoInputProvidedText();
                } else if (userManager.signUp(username, password)) {
                    GameLauncher.setCurrentUser(userManager.getlastAddedUser());
                    makeToastSignUpSuccessful();
                    switchToCreateAccount();
                } else {
                    makeToastSignUpUnsuccessful();
                }
            }
        });
    }

    /**
     * Display that the user did not input information for a username and password to sign in.
     */
    private void makeToastNoInputProvidedText() {
        Toast.makeText(SignUpActivity.this, "No input provided", Toast.LENGTH_SHORT).show();
    }

    /**
     * Display that the sign up was successful.
     */
    private void makeToastSignUpSuccessful() {
        Toast.makeText(SignUpActivity.this, "SignUp Successful", Toast.LENGTH_SHORT).show();
    }

    /**
     * Display that the sign up was unsuccessful since the username inputted by the user is already used by another user.
     */
    private void makeToastSignUpUnsuccessful() {
        Toast.makeText(SignUpActivity.this, "This username has already been taken, try another.", Toast.LENGTH_SHORT).show();
    }

    /**
     * Activate the next button.
     */
    private void addCreateAccountButtonListener() {
        Button createaccountButton = findViewById(R.id.CreateAccountButton);
        createaccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchToCreateAccount();
            }
        });
    }

    /**
     * Switch to the LoginActivity view in order for the user to Login to their account.
     */
    private void switchToCreateAccount() {
        Intent tmp = new Intent(this, GameCentreActivity.class);

        SaveAndLoad.saveToFile(SignUpActivity.this, LoginActivity.SAVE_FILENAME);
        startActivity(tmp);
    }

    /**
     * Dispatch onPause() to fragments.
     */
    @Override
    protected void onPause() {
        super.onPause();

        SaveAndLoad.saveToFile(SignUpActivity.this, LoginActivity.SAVE_FILENAME);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        SaveAndLoad.loadFromFile(SignUpActivity.this, LoginActivity.SAVE_FILENAME);
    }
}
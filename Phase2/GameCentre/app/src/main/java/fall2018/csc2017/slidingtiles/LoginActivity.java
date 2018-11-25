package fall2018.csc2017.slidingtiles;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Button;
import android.widget.Toast;
import android.content.Intent;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

/**
 * The login activity for users to interact with.
 * Code citation (for login implementation): https://www.youtube.com/watch?v=qpNnfq9ZPDU
 */
public class LoginActivity extends AppCompatActivity {
    EditText UsernameInput;
    EditText PasswordInput;
    Button LoginButton;
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
        loadFromFile(SAVE_FILENAME);
        setContentView(R.layout.activity_login2);
        addSignUpButtonListener();

        UsernameInput = findViewById(R.id.Username);
        PasswordInput = findViewById(R.id.Password);
        LoginButton =  findViewById(R.id.LoginButton);
        SignUpButton = findViewById(R.id.SignUpButton);


        LoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = UsernameInput.getText().toString().trim();
                String password = PasswordInput.getText().toString().trim();


                if (username.isEmpty() || password.isEmpty()){
                    Toast.makeText(LoginActivity.this, "No input provided", Toast.LENGTH_SHORT).show();
                }

                else if(userManager.signIn(username, password)){
                    GameLauncher.setCurrentUser(userManager.findUser(username));
                    switchToGameCentreActivity();
                    Toast.makeText(LoginActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(LoginActivity.this, "Login Unsuccessful, try again.", Toast.LENGTH_SHORT).show();
                }
            }
        });
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
        saveToFile(LoginActivity.SAVE_FILENAME);
        startActivity(tmp);
    }

    /**
     * Switch to GameCentreActivity view.
     */
    private void switchToGameCentreActivity() {
        Intent tmp = new Intent(this, GameCentreActivity.class);
        saveToFile(LoginActivity.SAVE_FILENAME);
        startActivity(tmp);
    }

    /**
     * Read the temporary board from disk.
     */
    @Override
    protected void onResume() {
        super.onResume();
        loadFromFile(SAVE_FILENAME);
    }

    /**
     * Load the user manager and scoreboard from fileName.
     *
     * @param fileName the name of the file
     */
    public void loadFromFile(String fileName) {

        try {
            InputStream inputStream = this.openFileInput(fileName);
            if (inputStream == null) {
                saveToFile(fileName);
            }
            else {
                ObjectInputStream input = new ObjectInputStream(inputStream);
                userManager = (UserManager) input.readObject();
                inputStream.close();
            }
        } catch (FileNotFoundException e) {
            Log.e("login activity", "File not found: " + fileName);
        } catch (IOException e) {
            Log.e("login activity", "Can not read file: " + e.toString());
        } catch (ClassNotFoundException e) {
            Log.e("login activity", "File contained unexpected data type: " + e.toString());
        }
    }

    /**
     * Save the user manager and scoreboard to fileName.
     *
     * @param fileName the name of the file
     */
    public void saveToFile(String fileName) {
        try {
            ObjectOutputStream outputStream = new ObjectOutputStream(
                    this.openFileOutput(fileName, MODE_PRIVATE));
            outputStream.writeObject(userManager);
            outputStream.close();
        } catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }
}
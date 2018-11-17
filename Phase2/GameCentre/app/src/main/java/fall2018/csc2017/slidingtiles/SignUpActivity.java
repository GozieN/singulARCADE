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
 * The SignUp activity for users to create an account with.
 * Code citation (for Sign Up implementation): https://www.youtube.com/watch?v=qpNnfq9ZPDU
 */
public class SignUpActivity extends AppCompatActivity {


    EditText newUsernameInput;
    EditText newPasswordInput;
    Button CreateAccountButton;

    /**
     * The user manager.
     */
    private UserManager userManager;

    private ScoreBoard scoreBoard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        userManager = new UserManager();
        loadFromFile(LoginActivity.SAVE_FILENAME);

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


                if (username.isEmpty() || password.isEmpty()){
                    Toast.makeText(SignUpActivity.this, "No input provided", Toast.LENGTH_SHORT).show();
                }
                else if(userManager.signUp(username, password)){

                    GameLauncher.setCurrentUser(userManager.getListOfUsers().get(userManager.getListOfUsers().size()-1));
                    Toast.makeText(SignUpActivity.this, "SignUp Successful", Toast.LENGTH_SHORT).show();
                    switchToCreateAccount();
                }
                else{
                    Toast.makeText(SignUpActivity.this, "This username has already been taken, try another.", Toast.LENGTH_SHORT).show();
                }
            }
        });
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
        saveToFile(LoginActivity.SAVE_FILENAME);
        startActivity(tmp);
    }

    /**
     * Dispatch onPause() to fragments.
     */
    @Override
    protected void onPause() {
        super.onPause();
        saveToFile(LoginActivity.SAVE_FILENAME);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        loadFromFile(LoginActivity.SAVE_FILENAME);
    }

    /**
     * Load the user manager and scoreboard from fileName.
     *
     * @param fileName the name of the file
     */
    private void loadFromFile(String fileName) {

        try {
            InputStream inputStream = this.openFileInput(fileName);
            if (inputStream == null) {
                saveToFile(fileName);
            }
            else {
                ObjectInputStream input = new ObjectInputStream(inputStream);
                ArrayList arrayList = (ArrayList) input.readObject();
                userManager = (UserManager) arrayList.get(0);
                scoreBoard = (ScoreBoard) arrayList.get(1);

//                userManager = (UserManager) input.readObject();
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

        ArrayList arrayList= new ArrayList();
        arrayList.add(userManager);
        arrayList.add(scoreBoard);
        try {
            ObjectOutputStream outputStream = new ObjectOutputStream(
                    this.openFileOutput(fileName, MODE_PRIVATE));
            outputStream.writeObject(arrayList);
            outputStream.close();
        } catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }
}
package co.mandeep_singh.chatapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Objects;

import co.mandeep_singh.chatapp.Auth.Auth;

public class MainActivity extends AppCompatActivity {

    private TextView newToApp;
    private EditText Name,Number, Password;
    private Button authButton;
    public ProgressBar progressBar;
    private boolean signIn = true;
    private Auth auth = new Auth();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Objects.requireNonNull(getSupportActionBar()).hide();
        setContentView(R.layout.activity_main);
        newToApp = (TextView) findViewById(R.id.authSwitch);
        Name = (EditText) findViewById(R.id.Name);
        Number = (EditText) findViewById(R.id.Number);
        Password = (EditText) findViewById(R.id.Password);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        authButton = (Button) findViewById(R.id.authButton);
        newToApp.setOnClickListener(view -> authSwitch());
        authButton.setOnClickListener(view -> doAuth());
    }

    private void doAuth(){
        String name = Name.getText().toString();
        String number = Number.getText().toString();
        String password = Password.getText().toString();

        try {
            if (!signIn && !name.isEmpty() && !number.isEmpty() && !password.isEmpty()) {
                progressBar.setVisibility(View.VISIBLE);
                auth.signUp(name, number, password, MainActivity.this, progressBar);
            }
            if(signIn && !number.isEmpty() && !password.isEmpty()){
                progressBar.setVisibility(View.VISIBLE);
                auth.signIn( number, password, MainActivity.this, progressBar);
            }

        }
        catch(Exception e){
            Log.d("Main Activity", e.getMessage());
            progressBar.setVisibility(View.INVISIBLE);
        }
    }

    private void authSwitch(){
        signIn = !signIn;
        if(signIn)
        {
            newToApp.setText(R.string.new_to_our_app);
            Name.setVisibility(View.INVISIBLE);
            authButton.setText(R.string.signin);
        }
        if(!signIn){
            newToApp.setText(R.string.Gbtl);
            Name.setVisibility(View.VISIBLE);
            authButton.setText(R.string.signup);
        }
    }
}
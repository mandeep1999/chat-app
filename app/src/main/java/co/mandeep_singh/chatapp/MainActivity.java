package co.mandeep_singh.chatapp;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    private TextView newToApp;
    private EditText Name;
    private Button authButton;
    private boolean signIn = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Objects.requireNonNull(getSupportActionBar()).hide();
        setContentView(R.layout.activity_main);
        newToApp = (TextView) findViewById(R.id.authSwitch);
        Name = (EditText) findViewById(R.id.Name);
        authButton = (Button) findViewById(R.id.authButton);
        newToApp.setOnClickListener(view -> authSwitch());
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
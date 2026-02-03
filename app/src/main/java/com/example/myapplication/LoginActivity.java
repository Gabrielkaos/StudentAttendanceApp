package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class LoginActivity extends AppCompatActivity {


    private Button loginButton;
    private TextView errorText;

    private EditText emailText;
    private EditText passwordText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);

        loginButton = findViewById(R.id.button);
        errorText = findViewById(R.id.errorTextBox);
        emailText = findViewById(R.id.emailTextBox);
        passwordText = findViewById(R.id.passwordTextBox);

        errorText.setVisibility(View.GONE);

        loginButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                performLogin();
            }
        });


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }


    private void performLogin(){
        String email = emailText.getText().toString().strip();
        String password = passwordText.getText().toString().strip();

        if(email.isEmpty()){
            showError("Enter email");
            return;
        }

        if(password.isEmpty()){
            showError("Enter password");
            return;
        }

        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            showError("Please enter a valid email");
            return;
        }

        errorText.setVisibility(View.GONE);
        loginButton.setText("Logging");

        loginSucccess();

    }


    private void loginSucccess(){
        Intent i = new Intent(LoginActivity.this,MainActivity.class);

        i.putExtra("USER_EMAIL",emailText.getText().toString().strip());

        startActivity(i);

        finish();
    }


    private void showError(String error1){
        errorText.setVisibility(View.VISIBLE);
        errorText.setText(error1);
    }
}
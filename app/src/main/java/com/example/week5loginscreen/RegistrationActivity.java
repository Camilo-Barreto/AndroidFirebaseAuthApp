package com.example.week5loginscreen;


import android.app.Notification;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class RegistrationActivity extends AppCompatActivity {
    // Declare
    private EditText emailEdTxt, passwordEdTxt;
    private Button btn_register;
    private TextView signinTxt;
    private ProgressBar progressBar;
    private FirebaseAuth mAuth;

    @Override
    protected void onStart() {
        super.onStart();
        // Check if user is signed in (not-null)
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null){
            // User is logged is as current user is not null -> Open welcome page
            Intent intent = new Intent(getApplicationContext(), WelcomeActivity.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        // Initialising
        emailEdTxt = findViewById(R.id.emailEdtxt);
        passwordEdTxt = findViewById(R.id.passwordEdtxt);
        btn_register = findViewById(R.id.btn_register);
        signinTxt = findViewById(R.id.signinTxt);
        progressBar = findViewById(R.id.progressBar);
        mAuth = FirebaseAuth.getInstance();

        signinTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open the login page
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
            }
        });

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // show the visibility of progress bar to show loading
                progressBar.setVisibility(View.VISIBLE);

                String email, password;
                // Can use string.value of or getText().toString()
                email = emailEdTxt.getText().toString();
                password = passwordEdTxt.getText().toString();

                // Check if email or password is empty
                if (TextUtils.isEmpty(email)){
                    Toast.makeText(RegistrationActivity.this, "Enter email", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(password)){
                    Toast.makeText(RegistrationActivity.this, "Enter password", Toast.LENGTH_SHORT).show();
                    return;
                }

                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>()
                        {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task)
                            {
                                if (task.isSuccessful()) {
                                    Toast.makeText(getApplicationContext(),"Registration successful!", Toast.LENGTH_LONG).show();
                                    // hide the progress bar
                                    progressBar.setVisibility(View.GONE);
                                    // if the user created intent to login activity
                                    Intent intent = new Intent(RegistrationActivity.this, LoginActivity.class);
                                    startActivity(intent);
                                    finish();
                                }
                                else {
                                    // Registration failed
                                    Toast.makeText(getApplicationContext(), "Registration failed!!" + " Please try again later", Toast.LENGTH_LONG).show();
                                    // hide the progress bar
                                    progressBar.setVisibility(View.GONE);
                                }
                            }
                        });
            }
        });
    }

}

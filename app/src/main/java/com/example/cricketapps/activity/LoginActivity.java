package com.example.cricketapps.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.example.cricketapps.AppSettings;
import com.example.cricketapps.R;
import com.example.cricketapps.Utils;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.progressindicator.CircularProgressIndicator;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    Button btnLogin;
    EditText etEmail, etPassword;
    CircularProgressIndicator loadingBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loadingBar = findViewById(R.id.loadingBar);
        btnLogin = findViewById(R.id.btnLogin);
        etPassword = findViewById(R.id.etPassword);
        etEmail = findViewById(R.id.etEmail);

        if(!AppSettings.getUId(this).equals(""))
        {
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
            finish();
        }

        Switch sw = (Switch) findViewById(R.id.switch1);
        sw.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    // The toggle is enabled
                    sw.setText("SignIn");
                    btnLogin.setText("SignIn");
                } else {
                    // The toggle is disabled
                    sw.setText("Login");
                    btnLogin.setText("Login");
                }
            }
        });


// Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadingBar.setVisibility(View.VISIBLE);
                if(btnLogin.getText().toString().trim().equals("Login"))
                {
                    mAuth.signInWithEmailAndPassword(etEmail.getText().toString().trim(), etPassword.getText().toString().trim())
                            .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        // Sign in success, update UI with the signed-in user's information
                                        Log.d(Utils.getTag()+" ", "signInWithEmail:success");
                                        FirebaseUser user = mAuth.getCurrentUser();
                                        updateUI(user);
                                    } else {
                                        // If sign in fails, display a message to the user.
                                        Log.w(Utils.getTag()+" ", "signInWithEmail:failure", task.getException());
                                        Toast.makeText(LoginActivity.this, "Authentication failed.",
                                                Toast.LENGTH_SHORT).show();
                                        updateUI(null);
                                    }
                                }
                            });
                }else
                    {
                        mAuth.createUserWithEmailAndPassword(etEmail.getText().toString().trim(), etPassword.getText().toString().trim())
                                .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        if (task.isSuccessful()) {
                                            // Sign in success, update UI with the signed-in user's information
                                            Log.d(Utils.getTag()+" ", "createUserWithEmail:success");
                                            FirebaseUser user = mAuth.getCurrentUser();
                                            updateUI(user);
                                        } else {
                                            // If sign in fails, display a message to the user.
                                            Log.w(Utils.getTag()+" ", "createUserWithEmail:failure", task.getException());
                                            Toast.makeText(LoginActivity.this, "Authentication failed.",
                                                    Toast.LENGTH_SHORT).show();
                                            updateUI(null);
                                        }
                                    }
                                });
                    }
            }
        });
    }

    public void updateUI(FirebaseUser user)
    {
        loadingBar.setVisibility(View.GONE);
        if(user!= null)
        {
            Log.e(Utils.getTag()+" " , " getUid ="+ user.getUid());
            Log.e(Utils.getTag()+" " , " getEmail ="+ user.getEmail());
            Log.e(Utils.getTag()+" " , " getMetadata ="+ user.getMetadata());
            Log.e(Utils.getTag()+" " , " getPhoneNumber ="+ user.getPhoneNumber());
            Log.e(Utils.getTag()+" " , " getPhotoUrl ="+ user.getPhotoUrl());
            Log.e(Utils.getTag()+" " , " getDisplayName ="+ user.getDisplayName());

            AppSettings.setUId(LoginActivity.this, user.getUid()+"");


            startActivity(new Intent(LoginActivity.this, MainActivity.class));
        }
    }
}
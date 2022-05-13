package com.example.foodex.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.foodex.R;
import com.example.foodex.viewmodel.ForgotPasswordViewModel;

public class ForgotPassword extends AppCompatActivity {

    private EditText emailEditText;
    private Button resetPasswordButton;
    private ProgressBar progressBar;

    private ForgotPasswordViewModel forgotPasswordViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        forgotPasswordViewModel = new ViewModelProvider(this).get(ForgotPasswordViewModel.class);

        emailEditText = (EditText) findViewById(R.id.email);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        resetPasswordButton = (Button) findViewById(R.id.resetPassword);
        resetPasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetPassword();
            }
        });

        forgotPasswordViewModel.getProgressBar().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if(!aBoolean)
                {
                    int visibility = View.INVISIBLE;
                    progressBar.setVisibility(View.GONE);
                }
            }
        });

        forgotPasswordViewModel.getAuthenticationMessage().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                Toast.makeText(ForgotPassword.this, s, Toast.LENGTH_LONG).show();
            }
        });

        forgotPasswordViewModel.getCompleted().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if(aBoolean)
                {
                    startActivity(new Intent(ForgotPassword.this, MainActivity.class));
                }
            }
        });

    }

    private void resetPassword() {
        String email = emailEditText.getText().toString().trim();

        if (email.isEmpty()) {
            emailEditText.setError("Email is required!");
            emailEditText.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailEditText.setError("Please provide a valid email address");
            emailEditText.requestFocus();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);
        forgotPasswordViewModel.resetPassword(email);
    }
}
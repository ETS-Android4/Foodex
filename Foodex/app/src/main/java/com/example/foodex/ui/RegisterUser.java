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
import android.widget.TextView;
import android.widget.Toast;

import com.example.foodex.R;
import com.example.foodex.viewmodel.RegisterViewModel;

public class RegisterUser extends AppCompatActivity implements View.OnClickListener {

    private TextView banner, registerUser;
    private EditText editTextFullName, editTextEmail, editTextPassword;
    private ProgressBar progressBar;
    private RegisterViewModel registerViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);

        registerViewModel = new ViewModelProvider(this).get(RegisterViewModel.class);

        banner = (TextView) findViewById(R.id.banner);
        banner.setOnClickListener(this);

        registerUser = (Button) findViewById(R.id.registerUser);
        registerUser.setOnClickListener(this);

        editTextFullName = (EditText) findViewById(R.id.fullName);
        editTextEmail = (EditText) findViewById(R.id.email);
        editTextPassword = (EditText) findViewById(R.id.password);


        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        registerViewModel.getProgressBar().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if(!aBoolean)
                {
                    int visibility = View.INVISIBLE;
                    progressBar.setVisibility(View.GONE);
                }
            }
        });

        registerViewModel.getAuthenticationMessage().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                Toast.makeText(RegisterUser.this, s, Toast.LENGTH_LONG).show();
            }
        });

        registerViewModel.getCompleted().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if(aBoolean)
                {
                    startActivity(new Intent(RegisterUser.this, LoginActivity.class));
                }
            }
        });


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.banner:
                startActivity(new Intent(this, LoginActivity.class));
                break;
            case R.id.registerUser:
                registerUser();
        }
    }

    private void registerUser() {
        String email = editTextEmail.getText().toString().trim();
        String fullName = editTextFullName.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        if (fullName.isEmpty()) {
            editTextFullName.setError("Full Name is required!");
            editTextFullName.requestFocus();
            return;
        }

        if (email.isEmpty()) {
            editTextEmail.setError("Email is required!");
            editTextEmail.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            editTextEmail.setError("Please provide a valid email address");
            editTextEmail.requestFocus();
            return;
        }

        if (password.isEmpty()) {
            editTextPassword.setError("Password is required!");
            editTextPassword.requestFocus();
            return;
        }

        if (password.length() < 6) {
            editTextPassword.setError("Min password length should be 6 characters!");
            editTextPassword.requestFocus();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);
//        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(RegisterUser.this, new OnCompleteListener<AuthResult>() {
//            @Override
//            public void onComplete(@NonNull Task<AuthResult> task) {
//                progressBar.setVisibility(View.VISIBLE);
//                if (task.isSuccessful()) {
//                    User user = new User(fullName, email);
//
//                    FirebaseDatabase.getInstance().getReference("Users")
//                            .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(user)
//                            .addOnCompleteListener(new OnCompleteListener<Void>() {
//                                @Override
//                                public void onComplete(@NonNull Task<Void> task) {
//                                    if (task.isSuccessful()) {
//                                        Toast.makeText(RegisterUser.this, "User has been registered successfully!", Toast.LENGTH_LONG).show();
//                                        progressBar.setVisibility(View.GONE);
//                                    } else {
//                                        Toast.makeText(RegisterUser.this, "Failed to register! Try again!", Toast.LENGTH_LONG).show();
//                                        progressBar.setVisibility(View.GONE);
//                                    }
//                                }
//                            });
//                } else {
//                    Toast.makeText(RegisterUser.this, "Failed to register the user! Try again!", Toast.LENGTH_LONG).show();
//                    progressBar.setVisibility(View.GONE);
//                }
//            }
//        });
        registerViewModel.register(email, password, fullName);
    }
}